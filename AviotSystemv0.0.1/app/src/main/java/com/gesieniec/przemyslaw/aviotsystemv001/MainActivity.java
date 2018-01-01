package com.gesieniec.przemyslaw.aviotsystemv001;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.ApplicationContext;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.view.devices.DeviceInstanceFragment;
import com.gesieniec.przemyslaw.aviotsystemv001.view.ManualControlFragment;
import com.gesieniec.przemyslaw.aviotsystemv001.view.VoiceControlFragment;
import com.gesieniec.przemyslaw.aviotsystemv001.view.devices.DeviceSettingsFragment;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.DatagramPacket;

public class MainActivity extends AppCompatActivity implements ITaskDispatcherListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private VoiceControlFragment voiceControlFragment;
    private ManualControlFragment manualControlFragment;
    private ApplicationContext applicationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * layout
         */
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        /**
         * application ctxt
         */
        TaskDispatcher.addListener(this);
        applicationContext = new ApplicationContext(this);


    }

    /**
     * layout related
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    voiceControlFragment = new VoiceControlFragment();
                    return voiceControlFragment;
                case 1:
                     manualControlFragment = new ManualControlFragment();
                    return manualControlFragment;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    /**
     * Voice Control related
     */


    public void onClickStartStopCapturing(View view) {
        Log.d("voice fragment","onClickStartStopCapturing");
        if (voiceControlFragment.getAviotButtonState() == false) {
            Log.d("voice fragment","onClickS tartStopCapturing false");
            //  Toast.makeText(this, "was off and clicked", Toast.LENGTH_SHORT).show();
            applicationContext.getVoiceRecognition().getSpeechRecognizer().startListening(applicationContext.getVoiceRecognition().getSpeechRecognizerIntent());
            voiceControlFragment.setAviotButtonState(true);
        }
        else {
            Log.d("voice fragment","onClickStartStopCapturing true");
            applicationContext.getVoiceRecognition().getSpeechRecognizer().stopListening();
            //voiceRecognition.getSpeechRecognizer().cancel();
            //  Toast.makeText(this, "was on and clicked", Toast.LENGTH_SHORT).show();
            voiceControlFragment.setAviotButtonState(false);
        }
        scrollDownScrollView();
    }

    public void setAviotButtonState(boolean aviotButtonState) {
        voiceControlFragment.setAviotButtonState(aviotButtonState);
    }


    /**
     * Task dispatcher related
     */
    @Override
    public void handleDispatchedVoiceCommandExecution(VoiceCommand arg) {
        TextView t = new TextView(this);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_console);
        t.setText("You:  "+arg.getBestMatchCommand());
        Log.d("VoiceCommandActivity","setBestMatchCommand: "+arg.getBestMatchCommand());
        if((arg.getVoiceCommandType() != VoiceCommand.VoiceCommandType.INVALID)){
            t.setTextColor(Color.rgb(255,255,255));
            ll.addView(t);
            scrollDownScrollView();
        }
        else{
            //TODO : get langiage
            t.setTextColor(Color.rgb(255,255,40));
            ll.addView(t);
            writeAviotMessage("I can not do that");
            //STUB();
        }

    }


    @Override
    public void handleDispatchedSystemCommandExecution(SystemCommandHandler systemCommandHandler) {
        writeAviotMessage(systemCommandHandler.getSystemAnswer());
        Log.d("VoiceCommandActivity","handleDispatchedSystemCommandExecution");
    }

    @Override
    public void handleDispatchedIoTCommandExecution(DatagramPacket datagramPacket) {
        Log.d("VoiceCommandActivity","New device trying to connect");
        writeAviotMessage("New device trying to connect");
    }

    @Override
    public void handleDispatchedIoTCommandExecution(String capabilities) {
        writeAviotMessage("New device connected capabilities: "+capabilities);

        //TODO : zrobic osobne fragmenty dla roznego rodzaju urzadzen i metode pozwalajaca je odroznic
        addManualControlFragment(capabilities);
    }


    @Override
    public void handleDispatchedGUICommandExecution(DeviceCapabilities capabilities) {
        //TODO: disable related button
    }

    @Override
    public void handleDispatchedIoTUpdateCommandExecution(DeviceCapabilities capabilities) {
        //TODO: enable related button
        Switch s = (Switch)findViewById(capabilities.getID());
        s.setChecked(capabilities.getState());
        String state = "OFF";
        if(capabilities.getState()){
            state = "ON";
        }
        writeAviotMessage(capabilities.getDeviceName()+" is now "+state);
    }


    private void writeAviotMessage(String msg){
        TextView systemResponse = new TextView(this);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_console);
        systemResponse.setTextColor(Color.rgb(114,156,239));
        systemResponse.setText("AVIOT: "+msg);
        ll.addView(systemResponse);
        scrollDownScrollView();
    }

    /**
     * device fragment related
     */
    private int deviceID = 0;
    private void addManualControlFragment(String capabilities){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("capabilities",capabilities);
        bundle.putInt("fragmentID",deviceID);
        DeviceInstanceFragment device = new DeviceInstanceFragment();
        device.setArguments(bundle);
        fragmentTransaction.add(R.id.ll_devices, device, "device" + deviceID);
        deviceID++;
        fragmentTransaction.commit();
    }

    /**
     * Manual Control related
     */

    private void scrollDownScrollView(){
        final ScrollView sv = (ScrollView) findViewById(R.id.scrollViewConsole);
        sv.post(() -> sv.fullScroll(View.FOCUS_DOWN));
    }


}
