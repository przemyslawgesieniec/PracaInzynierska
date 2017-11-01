package com.gesieniec.przemyslaw.aviotsystemv001.voicehandler;

import java.util.ArrayList;

/**
 * Created by przem on 01.11.2017.
 */

final class CommandDataClass {

    /**
     * English embedded commands
     */
    private static ArrayList<String> defaultFullCommandsListENG;
    private static ArrayList<String> actionsListENG;
    private static ArrayList<String> placesListENG;
    private static ArrayList<String> devicesListENG;

    /**
     * Polish embedded commands
     */
    private static ArrayList<String> defaultFullCommandsListPL;
    private static ArrayList<String> actionsListPL;
    private static ArrayList<String> placesListPL;
    private static ArrayList<String> devicesListPL;



    public CommandDataClass() {
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
    private void fillDefaultFullCommandsListENG()
    {
        defaultFullCommandsListENG.add("Get status");
        defaultFullCommandsListENG.add("Check status");
        defaultFullCommandsListENG.add("Show status");
    }
    private void fillActionsListENG()
    {
        actionsListENG.add("turn on");
        actionsListENG.add("turn off");
    }

    /**
     * Polish embedded commands
     */
    private void fillDefaultFullCommandsListPL() {
        defaultFullCommandsListPL.add("Podaj status");
        defaultFullCommandsListPL.add("Sprawdź status");

    }
    private void fillActionsListPL() {
        actionsListPL.add("włącz");
        actionsListPL.add("wyłącz");
    }

}
