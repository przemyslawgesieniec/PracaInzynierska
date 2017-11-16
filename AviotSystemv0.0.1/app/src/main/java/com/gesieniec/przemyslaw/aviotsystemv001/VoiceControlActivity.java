package com.gesieniec.przemyslaw.aviotsystemv001;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler.BroadcastListener;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.ApplicationContext;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

public class VoiceControlActivity extends AppCompatActivity implements ITaskDispatcherListener{

    private boolean aviotButtonState = false;
    private ApplicationContext applicationContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TaskDispatcher.addListener(this);
        applicationContext = new ApplicationContext(this);
        new BroadcastListener().execute("");
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

    //TODO: REFACTOR THIS !!!!
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
        Log.d("VoiceCommandActivity","handleDispatchedSystemCommandExecution");
        TextView systemResponse = new TextView(this);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_console);
        systemResponse.setTextColor(Color.rgb(114,156,239));
        systemResponse.setText(systemCommandHandler.getSystemAnswer());
        ll.addView(systemResponse);
    }

    public void setAviotButtonState(boolean aviotButtonState) {
        this.aviotButtonState = aviotButtonState;
    }
}
