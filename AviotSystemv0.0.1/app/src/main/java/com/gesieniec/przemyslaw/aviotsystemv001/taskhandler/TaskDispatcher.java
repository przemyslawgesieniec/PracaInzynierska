package com.gesieniec.przemyslaw.aviotsystemv001.taskhandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
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

//    public enum TaskContext{
//        GUI_COMMAND,
//        SEND_UDP_MESSAGE,
//        IOT_CONSISTENCY_REQUEST
//    }

    public enum VoiceTaskContext {
        EXECUTE_VOICE_COMMAND,
        VOICE_COMMAND_EXECUTED
    }

    public enum SystemTaskContext {
        EXECUTE_SYSTEM_COMMAND
    }

    private static ArrayList<ITaskDispatcherListener> listeners = new ArrayList<>();

    public static void addListener(ITaskDispatcherListener listener) {
        listeners.add(listener);
    }

    public static void newTask(VoiceTaskContext cause, VoiceCommand data) {
        switch (cause) {
            case EXECUTE_VOICE_COMMAND:
                List<String> voiceResults = data.getRawCommand();
                if (voiceResults != null) {
                    VoiceCommandHandler voiceCommandHandler = new VoiceCommandHandler(data);
                    voiceCommandHandler.interpreteCommand(voiceResults);
                    try {
                        for (ITaskDispatcherListener executeVoiceCommandListener : listeners) {
                            Log.d("TaskDispatcher", "setBestMatchCommand: " + data.getBestMatchCommand());
                            executeVoiceCommandListener.handleDispatchedVoiceCommandExecution(data);//->GUI Update //->Send UDP MSG
                        }
                    } catch (Exception e) {
                        Log.d("TaskDispatcher:", e.toString());
                    }
                }
                break;
            case VOICE_COMMAND_EXECUTED:
                break;
        }
    }

    public static void newTask(SystemTaskContext cause, SystemCommandHandler data) {
        Log.d("TaskDispatcher:", "new task SystemTaskContext");
        switch (cause) {
            case EXECUTE_SYSTEM_COMMAND:
                if (data.getSystemCommandType() != SystemCommandHandler.SystemCommandType.NONE) {
                    for (ITaskDispatcherListener executeSystemCommandListener : listeners) {
                        executeSystemCommandListener.handleDispatchedSystemCommandExecution(data);
                    }
                }
                break;
        }
    }
}
