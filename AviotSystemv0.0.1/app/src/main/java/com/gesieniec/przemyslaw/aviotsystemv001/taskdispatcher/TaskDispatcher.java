package com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher;

import android.util.Log;
import android.widget.Switch;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by przem on 01.11.2017.
 * <p>
 * Purpose of this class is to outsource tasks to proper components
 * triggered by any other component.
 * <p>
 * eg. VoiceHandler after receiving a voiceCommand has several action to realize:
 * change status in GUI, send message to IoT device.
 * These two actions are delegating to GUI component, and Message Handler.
 */

public class TaskDispatcher {


//    public enum TaskContext{
//        GUI_COMMAND,
//        SEND_UDP_MESSAGE,
//        IOT_CONSISTENCY_REQUEST
//    }

    public enum VoiceTaskContext {
        EXECUTE_VOICE_COMMAND
    }

    public enum GuiTaskContext {
        SWITCH_STATE_CHANGED,
        UPDATE_DEVICE_DATA

    }

    public enum SystemTaskContext {
        EXECUTE_SYSTEM_COMMAND
    }

    public enum IoTTaskContext {
        ATTACH_REQUEST,
        ATTACH_COMPLETE,
        UPDATE_DEVICE_DATA
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

    public static void newTask(IoTTaskContext cause, DatagramPacket data) {
        switch (cause) {
            case ATTACH_REQUEST:
                Log.d("TaskDispatcher:", "new task IoTCommand");
                for (ITaskDispatcherListener executeSystemCommandListener : listeners) {
                    executeSystemCommandListener.handleDispatchedIoTCommandExecution(data);
                }


        }
    }

    public static void newTask(IoTTaskContext cause, String data) {
        switch (cause) {
            case ATTACH_COMPLETE:
                for (ITaskDispatcherListener executeSystemCommandListener : listeners) {
                    executeSystemCommandListener.handleDispatchedIoTCommandExecution(data);
                }
        }
    }
    public static void newTask(IoTTaskContext cause, DeviceCapabilities data) {
        switch (cause) {
            case UPDATE_DEVICE_DATA:
                for (ITaskDispatcherListener executeSystemCommandListener : listeners) {
                    executeSystemCommandListener.handleDispatchedIoTUpdateCommandExecution(data);
                }
        }
    }
    //    }
    public static void newTask(GuiTaskContext cause, DeviceCapabilities capabilities) {
        switch (cause) {
            case SWITCH_STATE_CHANGED:
                for (ITaskDispatcherListener executeSystemCommandListener : listeners) {
                    executeSystemCommandListener.handleDispatchedGUICommandExecution(capabilities);
                }
            case UPDATE_DEVICE_DATA:
                capabilities.setMessageType("updatecapabilities");
                for (ITaskDispatcherListener executeSystemCommandListener : listeners) {
                    executeSystemCommandListener.handleDispatchedUpdateDeviceDataCommandExecution(capabilities);
                }
        }
    }
}
