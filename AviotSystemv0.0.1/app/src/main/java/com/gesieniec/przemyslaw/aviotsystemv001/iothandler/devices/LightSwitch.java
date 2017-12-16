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

    /**
     * getters
     */
    public HashMap<String, DeviceAction> getActionMapENG() { return actionMapENG;}
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
    public LightSwitch(String name, String location, InetAddress deviceAddress, String macAddress,boolean state ) {
        super(name,location,deviceAddress,macAddress);
        Log.d("LightSwitch: ","NEW LIGHT SWITCH" );
        type = DeviceType.SWITCH;
        this.state = state;
        actionMapENG = new HashMap<>();
        actionMapPL = new HashMap<>();
        fillActionMap();
        updateCommonDataClass();
    }
    /**
     * methods
     */    private void fillActionMap(){
        /**
         * English Commands ON
         */
        actionMapENG.put("turn on",DeviceAction.ON);
        actionMapENG.put("switch on",DeviceAction.ON);
        actionMapENG.put("illuminate",DeviceAction.ON);
        /**
         * English Commands OFF
         */
        actionMapENG.put("turn of",DeviceAction.OFF);
        actionMapENG.put("switch off",DeviceAction.OFF);
        /**
         * Polish Commands ON
         */
        actionMapPL.put("włącz",DeviceAction.ON);
        actionMapPL.put("oświetl",DeviceAction.ON);
        actionMapPL.put("zapal",DeviceAction.ON);
        /**
         * Polish Commands OFF
         */
        actionMapPL.put("wyłącz",DeviceAction.OFF);
        actionMapPL.put("zgaś",DeviceAction.OFF);
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

        Log.d("UCD name: ",CommandDataClass.getDevicesListENG().get(0));
        Log.d("UCD location: ",CommandDataClass.getPlacesListENG().get(0));

        //TODO: Polish commands
    }

    @Override
    public String getMessageToSend(DeviceCapabilities capabilities) { //TODO: rozbic na state update i na getmessage to send
         if(capabilities.getMessageType().equals("stateupdate")){
             if(state != capabilities.getState()){
                 state = capabilities.getState();
             }
         }
         return getMessageBasedOnCurrentState();
    }
    private String getMessageBasedOnCurrentState(){
         String action = "OFF";
         if(state){
             action = "ON";
         }
        return toString()+action;
    }


}
