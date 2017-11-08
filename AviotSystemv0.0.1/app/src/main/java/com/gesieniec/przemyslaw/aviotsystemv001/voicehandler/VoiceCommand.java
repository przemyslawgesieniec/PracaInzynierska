package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import com.gesieniec.przemyslaw.aviotsystemv001.taskhandler.CommonCommand;

import java.util.ArrayList;

/**
 * Created by przem on 02.11.2017.
 */

public class VoiceCommand extends CommonCommand {

    public String getAction() { return action; }

    public String getDeviceName() {
        return deviceName;
    }

    public String getPlace() {
        return place;
    }

    public boolean getNegation() { return negation; }


    public ArrayList<String> getRawCommand() {
        return rawCommand;
    }

    public void setRawCommand(ArrayList<String> rawCommand) {
        this.rawCommand = rawCommand;
    }

    private ArrayList<String> rawCommand;

    public void setAction(String action) {
        this.action = action;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    private String action;
    private String deviceName;
    private String place;
    private boolean negation;


    public VoiceCommand(String action, String deviceName, String place, boolean negation) {
        this.action = action;
        this.deviceName = deviceName;
        this.place = place;
        this.negation = negation;
    }

    public VoiceCommand(ArrayList<String> rawCommand) {
        this.rawCommand = rawCommand;
        this.action = null;
        this.deviceName = null;
        this.place = null;
    }

    @Override
    public String toString() {
        return "CommandTemplate{" +
                "action='" + action + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", place='" + place + '\'' +
                ", negation=" + negation +
                '}';
    }
    public boolean isContainFullCommand()
    {
        //TODO przemyśleć jak załatwić przepuszczenie komendy gdy nie ma podanego miejsca, ale nazwa jest unikalana
        if(action != null && place != null && deviceName != null)
        {
            return true;
        }
        return false;
    }
}
