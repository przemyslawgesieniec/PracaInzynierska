package com.gesieniec.przemyslaw.aviotsystemv001.iothandler;

/**
 * Created by przem on 15.12.2017.
 */

public class DeviceCapabilities {

    public DeviceCapabilities(String capabilities){
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
        deviceName = caps[1];
        macAddress = caps[3];
        messageType = caps[2];
        state = Boolean.parseBoolean(caps[5]);
        ipAddress = caps[0];
        deviceLocation = "kitchen";
        numberOfSwitches = Integer.valueOf(caps[4]);
    }

    private String deviceName;
    private String deviceLocation;
    private String ipAddress;
    private String macAddress;
    private String acctions;

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    private String messageType;
    private boolean state;
    private int ID;

    public int getNumberOfSwitches() {
        return numberOfSwitches;
    }

    public void setNumberOfSwitches(int numberOfSwitches) {
        this.numberOfSwitches = numberOfSwitches;
    }

    private int numberOfSwitches;


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getAcctions() {
        return acctions;
    }

    public void setAcctions(String acctions) {
        this.acctions = acctions;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


}
