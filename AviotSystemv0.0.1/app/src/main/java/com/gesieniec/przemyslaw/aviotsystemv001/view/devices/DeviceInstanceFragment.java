package com.gesieniec.przemyslaw.aviotsystemv001.view.devices;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.gesieniec.przemyslaw.aviotsystemv001.R;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;

/**
 * Created by przem on 26.11.2017.
 */

public class DeviceInstanceFragment extends android.support.v4.app.Fragment{

    private String capabilities;

      DeviceCapabilities deviceCapabilities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        capabilities = getArguments().getString("capabilities");
        deviceCapabilities = new DeviceCapabilities(getArguments().getString("capabilities"));
        Log.d("DeviceInstance caps:","caps: "+capabilities);
        View v = inflater.inflate(R.layout.device_instance_fragment, null);


        setViewElements(v);
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout ll =(LinearLayout) view.findViewById(R.id.switchContainer);
        Switch s = new Switch(getActivity());
        s.setGravity(Gravity.FILL_VERTICAL);
        s.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        s.setChecked(deviceCapabilities.getState());
        s.setId(deviceCapabilities.getID());
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDispatcher.newTask(TaskDispatcher.GuiTaskContext.SWITCH_STATE_CHANGED,deviceCapabilities);
            }
        });
        ll.addView(s);
    }
    private void setViewElements(View v){
        ((TextView)(v.findViewById(R.id.deviceName))).setText(deviceCapabilities.getDeviceName());
        ((TextView)(v.findViewById(R.id.ipAddress))).append(deviceCapabilities.getIpAddress());
        ((TextView)(v.findViewById(R.id.macAddress))).append(deviceCapabilities.getMacAddress());

    }


}
