package com.gesieniec.przemyslaw.aviotsystemv001.systemhandler;

import java.util.ArrayList;

/**
 * Created by przem on 01.11.2017.
 */

public final class CommandDataClass {



    /**
     * English embedded commands
     */

    private static ArrayList<String> systemStatusCommandsENG;



    private static ArrayList<String> singlePurposeCommandsENG;

    private static ArrayList<String> actionsListENG;
    private static ArrayList<String> placesListENG;
    private static ArrayList<String> devicesListENG;
    private static ArrayList<String> negationENG;


    /**
     * Polish embedded commands
     */
    private static ArrayList<String> systemStatusCommandsPL;
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
    public static ArrayList<String> getSystemStatusCommandsENG() {
        return systemStatusCommandsENG;
    }
    public static ArrayList<String> getSinglePurposeCommandsENG() {
        return singlePurposeCommandsENG;
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
    public static ArrayList<String> getSystemStatusCommandsPL() {
        return systemStatusCommandsPL;
    }


    public static void initializeCommandsData() {

        /**
         * English commands init
         */
        systemStatusCommandsENG = new ArrayList<>();
        singlePurposeCommandsENG = new ArrayList<>();
        actionsListENG = new ArrayList<>();
        placesListENG = new ArrayList<>();
        devicesListENG = new ArrayList<>();
        negationENG = new ArrayList<>();
        fillDefaultFullCommandsListENG();
        fillNegationListENG();
        fillSinglePuropseCommandsENG();


        /**
         * Polish commands init
         */
        systemStatusCommandsPL = new ArrayList<>();
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
        systemStatusCommandsENG.add("get status");
        systemStatusCommandsENG.add("check status");
        systemStatusCommandsENG.add("show status");
        systemStatusCommandsENG.add("show connected devices");
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
        systemStatusCommandsPL.add("podaj status");
        systemStatusCommandsPL.add("sprawdź status");
        systemStatusCommandsPL.add("pokaż status");
        systemStatusCommandsPL.add("pokaż połączone urządzenia");
        systemStatusCommandsPL.add("pokaż podłączone urządzenia");
        systemStatusCommandsPL.add("pokaż wszystkie urządzenia");
    }
    private static void fillNegationListPL(){
        negationPL.add("nie");
    }

    private static void fillSinglePuropseCommandsENG(){
        singlePurposeCommandsENG.add("");
    }




}
