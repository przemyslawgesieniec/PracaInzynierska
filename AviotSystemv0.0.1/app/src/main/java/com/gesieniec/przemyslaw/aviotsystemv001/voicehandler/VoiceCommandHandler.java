package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.ApplicationContext;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.CommonCommand;

import java.util.ArrayList;

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
    private Enum<Language> currentCommandLanguage = Language.ENG_ENGLISH;

    public boolean interpreteCommand(ArrayList<String> capturedVoiceResult){
        return (parseVoiceResultToCommand(capturedVoiceResult) != null) ? true : false;
    }
    private String parseVoiceResultToCommand(ArrayList<String> capturedVoiceResult){
        ArrayList<String> capturedVoiceResultLowerCase = new ArrayList<>(StringArrayToLowerCase(capturedVoiceResult));
        for (String possibleCommand : capturedVoiceResultLowerCase) {

            /**
             * Try to find keywords in possible command
             */
            if(tryMatchWithKeyWords(possibleCommand)){
                if(voiceCommand.isContainFullCommand()) {
                    return possibleCommand;
                }
                else{
                    /*partial match ( not implemented yet )*/}
                }
        }
        return null;
    }


    private boolean tryMatchWithKeyWords(String possibleCommand){
        String mAction = null;
        String mDeviceName = null;
        String mPlace = null;
        boolean mNegation = false;
        /**
         * ENG
         */
        for (String action:CommandDataClass.getActionsListENG()){
            if(possibleCommand.contains(action)){
                mAction = action;
            }
        }
        for (String device:CommandDataClass.getDevicesListENG()){
            if(possibleCommand.contains(device)){
                mDeviceName = device;
            }
        }
        for (String place:CommandDataClass.getPlacesListENG()){
            if(possibleCommand.contains(place)){
                mPlace = place;
            }
        }
        for (String negation:CommandDataClass.getNegationENG()){
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


        /**
         * COMMON
         */
        if(mAction != null && mDeviceName != null && mPlace != null)
        {
            voiceCommand.setAction(mAction);
            voiceCommand.setDeviceName(mDeviceName);
            voiceCommand.setPlace(mPlace);
            voiceCommand.setNegation(mNegation);
            return true;
        }
        return false;
    }

    public boolean executeCommand(){
        ArrayList<CommonDevice> commonDeviceArrayList = new ArrayList<>();
        for (CommonDevice device : ApplicationContext.getCommonDevices()){
            if(device.getName() == voiceCommand.getDeviceName()) {
                commonDeviceArrayList.add(device);
            }
        }
        if(commonDeviceArrayList.size() > 0) {
            if (commonDeviceArrayList.size() > 1) {
                for (CommonDevice repeatedDevice : commonDeviceArrayList) {
                    if (repeatedDevice.getLocation() == voiceCommand.getPlace()) {
                        //execute for this device
                        return true;
                    }
                }
            } else {
                //execute for this device
                return true;
            }
        }
        return false;
        //display error, device not found
    }
    private ArrayList<String> StringArrayToLowerCase(ArrayList<String> capturedVoiceResult)
    {
        ArrayList<String> capturedVoiceResultLowerCase = new ArrayList<>();
        for (String possibleCommand:capturedVoiceResult) {
            capturedVoiceResultLowerCase.add(possibleCommand.toLowerCase());
        }
        return capturedVoiceResultLowerCase;
    }
}
