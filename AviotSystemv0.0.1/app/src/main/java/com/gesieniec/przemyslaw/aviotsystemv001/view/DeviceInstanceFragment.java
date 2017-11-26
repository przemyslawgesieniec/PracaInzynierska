package com.gesieniec.przemyslaw.aviotsystemv001.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gesieniec.przemyslaw.aviotsystemv001.R;

/**
 * Created by przem on 26.11.2017.
 */

public class DeviceInstanceFragment extends android.support.v4.app.Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.device_instance_fragment, null);
        Log.d("DeviceInstanceFragment","onCreateView()");
        return v;
    }
}
