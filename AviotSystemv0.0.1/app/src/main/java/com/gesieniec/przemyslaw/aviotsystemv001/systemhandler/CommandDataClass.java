package com.gesieniec.przemyslaw.aviotsystemv001.systemhandler;

import java.util.ArrayList;

/**
 * Created by przem on 01.11.2017.
 */

public final class CommandDataClass {



    /**
     * English embedded commands
     */
    private static ArrayList<String> systemCommandsENG;
    private static ArrayList<String> actionsListENG;
    private static ArrayList<String> placesListENG;
    private static ArrayList<String> devicesListENG;
    private static ArrayList<String> negationENG;

    /**
     * Polish embedded commands
     */
    private static ArrayList<String> systemCommandsPL;
    private static ArrayList<String> actionsListPL;
    private static ArrayList<String> placesListPL;
    private static ArrayList<String> devicesListPL;
    private static ArrayList<String> negationPL;

    /**
     * Getters for english commands
     */
    public static ArrayList<String> getActionsListENG() {
        return actionsListENG;
    }
    public static ArrayList<String> getPlacesListENG() {
        return placesListENG;
    }
    public static ArrayList<String> getDevicesListENG() {
        return devicesListENG;
    }
    public static ArrayList<String> getNegationENG() {
        return negationENG;
    }
    public static ArrayList<String> getSystemCommandsENG() {
        return systemCommandsENG;
    }

    /**
     * Getters for polish commands
     */
    public static ArrayList<String> getActionsListPL() {
        return actionsListPL;
    }
    public static ArrayList<String> getPlacesListPL() {
        return placesListPL;
    }
    public static ArrayList<String> getDevicesListPL() {
        return devicesListPL;
    }
    public static ArrayList<String> getNegationPL() {
        return negationPL;
    }
    public static ArrayList<String> getSystemCommandsPL() {
        return systemCommandsPL;
    }


    public static void initializeCommandsData() {

        /**
         * English commands init
         */
        systemCommandsENG = new ArrayList<>();
        actionsListENG = new ArrayList<>();
        placesListENG = new ArrayList<>();
        devicesListENG = new ArrayList<>();
        negationENG = new ArrayList<>();
        fillDefaultFullCommandsListENG();
        fillNegationListENG();


        /**
         * Polish commands init
         */
        systemCommandsPL = new ArrayList<>();
        actionsListPL = new ArrayList<>();
        placesListPL = new ArrayList<>();
        devicesListPL = new ArrayList<>();
        negationPL = new ArrayList<>();
        fillDefaultFullCommandsListPL();
        fillNegationListPL();

    }

    /**
     * English embedded commands
     */
    private static void fillDefaultFullCommandsListENG()
    {
        systemCommandsENG.add("get status");
        systemCommandsENG.add("check status");
        systemCommandsENG.add("show status");
        systemCommandsENG.add("show connected devices");
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
        systemCommandsPL.add("podaj status");
        systemCommandsPL.add("sprawdź status");
        systemCommandsPL.add("pokaż status");
        systemCommandsPL.add("pokaż połączone urządzenia");
        systemCommandsPL.add("pokaż podłączone urządzenia");
        systemCommandsPL.add("pokaż wszystkie urządzenia");
    }
    private static void fillNegationListPL(){
        negationPL.add("nie");
    }




}
