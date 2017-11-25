package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceAction;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceType;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by przem on 08.11.2017.
 */

public class MulitLightSwitch extends CommonDevice {

    private ArrayList<Boolean> switchStatusList;
    private ArrayList<String> triggeringONCommandsENG;
    private ArrayList<String> triggeringOFFCommandsENG;
    private ArrayList<String> triggeringONCommandsPL;
    private ArrayList<String> triggeringOFFCommandsPL;

    public MulitLightSwitch(ArrayList<Boolean> switchStatusList, String name, String location, InetAddress deviceAddress,String macAddress) {
        super(name,location,deviceAddress,macAddress);
        this.switchStatusList = switchStatusList;
        fillTriggeringCommandsList();

    }

    protected void fillTriggeringCommandsList(){
        /**
         * English Commands ON
         */
        triggeringONCommandsENG.add("turn on");
        triggeringONCommandsENG.add("switch on");
        triggeringONCommandsENG.add("illuminate");
        /**
         * English Commands OFF
         */
        triggeringOFFCommandsENG.add("switch off");
        triggeringOFFCommandsENG.add("turn off");
        /**
         * Polish Commands ON
         */
        triggeringONCommandsPL.add("włącz");
        triggeringONCommandsPL.add("oświetl");
        triggeringONCommandsPL.add("zapal");
        /**
         * Polish Commands OFF
         */
        triggeringOFFCommandsPL.add("wyłacz");
        triggeringOFFCommandsPL.add("zgaś");
    }

    @Override
    public String toString() {
        return "MultiLightSwitch";
    }

    @Override
    public DeviceType getDeviceType() {
        return null;
    }

    @Override
    public HashMap<String, DeviceAction> getActionMapENG() {
        return null;
    }

    @Override
    public HashMap<String, DeviceAction> getActionMapPL() {
        return null;
    }

    @Override
    public void updateCommonDataClass() {
        //TODO: implement
    }
}
