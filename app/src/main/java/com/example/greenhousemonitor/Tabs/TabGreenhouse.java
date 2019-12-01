/**
 * Project: NAD A4
 * File: TabGreenhouse.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that represents the greenhouse tab in app. Allows the user to choose from
 *  any greenhouse tied to their account
 */

package com.example.greenhousemonitor.Tabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.greenhousemonitor.Greenhouse;
import com.example.greenhousemonitor.MainActivity;
import com.example.greenhousemonitor.R;
import com.example.greenhousemonitor.User;

import java.util.ArrayList;

/**
 * Class: TabGreenhouse
 * Extends: TabFragment
 * Implements: AdapterView.OnItemSelectedListener
 * Descr: Tab that handles displaying the available greenhouses to a user
 */
public class TabGreenhouse extends TabFragment implements AdapterView.OnItemSelectedListener {
    private Spinner greenSpinner;
    private View view;
    private boolean viewHasBeenSetup;
    private static String greenhouseName;

    /**
     * Method: onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * Descr: First method called upon the instantiation of the tab
     * @return: View of the tab
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_greenhouses, container, false);
        greenSpinner = view.findViewById(R.id.greenSpinner);
        greenSpinner.setOnItemSelectedListener(this);
        viewHasBeenSetup = true;
        getUserGreenhouses();
        return view;
    }

    /**
     * Method: getUserGreenhouses
     * Descr: Gets all the greenhouses a user has access to and displays them in a list
     * Return: void
     */
    private void getUserGreenhouses() {
        ArrayList<String> greenhouses = new ArrayList<>();
        User user = MainActivity.user;
        for (Greenhouse g : user.getGreenhouses()) {
            greenhouses.add(g.getGreenhouseName());
        }
        ArrayAdapter<String> greenAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, greenhouses);
        greenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        greenSpinner.setAdapter(greenAdapter);
    }

    /**
     * Method: onItemSelected
     * @param parent
     * @param view
     * @param position
     * @param id
     * Descr: Overridden method that's called when the user makes a selection from the spinner
     * Returns: void
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        greenhouseName = (String)parent.getItemAtPosition(position);
        ((MainActivity)activity).setSelectedGreenhouse(greenhouseName);
    }

    @Override public void onNothingSelected(AdapterView<?> parent) { }
}