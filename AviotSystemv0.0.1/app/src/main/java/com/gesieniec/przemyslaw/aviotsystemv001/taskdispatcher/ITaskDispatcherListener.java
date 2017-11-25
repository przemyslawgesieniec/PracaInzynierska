package com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher;

import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

import java.net.DatagramPacket;

/**
 * Created by przem on 09.11.2017.
 */

public interface ITaskDispatcherListener {
    void handleDispatchedVoiceCommandExecution(VoiceCommand voiceCommand);
    void handleDispatchedSystemCommandExecution(SystemCommandHandler systemCommandHandler);
    void handleDispatchedIoTCommandExecution(DatagramPacket datagramPacket);
    void handleDispatchedIoTCommandExecution(String capabilities);
}
