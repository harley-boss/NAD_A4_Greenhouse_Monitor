/**
 * Project: NAD A4
 * File: GreenhouseRequests.java
 * Developer: Harley Boss
 * Date: December 1st 2019
 * Class: Network Application Development
 * Description: Class that handles requests to the server relating to a greenhouse
 */

package com.example.greenhousemonitor.Network;

import com.android.volley.VolleyError;
import com.example.greenhousemonitor.User;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class: GreenhouseRequests
 * Descr: Handles all network requests related to a greenhouse
 */
public class GreenhouseRequests extends NetworkRequests {
    private ReplyHandler replyHandler;

    /**
     * Method: getGreenhouses
     * @param user
     * @param replyHandler
     * Descr: Gets all the greenhouses for a certain user based on id
     */
    public void getGreenhouses(User user, ReplyHandler replyHandler) {
        this.replyHandler = replyHandler;
        String url = urlPrefix + "Greenhouse?userID=" + user.getUserId();
        getArray(url);
    }

    /**
     * Method: onSuccess
     * @param object
     * @param responseStatusCode
     * Descr: Overridden method called when a network request was successful
     */
    @Override
    protected void onSuccess(JSONObject object, int responseStatusCode) {
        super.onSuccess(object, responseStatusCode);
        this.replyHandler.onSuccess(object, responseStatusCode);
    }

    /**
     * Method: onSuccess
     * @param error
     * Descr: Called when a network request was unsuccessful
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
     * Descr: Called when a request for a json array of data was successful
     */
    @Override
    protected void onArraySuccess(JSONArray response, int responseStatusCode) {
        super.onArraySuccess(response, responseStatusCode);
        this.replyHandler.onArraySuccess(response, responseStatusCode);
    }

    public interface ReplyHandler {
        void onSuccess(JSONObject object, int responseCode);
        void onArraySuccess(JSONArray array, int responseCode);
        void onFailure(int responseCode);
    }
}
