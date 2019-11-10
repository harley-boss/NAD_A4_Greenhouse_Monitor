/**
 * Project: NAD A4
 * File: TabSensors.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that represents the sensors in a specific greenhouse
 */

package com.example.greenhousemonitor.Tabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.greenhousemonitor.MainActivity;
import com.example.greenhousemonitor.R;
import com.example.greenhousemonitor.User;

public class TabSensors extends TabFragment {
    private View view;
    private boolean viewHasBeenSetup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_sensors, container, false);
        viewHasBeenSetup = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && viewHasBeenSetup) {
            User user = null;
            try {
                //user = ((MainActivity)getActivity()).getUser();
            } catch (Exception e) {}
            Activity activity = getActivity();
            if (activity == null) {
                return;
            }
            boolean hasGreenHouse = ((MainActivity)getActivity()).hasGreenhouseBeenPicked();
        }
    }
}