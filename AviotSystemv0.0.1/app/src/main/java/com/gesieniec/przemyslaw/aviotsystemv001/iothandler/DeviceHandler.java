package com.gesieniec.przemyslaw.aviotsystemv001.iothandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.LightSwitch;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler.MessageHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.ApplicationContext;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.ITaskDispatcherListener;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
    public void handleDispatchedIoTCommandExecution(DatagramPacket datagramPacket) {
        sendCapabilityRequest(datagramPacket);
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

    }

    @Override
    public void handleDispatchedGUICommandExecution(DeviceCapabilities capabilities) {
        sendMessageToRelatedDevice(capabilities);
    }

    @Override
    public void handleDispatchedUpdateDeviceDataCommandExecution(DeviceCapabilities capabilities) {
        sendMessageToRelatedDevice(capabilities);
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
                String deviceAction = getDeviceAction(device, voiceCommand.getAction()).toString();
                String message = device.toString().concat(deviceAction);
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

    private void sendCapabilityRequest(DatagramPacket datagramPacket) {
        MessageHandler messageHandler = new MessageHandler();
        messageHandler.sendAndReceiveUDPMessage("CapabilityRequest", datagramPacket.getAddress());
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
            switch (checkDeviceType(deviceCapabilities.getDeviceType())) {
                case SWITCH:
                    boolean switchState = deviceCapabilities.getStates().get(0);
                    return new LightSwitch(deviceCapabilities.getDeviceName(), deviceCapabilities.getDeviceLocation(), deviceAddress, deviceCapabilities.getMacAddress(), switchState);//TODO: FIX LOCATION !!!
                //TODO: MULTI SWITCH !!!
                case NONE:
                    return null;
            }
        }
        return null;
    }

    //TODO: make this as map in device handler and use find on map
    private DeviceType checkDeviceType(String deviceType) {
        switch (deviceType) {
            case "switch":
                return DeviceType.SWITCH;
            case "multiswitch":
                return DeviceType.MULTI_SWITCH;
            default:
                return null;
        }
    }

}
