package com.gesieniec.przemyslaw.aviotsystemv001.taskhandler;

import android.speech.tts.Voice;
import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.VoiceControlActivity;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by przem on 01.11.2017.
 *
 * Purpose of this class is to outsource tasks to proper components
 * triggered by any other component.
 *
 * eg. VoiceHandler after receiving a voiceCommand has several action to realize:
 * change status in GUI, send message to IoT device.
 * These two actions are delegating to GUI component, and Message Handler.
 *
 */

public class TaskDispatcher {

    public enum TaskContext{
        EXECUTE_VOICE_COMMAND,
        INTERPRET_VOICE_COMMAND,
        GUI_COMMAND,
        SEND_UDP_MESSAGE,
        IOT_CONSISTENCY_REQUEST
    }

    private static ArrayList<ITaskDispatcherListener> listeners = new ArrayList<>();

    public static void addListener(ITaskDispatcherListener listener){
        listeners.add(listener);
        Log.d("TaskDispatcher","someone inserted listener" + listeners.size());

    }

    public static boolean newTask(TaskContext cause, CommonCommand data){
        switch (cause){
            case INTERPRET_VOICE_COMMAND:
                if(data instanceof VoiceCommand){
                    ArrayList<String> voiceResults = ((VoiceCommand)data).getRawCommand();
                    if (voiceResults != null) {
                        VoiceCommandHandler voiceCommandHandler = new VoiceCommandHandler((VoiceCommand)data);
                        /**
                         * This method know only if the command was intepreted or not.
                         */
                        Log.d("TaskDispatcher","command interpreted");
                       return voiceCommandHandler.interpreteCommand(voiceResults);
                    }
                }
                break;
            case EXECUTE_VOICE_COMMAND:
                if(data instanceof VoiceCommand){
                    try{
                        VoiceCommandHandler voiceCommandHandler = new VoiceCommandHandler((VoiceCommand)data);
                        if(voiceCommandHandler.executeCommand()){
                            Log.d("TaskDispatcher","execute command");
                          //  Log.d("TaskDispatcher","listeners size: "+  listeners.size()); //TUTAJ MAM NULL POINTER EXEPTION

//                            for(ITaskDispatcherListener executeVoiceCommandListener : listeners)
//                                executeVoiceCommandListener.handleDispatchedTask("dupa");
                            //->Voice Synthesis answer
                            //->Send UDP MSG
                            return true;
                        }
                    }
                    catch (Exception e){
                        Log.d("TaskDispatcher:", e.toString());
                    }

                }
                break;
            case GUI_COMMAND:
                break;
            case SEND_UDP_MESSAGE:
                break;
            case IOT_CONSISTENCY_REQUEST:
                break;
        }
        return false;
    }
}
