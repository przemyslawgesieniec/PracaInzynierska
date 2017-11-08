package com.gesieniec.przemyslaw.aviotsystemv001;

import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;

import java.util.ArrayList;

/**
 * Created by przem on 08.11.2017.
 */

public final class ApplicationContext {

    public static ArrayList<CommonDevice> getCommonDevices() {
        return commonDevices;
    }

    public static void setCommonDevices(ArrayList<CommonDevice> commonDevices) {
        ApplicationContext.commonDevices = commonDevices;
    }

    private static ArrayList<CommonDevice> commonDevices;
}
