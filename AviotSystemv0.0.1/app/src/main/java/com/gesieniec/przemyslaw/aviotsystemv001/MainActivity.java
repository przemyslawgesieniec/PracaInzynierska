package com.gesieniec.przemyslaw.aviotsystemv001;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.ApplicationContext;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.DatagramPacket;

public class MainActivity extends AppCompatActivity implements ITaskDispatcherListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
    }

    public void setAviotButtonState(boolean aviotButtonState) {
        Log.d("MainActivity","setAviotButtonState");
        voiceControlFragment.setAviotButtonState(aviotButtonState);

    }

    /**
     * Manual Control related
     */

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
        }
        else{
            TextView systemResponse = new TextView(this);
            systemResponse.setText("AVIOT:  I can not do that");
            t.setTextColor(Color.rgb(255,255,40));
            systemResponse.setTextColor(Color.rgb(114,156,239));
            ll.addView(t);
            ll.addView(systemResponse);
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
        writeAviotMessage("New device connected");
    }

    private void writeAviotMessage(String msg){
        TextView systemResponse = new TextView(this);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_console);
        systemResponse.setTextColor(Color.rgb(114,156,239));
        systemResponse.setText(msg);
        ll.addView(systemResponse);
    }
}
