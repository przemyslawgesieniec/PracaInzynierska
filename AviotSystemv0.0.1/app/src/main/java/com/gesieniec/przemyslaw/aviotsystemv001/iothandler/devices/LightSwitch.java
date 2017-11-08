package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices;

import java.util.ArrayList;

/**
 * Created by przem on 08.11.2017.
 */

public class LightSwitch extends CommonDevice {

    private ArrayList<Boolean> switchStatusList;
    private ArrayList<String> triggeringONCommandsENG;
    private ArrayList<String> triggeringOFFCommandsENG;
    private ArrayList<String> triggeringONCommandsPL;
    private ArrayList<String> triggeringOFFCommandsPL;

    public LightSwitch(ArrayList<Boolean> switchStatusList,String name, String location) {
        super(name,location);
        this.switchStatusList = switchStatusList;
        fillTriggeringCommandsList();

    }

    private void fillTriggeringCommandsList(){
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
}
