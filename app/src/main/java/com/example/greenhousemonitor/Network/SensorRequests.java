/**
 * Project: NAD A4
 * File: SensorRequests.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that handles requests related to sensors
 */

package com.example.greenhousemonitor.Network;

import org.json.JSONObject;

public class SensorRequests extends NetworkRequests {

    public interface ReplyHandler {
        void onSuccess(JSONObject object, int responseCode);
        void onFailure(int responseCode);
    }
}
