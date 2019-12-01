/**
 * Project: NAD A4
 * File: TabAccount.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class for the tab handling the user signed in. Currently shows signed in user and
 *  gives the ability to sign out
 */

package com.example.greenhousemonitor.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.greenhousemonitor.MainActivity;
import com.example.greenhousemonitor.R;
import com.example.greenhousemonitor.User;


/**
 * Class: TabAccount
 * Extends: TabFragment
 * Implements: View.OnClickListener
 * Descr: Tab that displays the user currently logged in
 */
public class TabAccount extends TabFragment implements View.OnClickListener {
    private Button btnLogout;
    private View view;
    private TextView userName;
    private Handler handler;

    /**
     * Method: onCreateView
     * @param inflater
     * @param container
     * @param savedInstanceState
     * Descr: First method called when the Tab is instantiated
     * @return View of the tab
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_account, container, false);
        userName = view.findViewById(R.id.tvUserName);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        handler = new Handler();
        return view;
    }

    /**
     * Method: setUserVisibleHint
     * @param isVisibleToUser
     * Descr: method used to determine if that tab is currently visible to the user
     * Returns: void
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            User user = MainActivity.user;
            userName.setText(user.getUserName());
        }
    }

    /**
     * Method: onClick
     * @param v
     * Descr: Handles the logic when the user clicks the sign out button
     * Returns: void
     */
    @Override
    public void onClick(View v) {
        if (v == btnLogout) {
            Intent i = getActivity().getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}
