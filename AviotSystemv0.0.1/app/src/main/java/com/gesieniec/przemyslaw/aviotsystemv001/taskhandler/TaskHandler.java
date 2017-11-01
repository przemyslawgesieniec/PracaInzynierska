package com.gesieniec.przemyslaw.aviotsystemv001.taskhandler;

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

public final class TaskHandler {

    public enum ReceivedCause{
        VOICE_COMMAND,
        GUI_COMMAND,
        UDP_MESSAGE,
        IOT_CONSISTENCY_REQUEST
    }

    public static void createNewTask(ReceivedCause cause, String data){
        switch (cause){
            case VOICE_COMMAND:
                break;
            case GUI_COMMAND:
                break;
            case UDP_MESSAGE:
                break;
            case IOT_CONSISTENCY_REQUEST:
                break;
        }
    }
}
