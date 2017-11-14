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
        //fillActionsListENG();
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
        //fillActionsListPL();
        fillNegationListPL();

        /**
         * stubs
         */
        //fillstubsENG();
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
        systemCommandsENG.add("handle both languages");
        systemCommandsENG.add("switch to polish");
        systemCommandsENG.add("switch to english");

    }
//    private static void fillActionsListENG()
//    {
//        actionsListENG.add("turn on");
//        actionsListENG.add("turn off");
//    }
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

        //TODO: split this
        systemCommandsPL.add("przejdź na polski");
        systemCommandsPL.add("przejdź na angielski");
        systemCommandsPL.add("zmień na polski");
        systemCommandsPL.add("zmień na angielski");
        systemCommandsPL.add("przełącz na polski");
        systemCommandsPL.add("przełącz na angielski");


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
        actionsListENG.add("turn on");
        placesListENG.add("living room");
        placesListENG.add("garage");
        placesListENG.add("stairs");
        placesListENG.add("kitchen");
        devicesListENG.add("light");

    }


}
