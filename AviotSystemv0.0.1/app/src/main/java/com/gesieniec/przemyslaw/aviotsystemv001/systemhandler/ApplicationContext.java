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

        //TODO : remove STUB
       // STUBDEVICEMETHOD();
    }

    /**
     * getters
     */
    public static ArrayList<CommonDevice> getCommonDevices() {
        return commonDevices;
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


    /**
     * STUBS
     */
    //TODO: remove this after tests
    private void STUBDEVICEMETHOD() {
        InetAddress address = null;
        try {
            address = InetAddress.getByAddress(new byte[]{
                    (byte) 192, (byte) 168, (byte) 1, (byte) 101});
//            address = InetAddress.getByAddress(new byte[] {
//                    (byte)172, (byte)217, (byte)23, (byte)164});
            CommonDevice ls = new LightSwitch("light", "kitchen", address,"iljbdflijasbdfjasbdf"); //TODO OGARNAC CZEMU MAM TU NULL W ADRESIE !
            addCommonDevice(ls);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (address != null) Log.d("ApplicationContext", "STUB device added");
    }
}
