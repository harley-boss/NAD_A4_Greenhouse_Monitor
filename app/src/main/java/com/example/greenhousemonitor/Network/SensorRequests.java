/**
 * Project: NAD A4
 * File: SensorRequests.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that handles requests related to sensors
 */

package com.example.greenhousemonitor.Network;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class: SensorRequests
 * Descr: Extends network requests for specialied requests pertaining to
 * sensor details for a given greenhouse
 */
public class SensorRequests extends NetworkRequests {

    private ReplyHandler replyHandler;

    /**
     * Method: getAllSensorsForAGreenhouse
     * @param greenhouseId
     * @param replyHandler
     * Descr: Gets a json array of all sensors for a given greenhouse id
     * Returns: void
     */
    public void getAllSensorsForAGreenhouse(int greenhouseId, ReplyHandler replyHandler) {
        this.replyHandler = replyHandler;
        String url = urlPrefix + "Sensor?greenhouseID=" + greenhouseId;
        getArray(url);
    }

    /**
     * Method: onSuccess
     * @param object
     * @param responseStatusCode
     * Descr: Called when the request was successful
     * Returns: void
     */
    @Override
    protected void onSuccess(JSONObject object, int responseStatusCode) {
        super.onSuccess(object, responseStatusCode);
        this.replyHandler.onSuccess(object, responseStatusCode);
    }

    /**
     * Method: onFailure
     * @param error
     * Descr: Called when the request failed
     * Returns: void
     */
    @Override
    protected void onFailure(VolleyError error) {
        super.onFailure(error);
        this.replyHandler.onFailure(responseStatusCode);
    }

    /**
     * Method: onArraySuccess
     * @param response
     * @param responseStatusCode
     * Descr: Called when an array request was successful
     * Returns: void
     */
    @Override
    protected void onArraySuccess(JSONArray response, int responseStatusCode) {
        super.onArraySuccess(response, responseStatusCode);
        this.replyHandler.onArraySuccess(response, responseStatusCode);
    }

    /**
     * Interface which class calling the request class must implement
     * in order to receive callbacks
     */
    public interface ReplyHandler {
        void onSuccess(JSONObject object, int responseCode);

        void onArraySuccess(JSONArray array, int responseCode);

        void onFailure(int responseCode);
    }
}
