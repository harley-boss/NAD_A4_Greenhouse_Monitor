/**
 * Project: NAD A4
 * File: MainApplication.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Derives from the Android Application class. Provides custom actions in app and
 *  allows to essentially set global variables for other resources to access.
 */

package com.example.greenhousemonitor;

import android.app.Application;

public class MainApplication extends Application {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public MainApplication getMainApplication() {
        return this;
    }
}
