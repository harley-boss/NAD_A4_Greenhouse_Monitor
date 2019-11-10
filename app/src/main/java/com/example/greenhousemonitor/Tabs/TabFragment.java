/**
 * Project: NAD A4
 * File: TabFragment.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that implements the fragment functionality along with some customization
 *  specific to this app. All tabs will implement this class.
 */

package com.example.greenhousemonitor.Tabs;

import android.app.Application;

import androidx.fragment.app.Fragment;

import com.example.greenhousemonitor.MainApplication;

public class TabFragment extends Fragment {

    public MainApplication getMainApplication() {
        Application application = getActivity().getApplication();
        return (MainApplication)application;
    }
}
