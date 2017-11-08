package com.gesieniec.przemyslaw.aviotsystemv001.taskhandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.CommandInterpreter;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.util.ArrayList;

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

public final class TaskDispatcher {

    public enum TaskContext{
        EXECUTE_VOICE_COMMAND,
        INTERPRET_VOICE_COMMAND,
        GUI_COMMAND,
        UDP_MESSAGE,
        IOT_CONSISTENCY_REQUEST
    }

    public static boolean newTask(TaskContext cause, CommonCommand data){
        switch (cause){
            case INTERPRET_VOICE_COMMAND:
                if(data instanceof VoiceCommand)
                {
                    ArrayList<String> voiceResults = ((VoiceCommand)data).getRawCommand();
                    if (voiceResults != null) {
                        CommandInterpreter commandInterpreter = new CommandInterpreter();
                        /**
                         * This method know only if the command was intepreted or not.
                         */
                        //Update GUI
                        //Voice Synthesis answer
                        //Send UDP MSG
                       return commandInterpreter.interpreteCommand(voiceResults);
                    }
                }
                break;
            case EXECUTE_VOICE_COMMAND:
                break;
            case GUI_COMMAND:
                break;
            case UDP_MESSAGE:
                break;
            case IOT_CONSISTENCY_REQUEST:
                break;
        }
        return false;
    }
}
