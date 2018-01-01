package com.gesieniec.przemyslaw.aviotsystemv001.systemhandler;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.MainActivity;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.LightSwitch;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.messagehandler.BroadcastListener;
import com.gesieniec.przemyslaw.aviotsystemv001.voicehandler.VoiceRecognition;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by przem on 08.11.2017.
 */

public final class ApplicationContext {



    /**
     * fields
     */
    public enum Language{
        PL_POLISH,
        ENG_ENGLISH
    }

    private Language systemLanguage = Language.ENG_ENGLISH;

    private DeviceHandler deviceHandler;
    private VoiceRecognition voiceRecognition;
    private static ArrayList<CommonDevice> commonDevices;
    private SystemCommandHandler systemCommandHandler;


    public ApplicationContext(MainActivity mainActivity) {
        /**
         * Initialize all commands available in application
         */
        CommandDataClass.initializeCommandsData();
        deviceHandler = new DeviceHandler();
        voiceRecognition = new VoiceRecognition(mainActivity);
        commonDevices = new ArrayList<>();
        systemCommandHandler = new SystemCommandHandler();

        //TODO: move to taskDispatcher (on application start)
        BroadcastListener asyncTask = new BroadcastListener();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){ // Above Api Level 13
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{ // Below Api Level 13
            asyncTask.execute();
        }

    }

    /**
     * getters
     */
    public static ArrayList<CommonDevice> getCommonDevices() {
        return commonDevices;
    }
    public Language getSystemLanguage() {
        return systemLanguage;
    }
    
    public VoiceRecognition getVoiceRecognition() {
        return voiceRecognition;
    }

    /**
     * methods
     */
    public static void addCommonDevice(CommonDevice commonDevice) {
        ApplicationContext.commonDevices.add(commonDevice);
    }
    public void setSystemLanguage(Language systemLanguage) {
        this.systemLanguage = systemLanguage;
    }

}
