package com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices;

/**
 * Created by przem on 08.11.2017.
 */

public class CommonDevice {
    protected String name;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    protected String location;

    public CommonDevice(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
