package com.gesieniec.przemyslaw.aviotsystemv001.view.devices;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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

public class DeviceInstanceFragment extends android.support.v4.app.Fragment{

    private String capabilities;
    private boolean detailsState = false;

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
        view.findViewById(R.id.detailsContainer).setVisibility(LinearLayout.GONE);

        /**
         * State switch
         */
        Switch s = new Switch(getActivity());
        s.setGravity(Gravity.FILL_VERTICAL);
        s.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        s.setChecked(deviceCapabilities.getState());
        s.setId(deviceCapabilities.getID());
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceCapabilities.setState(!deviceCapabilities.getState());
                Log.d("button state ",String.valueOf(deviceCapabilities.getState()));
                TaskDispatcher.newTask(TaskDispatcher.GuiTaskContext.SWITCH_STATE_CHANGED,deviceCapabilities);
            }
        });
        ll.addView(s);

        /**
         * Details dropdown arrow  button
         */


        //TODO : optymalizacja kodu onCLICK !
        ImageButton imgBtn = new ImageButton(getActivity());
        //imgBtn.setId(deviceCapabilities.getID());
        imgBtn.setBackgroundColor(Color.TRANSPARENT);
        imgBtn.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        imgBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(detailsState){
                    view.findViewById(R.id.detailsContainer).setVisibility(LinearLayout.GONE);
                    float deg = v.getRotation() + 180F;
                    v.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
                    detailsState = false;
                }
                else {
                    view.findViewById(R.id.detailsContainer).setVisibility(LinearLayout.VISIBLE);
                    float deg = v.getRotation() + 180F;
                    v.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
                    detailsState = true;
                }

            }
        });
        ll.addView(imgBtn,0);


    }
    private void setViewElements(View v){
        ((TextView)(v.findViewById(R.id.deviceName))).setText(deviceCapabilities.getDeviceName());
        ((TextView)(v.findViewById(R.id.ipAddress))).append(deviceCapabilities.getIpAddress());
        ((TextView)(v.findViewById(R.id.macAddress))).append(deviceCapabilities.getMacAddress());

    }


}
