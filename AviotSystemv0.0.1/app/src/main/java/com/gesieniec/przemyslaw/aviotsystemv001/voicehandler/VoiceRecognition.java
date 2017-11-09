package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.gesieniec.przemyslaw.aviotsystemv001.VoiceControlActivity;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.TaskDispatcher;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by przem on 31.10.2017.
 */

public class VoiceRecognition implements RecognitionListener {

    private VoiceControlActivity vca;

    public SpeechRecognizer getSpeechRecognizer() {
        return speechRecognizer;
    }

    private SpeechRecognizer speechRecognizer;

    public Intent getSpeechRecognizerIntent() {
        return speechRecognizerIntent;
    }

    private Intent speechRecognizerIntent;

    public VoiceRecognition(VoiceControlActivity vca) {
        this.vca = vca;
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(vca);
        speechRecognizer.setRecognitionListener(this);
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,vca.getPackageName());
    }

    @Override
    public void onReadyForSpeech(Bundle params) {}

    @Override
    public void onBeginningOfSpeech() {}

    @Override
    public void onRmsChanged(float rmsdB) {}

    @Override
    public void onBufferReceived(byte[] buffer) {}

    @Override
    public void onEndOfSpeech() {
        vca.setAviotButtonState(false);
    }

    @Override
    public void onError(int error) {
        Log.d("VoiceRecognition","as: "+error);
    }

    @Override
    public void onResults(Bundle results) {

        /* TODO change for try->catch */
        if (null != results) {
            VoiceCommand voiceCommand = new VoiceCommand(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
            if(TaskDispatcher.newTask(TaskDispatcher.TaskContext.INTERPRET_VOICE_COMMAND,voiceCommand)){
                Log.d("VoiceRecognition","command.toStrong(): "+voiceCommand.toString());
                if(TaskDispatcher.newTask(TaskDispatcher.TaskContext.EXECUTE_VOICE_COMMAND,voiceCommand)){
                    Log.d("VoiceRecognition","command executed");
                }
                else{
                    Log.d("VoiceRecognition","unable to execute command on selected device");
                }
            }
            else{
                Toast.makeText(vca, "didn't catch that", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(vca, "something went wrong", Toast.LENGTH_SHORT).show();
        }
        vca.setAviotButtonState(false);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {}

    @Override
    public void onEvent(int eventType, Bundle params) {}
}
