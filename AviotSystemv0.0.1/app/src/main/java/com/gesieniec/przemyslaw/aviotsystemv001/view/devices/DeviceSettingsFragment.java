package com.gesieniec.przemyslaw.aviotsystemv001.view.devices;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.gesieniec.przemyslaw.aviotsystemv001.R;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;

/**
 * Created by przem on 26.11.2017.
 */

public class DeviceSettingsFragment extends android.support.v4.app.Fragment {

    DeviceCapabilities deviceCapabilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.device_settings_fragment, null);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }


}
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        /** Inflating the layout for this fragment **/
//        capabilities = getArguments().getString("capabilities");
//        deviceID = getArguments().getInt("fragmentID");
//        deviceCapabilities = new DeviceCapabilities(getArguments().getString("capabilities"));
//        Log.d("DeviceInstance caps:","caps: "+capabilities);
//        View v = inflater.inflate(R.layout.device_instance_fragment, null);
//
//
//        setViewElements(v);
//        return v;
//    }