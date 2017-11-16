package com.gesieniec.przemyslaw.aviotsystemv001.iothandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler.MessageHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.ApplicationContext;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by przem on 10.11.2017.
 */

public class DeviceHandler implements ITaskDispatcherListener {

    @Override
    public void handleDispatchedVoiceCommandExecution(VoiceCommand voiceCommand) {
        if(voiceCommand.getVoiceCommandType() == VoiceCommand.VoiceCommandType.DEVICE_RELATED){
            sendMessageToRelatedDevice(voiceCommand);
            Log.d("DeviceHandler","message sent (STUB)");
        }
    }

    public DeviceHandler() {
        TaskDispatcher.addListener(this);
    }

    @Override
    public void handleDispatchedSystemCommandExecution(SystemCommandHandler systemCommandHandler) {
        //TODO implement sth when system command need device action
    }

    private List<CommonDevice> getDevicesByNameFromTheCommand(String deviceNameFromCommand){

        List<CommonDevice> commonDeviceArrayList = new ArrayList<>();
        for (CommonDevice device : ApplicationContext.getCommonDevices()) {
            if (device.getName() == deviceNameFromCommand) {
                commonDeviceArrayList.add(device);
            }
        }
        return commonDeviceArrayList;
    }

    /**
     * Interpret voice action to device action
     * which can be send and interpreted by module
     * @param device
     * @param action
     * @return ENUM(DeviceAction) if device supports this action.
     */
    private DeviceAction getDeviceAction(CommonDevice device, String action) {
        for(String key : device.getActionMapENG().keySet()) {
            if(key == action){
                return device.getActionMapENG().get(key);
            }
        }
        return null;
    }
    private void sendMessageToRelatedDevice(VoiceCommand voiceCommand){
        Log.d("DeviceHandler","sendMessageToRelatedDevice");
        List<CommonDevice> commonDevices = getDevicesByNameFromTheCommand(voiceCommand.getDeviceName());
        if(commonDevices.size() > 0){
            for(CommonDevice device : commonDevices){
                String deviceAction = getDeviceAction(device,voiceCommand.getAction()).toString();
                String message = device.toString().concat(deviceAction);
                if(message != null){
                    Log.d("DeviceHandler","msg: "+message);
                    MessageHandler msgHandler = new MessageHandler();
                    msgHandler.sendUDPMessage(message,device.getDeviceAddress());
                    Log.d("MessageHandler","message sent");
                }
            }
        }
        else{
            Log.d("DeviceHandler","no device with this name");
        }
    }

}
