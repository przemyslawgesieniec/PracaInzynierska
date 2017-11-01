package com.gesieniec.przemyslaw.aviotsystemv001;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

//import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceRecognition;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceControlActivity extends AppCompatActivity implements RecognitionListener{

    private TextView userConsole;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         *Voice recognition setup
         */

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());



    }
    public void onClickGetSpeechInput(View view) {

        Toast.makeText(this,"button clicked", Toast.LENGTH_SHORT).show();
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
       // Toast.makeText(this,"onReadyForSpeech", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBeginningOfSpeech() {
       // Toast.makeText(this,"started to listen", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {
       // Toast.makeText(this,"sonBufferReceived", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEndOfSpeech() {
       // Toast.makeText(this,"onEndOfSpeech", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int error) {
        Toast.makeText(this,"onError" + error, Toast.LENGTH_SHORT).show(); //ttaj dostaje na ryj -,-
        Log.d("VoiceRecognition","as: "+error);
    }

    @Override
    public void onResults(Bundle results) {
        if (null != results) {
            ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (voiceResults != null) {
                Toast.makeText(this, voiceResults.get(0), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {
       // Toast.makeText(this,"onEvent", Toast.LENGTH_LONG).show();

    }

    public void btn_VoiceRecDeactivation(View view) {
        mSpeechRecognizer.stopListening();
    }



/***
 * Its working but not what i need for my project ;(
 */
//    public void onClickGetSpeechInput(View view) {
//
//        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        startActivityForResult(i,10);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case 10:
//                if(resultCode == RESULT_OK && data != null) {
//
//                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    Toast.makeText(this,result.get(0), Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
//

}
