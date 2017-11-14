package com.gesieniec.przemyslaw.aviotsystemv001.taskhandler;

import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.SystemCommandHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceCommand;

/**
 * Created by przem on 09.11.2017.
 */

public interface ITaskDispatcherListener {
    void handleDispatchedVoiceCommandExecution(VoiceCommand voiceCommand);
    void handleDispatchedSystemCommandExecution(SystemCommandHandler systemCommandHandler);
}
