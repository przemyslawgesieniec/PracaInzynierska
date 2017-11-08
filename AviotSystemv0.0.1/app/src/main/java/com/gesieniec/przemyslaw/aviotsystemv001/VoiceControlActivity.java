package com.gesieniec.przemyslaw.aviotsystemv001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceRecognition;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.CommandDataClass;
public class VoiceControlActivity extends AppCompatActivity{

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
    }
    public void onClickGetSpeechInput(View view) {
        voiceRecognition.getSpeechRecognizer().startListening(voiceRecognition.getSpeechRecognizerIntent());
    }

    public void btn_VoiceRecDeactivation(View view) {
        voiceRecognition.getSpeechRecognizer().stopListening();
    }



}
