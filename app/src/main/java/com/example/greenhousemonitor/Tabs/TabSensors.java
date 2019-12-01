/**
 * Project: NAD A4
 * File: TabSensors.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that represents the sensors in a specific greenhouse
 */

package com.example.greenhousemonitor.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.greenhousemonitor.Greenhouse;
import com.example.greenhousemonitor.MainActivity;
import com.example.greenhousemonitor.R;
import com.example.greenhousemonitor.Sensor;
import com.example.greenhousemonitor.User;

import java.util.ArrayList;

/**
 * Class: TabSensors
 * Extends: TabFragment
 * Descr: Class that displays all the sensors for a given greenhouse
 */
public class TabSensors extends TabFragment {
    private View view;
    private boolean viewHasBeenSetup;
    private TextView tempValue;
    private TextView humidValue;
    private TextView luxValue;

    /**
     * Method: onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * Descr: First method called on instantiation of the class
     * @return: void
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_sensors, container, false);
        tempValue = view.findViewById(R.id.tvTempValue);
        humidValue = view.findViewById(R.id.tvHumidValue);
        luxValue = view.findViewById(R.id.tvLightValue);
        viewHasBeenSetup = true;
        return view;
    }

    /**
     * Method: setUserVisibleHint
     * @param isVisibleToUser
     * Descr: Used to determine if the tab is currently visible to the user
     * Returns: void
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && viewHasBeenSetup) {
            User user = MainActivity.user;
            if (MainActivity.selectedGreenhouse != null) {
                Greenhouse gHouse = null;
                for (Greenhouse g : user.getGreenhouses()) {
                    if (g.getGreenhouseName().equalsIgnoreCase(MainActivity.selectedGreenhouse)) {
                        gHouse = g;
                        break;
                    }
                }
                if (gHouse != null) {
                    ArrayList<Sensor> sensors = gHouse.getAllSensors();
                    tempValue.setText(String.valueOf(sensors.get(0).getSensorValue()) + " \u00b0C");
                    humidValue.setText(String.valueOf(sensors.get(1).getSensorValue()) +" %");
                    luxValue.setText(String.valueOf(sensors.get(2).getSensorValue()) + " lux");
                }
            }
        }
    }
}