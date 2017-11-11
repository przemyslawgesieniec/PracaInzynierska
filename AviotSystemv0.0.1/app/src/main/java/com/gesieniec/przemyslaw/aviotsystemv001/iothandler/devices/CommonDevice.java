package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceAction;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceType;

import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by przem on 08.11.2017.
 */

public abstract class CommonDevice {
    /**
     * fields
     */
    protected String name;
    protected String location;
    protected InetAddress deviceAddress;
    protected HashMap<String,DeviceAction> actionMapENG;
    protected HashMap<String,DeviceAction> actionMapPL;

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

    public CommonDevice(String name, String location,InetAddress deviceAddress) {
        this.name = name;
        this.location = location;
        this.deviceAddress = deviceAddress;
    }
    public abstract DeviceType getDeviceType();
    public abstract HashMap<String, DeviceAction> getActionMapENG();
    public abstract HashMap<String, DeviceAction> getActionMapPL();

  //  protected abstract void fillTriggeringCommandsList();
}
