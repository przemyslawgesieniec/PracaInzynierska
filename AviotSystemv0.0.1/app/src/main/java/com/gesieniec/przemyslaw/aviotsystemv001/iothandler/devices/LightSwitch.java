package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceAction;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceType;
import com.gesieniec.przemyslaw.aviotsystemv001.systemhandler.CommandDataClass;

import java.net.InetAddress;
import java.util.HashMap;

/**
 * Created by przem on 08.11.2017.
 */

public class LightSwitch extends CommonDevice {


    /**
     * fields
     */
    private boolean state;
    private DeviceType type;
    private boolean isUpdated = false;

    /**
     * getters
     */
    public HashMap<String, DeviceAction> getActionMapENG() {
        return actionMapENG;
    }

    public HashMap<String, DeviceAction> getActionMapPL() {
        return actionMapPL;
    }

    @Override
    public DeviceType getDeviceType() {
        return type;
    }

    public boolean getState() {
        return state;
    }

    /**
     * setters
     */
    public void setState(boolean state) {
        this.state = state;
    }


    /**
     * ctor
     */
    public LightSwitch(String name, String location, InetAddress deviceAddress, String macAddress, boolean state) {
        super(name, location, deviceAddress, macAddress);
        //TODO: odhardkodować !!!
        namePL = "światło";
        locationPL = "pokoju";
        type = DeviceType.SWITCH;
        this.state = state;
        actionMapENG = new HashMap<>();
        actionMapPL = new HashMap<>();
        fillActionMap();
        updateCommonDataClass();
    }

    /**
     * methods
     */
    private void fillActionMap() {
        /**
         * English Commands ON
         */
        actionMapENG.put("turn on", DeviceAction.ON);
        actionMapENG.put("switch on", DeviceAction.ON);
        actionMapENG.put("illuminate", DeviceAction.ON);
        /**
         * English Commands OFF
         */
        actionMapENG.put("turn of", DeviceAction.OFF);
        actionMapENG.put("switch off", DeviceAction.OFF);
        /**
         * Polish Commands ON
         */
        actionMapPL.put("włącz", DeviceAction.ON);
        actionMapPL.put("oświetl", DeviceAction.ON);
        actionMapPL.put("zapal", DeviceAction.ON);
        /**
         * Polish Commands OFF
         */
        actionMapPL.put("wyłącz", DeviceAction.OFF);
        actionMapPL.put("zgaś", DeviceAction.OFF);
    }

    @Override
    public String toString() {
        return "LightSwitch";
    }

    @Override
    public void updateCommonDataClass() {

        CommandDataClass.getActionsListENG().addAll(actionMapENG.keySet());
        CommandDataClass.getDevicesListENG().add(name);
        CommandDataClass.getPlacesListENG().add(location);

        CommandDataClass.getActionsListPL().addAll(actionMapPL.keySet());
        CommandDataClass.getDevicesListPL().add(namePL);
        CommandDataClass.getPlacesListPL().add(locationPL);
        //TODO: Polish commands
    }

    @Override
    public String getMessageToSend(DeviceCapabilities capabilities) { //TODO: rozbic na state update i na getmessage to send
        return getMessageBasedOnCurrentState();
    }

    @Override
    public void updateDeviceWithCapabilities(DeviceCapabilities deviceCapabilities) {
        Log.d("LightSwitch", "updateDeviceWithCapabilities");
        isUpdated = false;
        isDataUpdated = false;
        if (state != deviceCapabilities.getStates().get(0)) {

            Log.d("LightSwitch", "updateDeviceWithCapabilities update state");
            Log.d("mstate", ""+state);
            Log.d("getState", deviceCapabilities.getStates().get(0).toString());

            state = deviceCapabilities.getStates().get(0);
            isUpdated = true;
        }
        if (!name.equals(deviceCapabilities.getDeviceName())) {

            Log.d("LightSwitch", "updateDeviceWithCapabilities update name");
            Log.d("mname", ""+name);
            Log.d("getName", deviceCapabilities.getDeviceName());
            name = deviceCapabilities.getDeviceName();
            isDataUpdated = true;
        }
        if (!location.equals(deviceCapabilities.getDeviceLocation())) {
            Log.d("LightSwitch", "updateDeviceWithCapabilities update location");
            Log.d("mlocation", ""+location);
            Log.d("getlocation", deviceCapabilities.getDeviceLocation());
            location = deviceCapabilities.getDeviceLocation();


            isDataUpdated = true;
        }
    }

    private String getMessageBasedOnCurrentState() {
        if (isUpdated) {
            Log.d("LightSwitch", "switch state updated");
            String action = "OFF";
            if (state) {
                Log.d("LightSwitch", "state true");
                action = "ON";
            }
            isUpdated = false;
            return toString() + action;
        }
        if(isDataUpdated){
            Log.d("isDataUpdated", ""+isDataUpdated);
            String action = "UpdateDeviceData;";
            action+=name;
            action+=";";
            action+=location;
            action+=";";
            return action;
        }
        isDataUpdated = false;
        return null;
    }


}
