package com.gesieniec.przemyslaw.aviotsystemv001.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gesieniec.przemyslaw.aviotsystemv001.R;

/**
 * Created by przem on 26.11.2017.
 */

public class DeviceInstanceFragment extends android.support.v4.app.Fragment{

    private String deviceName;
    private String deviceLocation;
    private String ipAddress;
    private String macAddress;
    private String acctions;
    private String capabilities;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        capabilities = getArguments().getString("capabilities");
        Log.d("DeviceInstance caps:","asd: "+capabilities);
        extractCapabilities();
        v = inflater.inflate(R.layout.device_instance_fragment, null);
        setViewElements(v);

        return v;
    }
    private void extractCapabilities(){
        String[] caps = capabilities.split(";");
        deviceName = caps[0];
        macAddress = caps[1];
        ipAddress = caps[2];
        deviceLocation = "none";

    }
    private void setViewElements(View v){
        ((TextView)(v.findViewById(R.id.deviceName))).setText(deviceName);
        ((TextView)(v.findViewById(R.id.ipAddress))).append(ipAddress);
        ((TextView)(v.findViewById(R.id.macAddress))).append(macAddress);
    }


}
