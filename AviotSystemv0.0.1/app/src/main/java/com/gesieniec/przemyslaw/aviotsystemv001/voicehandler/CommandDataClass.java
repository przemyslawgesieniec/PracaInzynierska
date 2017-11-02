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



    public static void initializeCommandsData() {
        defaultFullCommandsListENG = new ArrayList<>();
        actionsListENG = new ArrayList<>();
        placesListENG = new ArrayList<>();
        devicesListENG = new ArrayList<>();
        fillDefaultFullCommandsListENG();
        fillActionsListENG();

        defaultFullCommandsListPL = new ArrayList<>();
        actionsListPL = new ArrayList<>();
        placesListPL = new ArrayList<>();
        devicesListPL = new ArrayList<>();
        fillDefaultFullCommandsListPL();
        fillActionsListPL();
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

}
