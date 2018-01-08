package com.gesieniec.przemyslaw.aviotsystemv001.iothandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.MultiSwitch;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.Switch;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler.MessageHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.ApplicationContext;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by przem on 10.11.2017.
 */

public class DeviceHandler implements ITaskDispatcherListener {



    @Override
    public void handleDispatchedVoiceCommandExecution(VoiceCommand voiceCommand) {
        if (voiceCommand.getVoiceCommandType() == VoiceCommand.VoiceCommandType.DEVICE_RELATED) {
            sendMessageToRelatedDevice(voiceCommand);
            Log.d("DeviceHandler", "message sent");
        }
    }

    public DeviceHandler() {
        TaskDispatcher.addListener(this);
    }

    @Override
    public void handleDispatchedSystemCommandExecution(SystemCommandHandler systemCommandHandler) {
        //TODO implement sth when system command need device action
    }

    @Override
    public void handleDispatchedIoTCommandExecution(List<String> data) {
        Log.d("ATTACH REQ","sending capabilities");
        sendCapabilityRequest(data);
    }

    @Override
    public void handleDispatchedIoTCommandExecution(String capabilities) {
        CommonDevice device = createNewDevice(capabilities);
        if (device != null) {
            ApplicationContext.addCommonDevice(device);
        }
        Log.d("DeviceHandler", "new device: added");
    }

    @Override
    public void handleDispatchedIoTUpdateCommandExecution(DeviceCapabilities capabilities) {
        //DO NOT IMPLEMENT
    }

    @Override
    public void handleDispatchedGUICommandExecution(DeviceCapabilities capabilities) {
        sendMessageToRelatedDevice(capabilities);
    }

    @Override
    public void handleDispatchedUpdateDeviceDataCommandExecution(DeviceCapabilities capabilities) {
        sendMessageToRelatedDevice(capabilities);
    }

    @Override
    public void handleDispatchedIoTConsistencyControl(DeviceCapabilities capabilities) {
        CommonDevice device = getDeviceByMacAddress(capabilities);
        device.restoreDeviceStatusCounter();
    }

    @Override
    public void handleDispatchedIoTDeviceNotResponding(CommonDevice device) {
        ApplicationContext.getDisconnectedCommonDevices().add(device);
        ApplicationContext.getCommonDevices().remove(device);
    }


    private List<CommonDevice> getDevicesByNameFromTheCommand(String deviceNameFromCommand) {

        List<CommonDevice> commonDeviceArrayList = new ArrayList<>();
        for (CommonDevice device : ApplicationContext.getCommonDevices()) {
            if (device.getName() == deviceNameFromCommand) {
                commonDeviceArrayList.add(device);
            }
        }
        return commonDeviceArrayList;
    }

    private CommonDevice getDeviceByMacAddress(DeviceCapabilities deviceCapabilities) {
        for (CommonDevice device : ApplicationContext.getCommonDevices()) {
            if (device.getMacAddress().equals(deviceCapabilities.getMacAddress())) {
                return device;
            }
        }
        return null;
    }

    /**
     * Interpret voice action to device action
     * which can be send and interpreted by module
     *
     * @param device
     * @param action
     * @return ENUM(DeviceAction) if device supports this action.
     */
    private DeviceAction getDeviceAction(CommonDevice device, String action) {
        for (String key : device.getActionMapENG().keySet()) {
            if (key == action) {
                Log.d("POSZŁO", "IFIFIFIFIF");
                return device.getActionMapENG().get(key);
            }
        }
        return null;
    }

    private void sendMessageToRelatedDevice(VoiceCommand voiceCommand) {
        Log.d("DeviceHandler", "sendMessageToRelatedDevice");
        List<CommonDevice> commonDevices = getDevicesByNameFromTheCommand(voiceCommand.getDeviceName());
        if (commonDevices.size() > 0) {
            for (CommonDevice device : commonDevices) {
               String message =  device.getMessageBasedOnAction(getDeviceAction(device, voiceCommand.getAction()));
                if (message != null) {
                    Log.d("DeviceHandler", "msg: " + message);
                    MessageHandler msgHandler = new MessageHandler();
                    msgHandler.sendAndReceiveUDPMessage(message, device.getDeviceAddress());
                }
            }
        } else {
            Log.d("DeviceHandler", "no device with this name");
        }
    }

    private void sendMessageToRelatedDevice(DeviceCapabilities deviceCapabilities) {
        CommonDevice device = getDeviceByMacAddress(deviceCapabilities);
        try {
            device.updateDeviceWithCapabilities(deviceCapabilities);
            new MessageHandler().sendAndReceiveUDPMessage(device.getMessageToSend(deviceCapabilities), device.getDeviceAddress());
        }
        catch (NullPointerException e) {
            Log.d("MessageToRelatedDevice", "something gone wrong");
            e.printStackTrace();
        }
    }

    private void sendCapabilityRequest(List<String> data) {
        MessageHandler messageHandler = new MessageHandler();
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(data.get(1));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        messageHandler.sendAndReceiveUDPMessage("CapabilityRequest", ipAddress);
    }

    /**
     * this method interpret capabilities from string and create virtual instance of it in app
     */
    private CommonDevice createNewDevice(String capabilities) {

        DeviceCapabilities deviceCapabilities = new DeviceCapabilities(capabilities);

        InetAddress deviceAddress = null;
        Log.d("Device name", deviceCapabilities.getDeviceName());
        Log.d("Device Ip", deviceCapabilities.getIpAddress());
        Log.d("Device mac", deviceCapabilities.getMacAddress());
        Log.d("number of switches", String.valueOf(deviceCapabilities.getNumberOfSwitches()));

        try {
            deviceAddress = InetAddress.getByName(deviceCapabilities.getIpAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        if (deviceAddress != null) {
            switch (deviceCapabilities.getDeviceType()) {
                case SWITCH:
                    boolean switchState = deviceCapabilities.getStates().get(0);
                    return new Switch(deviceCapabilities.getDeviceName(), deviceCapabilities.getDeviceLocation(), deviceAddress, deviceCapabilities.getMacAddress(), switchState);
                case MULTI_SWITCH:
                    ArrayList<Boolean> states = new ArrayList<>(deviceCapabilities.getStates());
                    return new MultiSwitch(states,deviceCapabilities.getDeviceName(),deviceCapabilities.getDeviceLocation(),deviceAddress,deviceCapabilities.getMacAddress());
                case NONE:
                    return null;
            }
        }
        return null;
    }

    public void startSendingConsistencyControlMessage(){

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(CommonDevice device : ApplicationContext.getCommonDevices()){
                    Log.d("BUBUABUABAUBAUBAUBA","DEVICE , status counter: "+device.getDeviceStatusCounter());
                    if(device.getDeviceStatusCounter() > 0){

                        new MessageHandler().sendAndReceiveUDPMessage("connectionControl", device.getDeviceAddress());
                        device.decreseDeviceStatusCounter();
                    }
                    else {
                        Log.d("UBUABSIUFDBAI","DEVICE DISCONNECTED, status counter: "+device.getDeviceStatusCounter());
                        TaskDispatcher.newTask(TaskDispatcher.IoTTaskContext.DEVICE_NOT_RESPONDING,device);

                    }
                }
            }
        },1000,4000);
    }

}
