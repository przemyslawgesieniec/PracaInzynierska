package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceAction;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceType;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;

/**
 * Created by przem on 08.11.2017.
 */

public abstract class CommonDevice {


    /**
     * fields
     */
    protected String name;
    protected String location;
    protected String namePL;
    protected String locationPL;
    protected InetAddress deviceAddress;
    protected String macAddress;
    protected HashMap<String,DeviceAction> actionMapENG;
    protected HashMap<String,DeviceAction> actionMapPL;
    protected List<String> actionList;
    protected boolean isDataUpdated = false;
    /**
     * getters
     */
    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public InetAddress getDeviceAddress() {
        return deviceAddress;
    }
    public String getMacAddress() {
        return macAddress;
    }
    public String getNamePL() {
        return namePL;
    }
    public String getLocationPL() {
        return locationPL;
    }
    public List<String> getActionList() {
        return actionList;
    }

    /**
     * setters
     */
    public void setNamePL(String namePL) {
        this.namePL = namePL;
    }
    public void setLocationPL(String locationPL) {
        this.locationPL = locationPL;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }


    public CommonDevice(String name, String location,InetAddress deviceAddress,String macAddress) {
        this.name = name;
        this.location = location;
        this.deviceAddress = deviceAddress;
        this.macAddress = macAddress;
        Log.d("CommonDevice: ","New device:" );
    }
    public abstract DeviceType getDeviceType();
    public abstract HashMap<String, DeviceAction> getActionMapENG();
    public abstract HashMap<String, DeviceAction> getActionMapPL();
//    public abstract void updateCommonDataClass();
    public abstract String getMessageToSend(DeviceCapabilities capabilities);
    public abstract void updateDeviceWithCapabilities(DeviceCapabilities deviceCapabilities);
}
