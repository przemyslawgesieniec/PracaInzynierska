package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import java.util.ArrayList;

/**
 * Created by przem on 01.11.2017.
 */

public final class CommandDataClass {

    /**
     * English embedded commands
     */
    private static ArrayList<String> defaultFullCommandsListENG;
    private static ArrayList<String> actionsListENG;
    private static ArrayList<String> placesListENG;
    private static ArrayList<String> devicesListENG;

    public static ArrayList<String> getNegationENG() {
        return negationENG;
    }

    private static ArrayList<String> negationENG;

    public static ArrayList<String> getDefaultFullCommandsListENG() {
        return defaultFullCommandsListENG;
    }

    public static ArrayList<String> getActionsListENG() {
        return actionsListENG;
    }

    public static ArrayList<String> getPlacesListENG() {
        return placesListENG;
    }

    public static ArrayList<String> getDevicesListENG() {
        return devicesListENG;
    }

    public static ArrayList<String> getDefaultFullCommandsListPL() {
        return defaultFullCommandsListPL;
    }

    public static ArrayList<String> getActionsListPL() {
        return actionsListPL;
    }

    public static ArrayList<String> getPlacesListPL() {
        return placesListPL;
    }

    public static ArrayList<String> getDevicesListPL() {
        return devicesListPL;
    }

    /**
     * Polish embedded commands
     */
    private static ArrayList<String> defaultFullCommandsListPL;
    private static ArrayList<String> actionsListPL;
    private static ArrayList<String> placesListPL;
    private static ArrayList<String> devicesListPL;

    public static ArrayList<String> getNegationPL() {
        return negationPL;
    }

    private static ArrayList<String> negationPL;



    public static void initializeCommandsData() {

        /**
         * English commands init
         */
        defaultFullCommandsListENG = new ArrayList<>();
        actionsListENG = new ArrayList<>();
        placesListENG = new ArrayList<>();
        devicesListENG = new ArrayList<>();
        negationENG = new ArrayList<>();
        fillDefaultFullCommandsListENG();
        fillActionsListENG();
        fillNegationListENG();


        /**
         * Polish commands init
         */
        defaultFullCommandsListPL = new ArrayList<>();
        actionsListPL = new ArrayList<>();
        placesListPL = new ArrayList<>();
        devicesListPL = new ArrayList<>();
        negationPL = new ArrayList<>();
        fillDefaultFullCommandsListPL();
        fillActionsListPL();
        fillNegationListPL();

        /**
         * stubs
         */
        fillstubsENG();
    }

    /**
     * English embedded commands
     */
    private static void fillDefaultFullCommandsListENG()
    {
        defaultFullCommandsListENG.add("Get status");
        defaultFullCommandsListENG.add("Check status");
        defaultFullCommandsListENG.add("Show status");
    }
    private static void fillActionsListENG()
    {
        actionsListENG.add("turn on");
        actionsListENG.add("turn off");
    }
    private static void fillNegationListENG(){
        negationENG.add("do not");
        negationENG.add("don't");
        negationENG.add("dont");
    }


    /**
     * Polish embedded commands
     */
    private static void fillDefaultFullCommandsListPL() {
        defaultFullCommandsListPL.add("Podaj status");
        defaultFullCommandsListPL.add("Sprawdź status");

    }
    private static void fillActionsListPL() {
        actionsListPL.add("włącz");
        actionsListPL.add("wyłącz");
    }
    private static void fillNegationListPL(){
        negationPL.add("nie");
    }



    /**
     * stubbed test commands
     */
    private static void fillstubsENG()
    {
        //actionsListENG.add("turn on");
        placesListENG.add("livingroom");
        placesListENG.add("garage");
        placesListENG.add("stairs");
        placesListENG.add("bedroom");
        devicesListENG.add("light");
        defaultFullCommandsListENG.add("turn off the light in the kitchen");
    }


}
