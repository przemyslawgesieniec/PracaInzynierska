package com.gesieniec.przemyslaw.aviotsystemv001.view.devices;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.gesieniec.przemyslaw.aviotsystemv001.R;
import com.gesieniec.przemyslaw.aviotsystemv001.iothandler.DeviceCapabilities;
import com.gesieniec.przemyslaw.aviotsystemv001.taskdispatcher.TaskDispatcher;

/**
 * Created by przem on 26.11.2017.
 */

public class DeviceInstanceFragment extends android.support.v4.app.Fragment {

    private String capabilities;
    private int deviceID;
    private boolean detailsState = false;
    private boolean editingState = false;

    LinearLayout ll;
    LinearLayout l2;
    LinearLayout l3;
    LinearLayout l4;
    LinearLayout l5;

    Switch s;
    ImageButton imgBtn;
    Button editBtn;
    Button saveBtn;
    ImageButton closeBtn;

    TextView deviceName;
    TextView deviceLocation;

    TextInputLayout deviceNameInput;
    TextInputLayout deviceLocationInput;

    DeviceCapabilities deviceCapabilities;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** Inflating the layout for this fragment **/
        capabilities = getArguments().getString("capabilities");
        deviceID = getArguments().getInt("fragmentID");
        deviceCapabilities = new DeviceCapabilities(getArguments().getString("capabilities"));
        View v = inflater.inflate(R.layout.device_instance_fragment, null);

        setViewElements(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.view = view;
        view.findViewById(R.id.deviceSettings).setVisibility(RelativeLayout.GONE);
        Log.d("onCreateView.getId();", "" + view.getId());
        super.onViewCreated(view, savedInstanceState);
        ll = (LinearLayout) view.findViewById(R.id.switchContainer);
        l2 = (LinearLayout) view.findViewById(R.id.horizontalSpace);
        l3 = (LinearLayout) view.findViewById(R.id.detailsContainer);
        l4 = (LinearLayout) view.findViewById(R.id.mainFragmentContainer);
        l5 = (LinearLayout) view.findViewById(R.id.settingsContainer);


        view.findViewById(R.id.detailsContainer).setVisibility(LinearLayout.GONE);

        /**
         * Edit Fields
         */

        insertEditFields();


        /**
         * State switch
         */
        s = new Switch(getActivity());
        s.setGravity(Gravity.FILL_VERTICAL);
        s.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        s.setChecked(deviceCapabilities.getState());
        s.setId(deviceCapabilities.getID());
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceCapabilities.setState(!deviceCapabilities.getState());
                Log.d("button state ", String.valueOf(deviceCapabilities.getState()));
                TaskDispatcher.newTask(TaskDispatcher.GuiTaskContext.SWITCH_STATE_CHANGED, deviceCapabilities);
            }
        });
        ll.addView(s);

        /**
         * Details dropdown arrow  button
         */


        //TODO : optymalizacja kodu onCLICK !
        imgBtn = new ImageButton(getActivity());
        imgBtn.setBackgroundColor(Color.TRANSPARENT);
        imgBtn.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        imgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (detailsState) {
                    view.findViewById(R.id.detailsContainer).setVisibility(LinearLayout.GONE);
                    float deg = v.getRotation() + 180F;
                    v.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
                    detailsState = false;
                } else {
                    view.findViewById(R.id.detailsContainer).setVisibility(LinearLayout.VISIBLE);
                    float deg = v.getRotation() + 180F;
                    v.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
                    detailsState = true;
                }

            }
        });
        l2.addView(imgBtn, 0);

        /**
         * Edit button
         */

        editBtn = new Button(getActivity());
        editBtn.setText("Edit");
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDeviceFragmentData();
            }
        });
        l3.addView(editBtn);


        /**
         * Save button
         */
        saveBtn = (Button) view.findViewById(R.id.btn_saveChanges);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hahahah", "hahahah");
            }
        });

        /**
         *  Close Btn
         */
        closeBtn = (ImageButton) view.findViewById(R.id.settingsCloseBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.deviceSettings).setVisibility(RelativeLayout.GONE);
                view.findViewById(R.id.contentContainer).setVisibility(LinearLayout.VISIBLE);
            }
        });
    }

    private void insertEditFields() {


    }

    private void editDeviceFragmentData() {
        view.findViewById(R.id.deviceSettings).setVisibility(RelativeLayout.VISIBLE);
        view.findViewById(R.id.contentContainer).setVisibility(LinearLayout.GONE);
    }


    private void setViewElements(View v) {
        deviceName = ((TextView) (v.findViewById(R.id.deviceName)));
        deviceName.setText(deviceCapabilities.getDeviceName());

        deviceLocation = ((TextView) (v.findViewById(R.id.deviceLocation)));
        deviceLocation.setText(deviceCapabilities.getDeviceLocation());

        ((TextView) (v.findViewById(R.id.ipAddress))).append(deviceCapabilities.getIpAddress());
        ((TextView) (v.findViewById(R.id.macAddress))).append(deviceCapabilities.getMacAddress());

    }

}
