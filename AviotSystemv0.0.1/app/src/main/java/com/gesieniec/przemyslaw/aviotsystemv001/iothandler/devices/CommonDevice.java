package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceAction;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
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
    protected String macAddress;
    protected HashMap<String,DeviceAction> actionMapENG;
    protected HashMap<String,DeviceAction> actionMapPL;

    /**+
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
    public abstract void updateCommonDataClass();
    public abstract String getMessageToSend(DeviceCapabilities capabilities);
    public abstract void updateDeviceWithCapabilities(DeviceCapabilities deviceCapabilities);
}
