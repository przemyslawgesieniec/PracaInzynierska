package com.gesieniec.przemyslaw.aviotsystemv001.iothandler;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by przem on 15.12.2017.
 */

public class DeviceCapabilities {

    public DeviceCapabilities(String capabilities) {
        /**
         * caps(0) - ipAddress
         * caps(1) - device name
         * caps(2) - messageType
         * caps(3) - macAddress
         * caps(4) - number of switches
         * caps(5-n) - switch state
         * Extentions:
         */

        String[] caps = capabilities.split(";");
        try {
            ipAddress = caps[0];
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            deviceType = caps[1];
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            messageType = caps[2];
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            deviceName = caps[3];
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            deviceLocation = caps[4];
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            macAddress = caps[5];
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
        try {
            numberOfSwitches = Integer.valueOf(caps[6]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
        }
        catch (Exception e) {
        }

        states = new ArrayList<>();
        for(int i=0;i<numberOfSwitches;i++){
            try {
                states.add(Boolean.parseBoolean(caps[7+i]));
                Log.d("ASDFASD states;", String.valueOf(states.get(i)));
            }
            catch (ArrayIndexOutOfBoundsException e) {
            }
        }


        Log.d("ASDFASD ipAddress;",ipAddress);
        Log.d("ASDFASD deviceType;",deviceType);
        Log.d("ASDFASD messageType;",messageType);
        Log.d("ASDFASD deviceName;",deviceName);
        Log.d("ASDFASD deviceLocation;",deviceLocation);
        Log.d("ASDFASD macAddress;",macAddress);



}

    private String ipAddress;
    private String deviceType;
    private String messageType;
    private String deviceName;
    private String deviceLocation;
    private String macAddress;
    private List<Boolean> states;
    private int numberOfSwitches;



    private int ID;


    /**
     * getters
     */
    public int getID() {
        return ID;
    }
    public String getIpAddress() {
        return ipAddress;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public String getMacAddress() {
        return macAddress;
    }


    public List<Boolean> getStates() {
        return states;
    }

    public int getNumberOfSwitches() {
        return numberOfSwitches;
    }

    /**
     * setters
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setStates(List<Boolean> states) {
        this.states = states;
    }

}
