//package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;
//
//import android.os.Bundle;
//import android.speech.RecognitionListener;
//import android.speech.SpeechRecognizer;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
///**
// * Created by przem on 31.10.2017.
// */
//
//public class VoiceRecognition implements RecognitionListener {
//
//
//    @Override
//    public void onReadyForSpeech(Bundle params) {
//
//    }
//
//    @Override
//    public void onBeginningOfSpeech() {
//
//    }
//
//    @Override
//    public void onRmsChanged(float rmsdB) {
//
//    }
//
//    @Override
//    public void onBufferReceived(byte[] buffer) {
//
//    }
//
//    @Override
//    public void onEndOfSpeech() {
//
//    }
//
//    @Override
//    public void onError(int error) {
//
//    }
//
//    @Override
//    public void onResults(Bundle results) {
//
//        ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//        Log.d("VoiceRecognition", result.toString());
//                   // Toast.makeText(result.get(0), Toast.LENGTH_LONG).show();
//
//    }
//
//    @Override
//    public void onPartialResults(Bundle partialResults) {
//
//    }
//
//    @Override
//    public void onEvent(int eventType, Bundle params) {
//
//    }
//}
