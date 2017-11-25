package com.gesieniec.przemyslaw.aviotsystemv001.systemhandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by przem on 14.11.2017.
 */

public class SystemCommandHandler implements ITaskDispatcherListener {

    private String systemAnswer;
    private SystemCommandType systemCommandType = SystemCommandType.NONE;
    public enum SystemCommandType{
        REJECT,
        ACCEPT,
        WARNING,
        NONE
    }


    public SystemCommandHandler() {
        TaskDispatcher.addListener(this);
    }

    /**
     * getters
     */
    public String getSystemAnswer() {
        return systemAnswer;
    }
    public SystemCommandType getSystemCommandType() {
        return systemCommandType;
    }

    @Override
    public void handleDispatchedVoiceCommandExecution(VoiceCommand voiceCommand) {
        if(voiceCommand.getVoiceCommandType() == VoiceCommand.VoiceCommandType.SYSTEM_RELATED){
            executeSystemVoiceCommand(voiceCommand.getBestMatchCommand());
        }
    }

    private void executeSystemVoiceCommand(String command){
        switch (command){
            case "get status":
            case "check status":
            case "show connected devices":
                Log.d("SystemCommandHandler","show connected devices");
                List<String> connectedDevices = checkConnectedDevices();
                systemCommandType = SystemCommandType.ACCEPT;
                systemAnswer = "AVIOT:  You have "+connectedDevices.size()+" connected devices: "+connectedDevices.toString();
                TaskDispatcher.newTask(TaskDispatcher.SystemTaskContext.EXECUTE_SYSTEM_COMMAND, this);
                break;
        }
    }

    private List<String> checkConnectedDevices(){
        List<CommonDevice> commonDevices = ApplicationContext.getCommonDevices();
        List<String> commonDevicesName = new ArrayList<>();
        for(CommonDevice device : commonDevices){
            commonDevicesName.add(device.getName());
        }
        return commonDevicesName;
    }
    /**
     * DO NOT IMPLEMENT !
     */
    @Override
    public void handleDispatchedSystemCommandExecution(SystemCommandHandler systemCommandHandler) {
        /**
         * DO NOT IMPLEMENT HERE
         */
    }

    @Override
    public void handleDispatchedIoTCommandExecution(DatagramPacket datagramPacket) {}

    @Override
    public void handleDispatchedIoTCommandExecution(String capabilities) {

    }

}
