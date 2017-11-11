package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by przem on 02.11.2017.
 */

public class VoiceCommandHandler {

    private VoiceCommand voiceCommand;

    public VoiceCommandHandler(VoiceCommand voiceCommand) {
        this.voiceCommand = voiceCommand;
    }

    public Enum<Language> getCurrentCommandLanguage() {
        return currentCommandLanguage;
    }

    private enum Language{
        PL_POLISH,
        ENG_ENGLISH
    }
    private enum CommandMathResult{
        FULL_MATCH,
        PARTIAL_MATCH,
        NO_MATCH
    }
    private Enum<Language> currentCommandLanguage = Language.ENG_ENGLISH;

    public void interpreteCommand(List<String> capturedVoiceResult){
        parseVoiceResultToCommand(capturedVoiceResult);
    }
    private void parseVoiceResultToCommand(List<String> capturedVoiceResult){
        Log.d("VoiceCommandHandler","parseVoiceResult");
        List<String> capturedVoiceResultLowerCase = new ArrayList<>(StringArrayToLowerCase(capturedVoiceResult));
        for (String possibleCommand : capturedVoiceResultLowerCase) {
            Log.d("VoiceCommandHandler","possibleCommand 1: "+possibleCommand);
            /**
             * Try to find keywords in possible command
             */
            CommandMathResult match = tryMatchWithKeyWords(possibleCommand);
            if(match == CommandMathResult.FULL_MATCH){
                Log.d("VoiceCommandHandler","full match");
                voiceCommand.setVoiceCommandType(VoiceCommand.VoiceCommandType.DEVICE_RELATED);
                voiceCommand.setBestMatchCommand(possibleCommand);
                Log.d("VoiceCommandHandler","full match possible command: "+possibleCommand);
                return;
            }
            else if(match == CommandMathResult.PARTIAL_MATCH){
                Log.d("VoiceCommandHandler","partial match");
                Log.d("VoiceCommandHandler","possible command: "+possibleCommand);
                voiceCommand.setVoiceCommandType(VoiceCommand.VoiceCommandType.DEVICE_RELATED);
                voiceCommand.setBestMatchCommand(possibleCommand);
                //TODO: check if is it possible to run partial command
                return;
            }
            else if(tryToMachWitchSystemCommands(possibleCommand)){
                Log.d("VoiceCommandHandler","system command try");
                voiceCommand.setVoiceCommandType(VoiceCommand.VoiceCommandType.SYSTEM_RELATED);
                voiceCommand.setBestMatchCommand(possibleCommand);
                return;
            }
        }
        if(voiceCommand.getVoiceCommandType() != VoiceCommand.VoiceCommandType.INVALID){
            voiceCommand.setBestMatchCommand(capturedVoiceResultLowerCase.get(0));
            //TODO: NEED FIX , NULL IN  USER CONSOLE
        }
    }

    private boolean tryToMachWitchSystemCommands(String possibleCommand) {
        for(String systemCommand : CommandDataClass.getSystemCommandsENG()){
            if(systemCommand == possibleCommand){
                return true;
            }
        }
        return false;
    }


    private CommandMathResult tryMatchWithKeyWords(String possibleCommand){
        Log.d("VoiceCommandHandler","tryMatchWithKeyWords");
        String mAction = null;
        String mDeviceName = null;
        String mPlace = null;
        boolean mNegation = false;
        /**
         * ENG
         */
        for (String action : CommandDataClass.getActionsListENG()){
            if(possibleCommand.contains(action)){
                mAction = action;
            }
        }
        for (String device : CommandDataClass.getDevicesListENG()){
            if(possibleCommand.contains(device)){
                mDeviceName = device;
            }
        }
        for (String place : CommandDataClass.getPlacesListENG()){
            if(possibleCommand.contains(place)){
                mPlace = place;
            }
        }
        for (String negation : CommandDataClass.getNegationENG()){
            if(possibleCommand.contains(negation)){
                mNegation = true;
            }
        }



        /**
         * PL
         */
        for (String action:CommandDataClass.getActionsListPL()){
            if(possibleCommand.contains(action)){
                mAction = action;
            }
        }
        for (String device:CommandDataClass.getDevicesListPL()){
            if(possibleCommand.contains(device)){
                mDeviceName = device;
            }
        }
        for (String place:CommandDataClass.getPlacesListPL()){
            if(possibleCommand.contains(place)){
                mPlace = place;
            }
        }
        for (String negation:CommandDataClass.getNegationPL()){
            if(possibleCommand.contains(negation)){
                mNegation = true;
            }
        }

        Log.d("VoiceCommandHandler","mAction="+mAction+"mDeviceName="+mDeviceName+"mPlace="+mPlace);
        /**
         * COMMON
         */
        if(mAction != null && mDeviceName != null && mPlace != null)
        {
            voiceCommand.setAction(mAction);
            voiceCommand.setDeviceName(mDeviceName);
            voiceCommand.setPlace(mPlace);
            voiceCommand.setNegation(mNegation);
            return CommandMathResult.FULL_MATCH;
        }
        if(mAction != null && mDeviceName != null)
        {
            voiceCommand.setAction(mAction);
            voiceCommand.setDeviceName(mDeviceName);
            voiceCommand.setNegation(mNegation);
            return CommandMathResult.PARTIAL_MATCH;
        }
        return CommandMathResult.NO_MATCH;
    }

    private List<String> StringArrayToLowerCase(List<String> capturedVoiceResult)
    {
        List<String> capturedVoiceResultLowerCase = new ArrayList<>();
        for (String possibleCommand:capturedVoiceResult) {
            capturedVoiceResultLowerCase.add(possibleCommand.toLowerCase());
        }
        return capturedVoiceResultLowerCase;
    }
}
