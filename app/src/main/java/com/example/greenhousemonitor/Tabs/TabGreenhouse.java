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
import com.example.greenhousemonitor.MainActivity;
import com.example.greenhousemonitor.R;

import java.util.ArrayList;

public class TabGreenhouse extends TabFragment implements AdapterView.OnItemSelectedListener {
    private Spinner greenSpinner;
    private View view;
    private boolean viewHasBeenSetup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_greenhouses, container, false);
        greenSpinner = view.findViewById(R.id.greenSpinner);
        greenSpinner.setOnItemSelectedListener(this);
        viewHasBeenSetup = true;
        generateDummyData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && viewHasBeenSetup) {

        }
    }

    // TODO: remove when we have real data
    private void generateDummyData() {
        ArrayList<String> dummyGreen = new ArrayList<>();
        dummyGreen.add("Conestoga - Doon");
        dummyGreen.add("Conestoga - Waterloo");
        dummyGreen.add("Conestoga - Brantford");

        ArrayAdapter<String> greenAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, dummyGreen);
        greenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        greenSpinner.setAdapter(greenAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        ((MainActivity)activity).setGreenhousePicked(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}