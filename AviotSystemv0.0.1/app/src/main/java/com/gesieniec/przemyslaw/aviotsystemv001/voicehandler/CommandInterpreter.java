package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by przem on 02.11.2017.
 */

public class CommandInterpreter {

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
            VoiceCommand keyWordsMatch = tryMatchWithKeyWords(possibleCommand);
            Log.d("CommandInterpreter","keyWordsMatch"+keyWordsMatch);
            if(keyWordsMatch != null){
                if(keyWordsMatch.isContainFullCommand())
                {
                    execuiteCommand(keyWordsMatch);
                }
                else{/*partial match ( not implemented yet )*/}
                return possibleCommand;
            }

            /**
             * Try to match possible command with predefined full commands
             */
//            if(tryMatchToFullCommand(possibleCommand) != null) {
//                execuiteCommand(possibleCommand);
//                return possibleCommand;
//            }
        }
        return null;
    }

//    private String tryMatchToFullCommand(String possibleCommand){
//
//        for (String englishCommand:CommandDataClass.getDefaultFullCommandsListENG()){
//            if(possibleCommand.equals(englishCommand)){
//                currentCommandLanguage = Language.ENG_ENGLISH;
//                return possibleCommand;
//            }
//
//        }
//        for (String polishCommand:CommandDataClass.getDefaultFullCommandsListPL()){
//            if(possibleCommand.equals(polishCommand)){
//                currentCommandLanguage = Language.PL_POLISH;
//                return possibleCommand;
//            }
//        }
//        return null;
//    }

    private VoiceCommand tryMatchWithKeyWords(String possibleCommand){
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
            Log.d("alltog","full command: "+mAction+"_"+mDeviceName+"_"+mPlace+"_"+mNegation);
            return new VoiceCommand(mAction,mDeviceName,mPlace,mNegation);
        }
        return null;
    }

    private void execuiteCommand(VoiceCommand possibleCommand){
        Log.d("CI:execuiteCommand","possibleCommand: "+possibleCommand.toString());
        /**
         * stub for a moment
         */
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
