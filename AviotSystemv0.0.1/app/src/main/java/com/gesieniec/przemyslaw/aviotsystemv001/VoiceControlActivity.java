package com.gesieniec.przemyslaw.aviotsystemv001;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceAction;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.MessageHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceRecognition;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.CommandDataClass;
public class VoiceControlActivity extends AppCompatActivity implements ITaskDispatcherListener{

    private boolean aviotButtonState = false;
    private ApplicationContext applicationContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        applicationContext = new ApplicationContext(this);
        TaskDispatcher.addListener(this);
    }

    public void onClickStartStopCapturing(View view) {
        if(aviotButtonState == false)
        {
          //  Toast.makeText(this, "was off and clicked", Toast.LENGTH_SHORT).show();
            applicationContext.getVoiceRecognition().getSpeechRecognizer().startListening(applicationContext.getVoiceRecognition().getSpeechRecognizerIntent());
            aviotButtonState = true;
        }
        else{
            applicationContext.getVoiceRecognition().getSpeechRecognizer().stopListening();
            //voiceRecognition.getSpeechRecognizer().cancel();
          //  Toast.makeText(this, "was on and clicked", Toast.LENGTH_SHORT).show();
            aviotButtonState = false;
        }
    }

    @Override
    public void handleDispatchedVoiceCommandExecution(VoiceCommand arg) {
        TextView t = new TextView(this);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_console);
        t.setText("You>>  "+arg.getBestMatchCommand());
        Log.d("VoiceCommandActivity","setBestMatchCommand: "+arg.getBestMatchCommand());
        if((arg.getVoiceCommandType() != VoiceCommand.VoiceCommandType.INVALID)){
            t.setTextColor(Color.rgb(255,255,255));
            ll.addView(t);
        }
        else{
            TextView systemResponse = new TextView(this);
            systemResponse.setText("AVIOT>>  I can not do that");
            t.setTextColor(Color.rgb(255,255,40));
            systemResponse.setTextColor(Color.rgb(114,156,239));
            ll.addView(t);
            ll.addView(systemResponse);
        }
    }
    public void setAviotButtonState(boolean aviotButtonState) {
        this.aviotButtonState = aviotButtonState;
    }
}
