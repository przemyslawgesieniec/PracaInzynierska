package com.gesieniec.przemyslaw.aviotsystemv001.systemhandler;

import android.util.Log;

import com.gesieniec.przemyslaw.aviotsystemv001.VoiceControlActivity;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceHandler;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.CommonDevice;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.devices.LightSwitch;
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


    public ApplicationContext(VoiceControlActivity voiceControlActivity) {
        /**
         * Initialize all commands available in application
         */
        CommandDataClass.initializeCommandsData();
        deviceHandler = new DeviceHandler();
        voiceRecognition = new VoiceRecognition(voiceControlActivity);
        commonDevices = new ArrayList<>();
        systemCommandHandler = new SystemCommandHandler();
        //TODO : remove STUB
        STUBDEVICEMETHOD();
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
    public static void addCommonDevices(CommonDevice commonDevice) {
        ApplicationContext.commonDevices.add(commonDevice);
    }


    /**
     * STUBS
     */
    //TODO: remove this after tests
    private void STUBDEVICEMETHOD(){
        InetAddress address = null;
        try {
            address = InetAddress.getByAddress(new byte[] {
                    (byte)192, (byte)168, (byte)1, (byte)128});
            CommonDevice ls = new LightSwitch("light","kitchen",address); //TODO OGARNAC CZEMU MAM TU NULL W ADRESIE !
            addCommonDevices(ls);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(address!=null) Log.d("ApplicationContext","STUB device added");
    }
}
