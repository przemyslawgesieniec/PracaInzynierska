package com.gesieniec.przemyslaw.aviotsystemv001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceRecognition;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.CommandDataClass;
public class VoiceControlActivity extends AppCompatActivity implements ITaskDispatcherListener{

    public void setAviotButtonState(boolean aviotButtonState) {
        this.aviotButtonState = aviotButtonState;
    }
    private boolean aviotButtonState = false;
    private VoiceRecognition voiceRecognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**
         * Initialize commandData
         */
        CommandDataClass.initializeCommandsData();
        /**
         *Voice recognition setup in constructor
         */
        voiceRecognition = new VoiceRecognition(this);
        /**
         *Task Dispatcher setup
         */
        TaskDispatcher.addListener(this);
    }

    public void onClickStartStopCapturing(View view) {
        if(aviotButtonState == false)
        {
          //  Toast.makeText(this, "was off and clicked", Toast.LENGTH_SHORT).show();
            voiceRecognition.getSpeechRecognizer().startListening(voiceRecognition.getSpeechRecognizerIntent());
            aviotButtonState = true;
        }
        else{
            voiceRecognition.getSpeechRecognizer().stopListening();
            //voiceRecognition.getSpeechRecognizer().cancel();
          //  Toast.makeText(this, "was on and clicked", Toast.LENGTH_SHORT).show();
            aviotButtonState = false;
        }
    }
    @Override
    public void handleDispatchedTask(String arg) {
        Toast.makeText(this, "poszlo !!!!!"+arg, Toast.LENGTH_SHORT).show();
    }
}
