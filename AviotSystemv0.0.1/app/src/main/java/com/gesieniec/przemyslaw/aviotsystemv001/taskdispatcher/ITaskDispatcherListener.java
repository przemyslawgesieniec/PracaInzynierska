package com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.DatagramPacket;
import java.util.List;

/**
 * Created by przem on 09.11.2017.
 */

public interface ITaskDispatcherListener {
    void handleDispatchedVoiceCommandExecution(VoiceCommand voiceCommand);
    void handleDispatchedSystemCommandExecution(SystemCommandHandler systemCommandHandler);
    void handleDispatchedIoTCommandExecution(List<String> data);
    void handleDispatchedIoTCommandExecution(String capabilities);
    void handleDispatchedIoTUpdateCommandExecution(DeviceCapabilities capabilities);
    void handleDispatchedGUICommandExecution(DeviceCapabilities capabilities);
    void handleDispatchedUpdateDeviceDataCommandExecution(DeviceCapabilities capabilities);
    void handleDispatchedIoTConsistencyControl(DeviceCapabilities capabilities);
    void handleDispatchedIoTDeviceNotResponding(CommonDevice data);
}
