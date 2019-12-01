/**
 * Project: NAD A4
 * File: RequestFactory.java
 * Developer: Harley Boss
 * Date: December 1st 2019
 * Class: Network Application Development
 * Description: Factory class that will return a request based on the type passed in
 */

package com.example.greenhousemonitor.Network;

import android.content.Context;

import com.example.greenhousemonitor.User;

public class RequestFactory {
    private NetworkRequestManager manager;
    private Context context;

    public RequestFactory(Context context) {
        this.context = context;
        manager = new NetworkRequestManager(context);
    }

    public NetworkRequests create(Type type, User user) {
        NetworkRequests request = null;
        switch (type) {
            case GREENHOUSE:
                request = new GreenhouseRequests();
                break;
            case SENSOR:
                request = new SensorRequests();
                break;
        }
        request.setNetworkRequestManager(manager);
        request.setUser(user);
        return request;
    }

    public enum Type {
        GREENHOUSE,
        SENSOR
    }
}
