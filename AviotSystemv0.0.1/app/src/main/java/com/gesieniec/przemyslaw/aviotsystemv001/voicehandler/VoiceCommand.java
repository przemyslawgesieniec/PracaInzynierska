package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by przem on 02.11.2017.
 */

public class VoiceCommand {

    public enum VoiceCommandType{
        INVALID,DEVICE_RELATED,SYSTEM_RELATED
    }
    /**
     * fields
     */
    private ArrayList<String> rawCommand;
    private String bestMatchCommand;
    private String deviceName;
    private String place;
    private boolean negation;
    private String action;
    private VoiceCommandType voiceCommandType;

    /**
     * getters
     */
    public String getAction() { return action; }
    public String getDeviceName() {
        return deviceName;
    }
    public String getPlace() {
        return place;
    }
    public boolean getNegation() { return negation; }
    public List<String> getRawCommand() {
        return rawCommand;
    }
    public String getBestMatchCommand() {
        return bestMatchCommand;
    }
    public VoiceCommandType getVoiceCommandType() {
        return voiceCommandType;
    }
    /**
     * setters
     */
    public void setRawCommand(ArrayList<String> rawCommand) {
        this.rawCommand = rawCommand;
    }
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
    public void setBestMatchCommand(String bestMatchCommand) {
        this.bestMatchCommand = bestMatchCommand;
    }
    public void setVoiceCommandType(VoiceCommandType voiceCommandType) {
        this.voiceCommandType = voiceCommandType;
    }

    /**
     * methods
     */
    public VoiceCommand(ArrayList<String> rawCommand) {
        this.rawCommand = rawCommand;
        this.action = null;
        this.deviceName = null;
        this.place = null;
        voiceCommandType = VoiceCommandType.INVALID;
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


}
