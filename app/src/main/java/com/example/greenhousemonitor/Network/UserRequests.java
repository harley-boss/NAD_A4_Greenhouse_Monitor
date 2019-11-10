/**
 * Project: NAD A4
 * File: UserRequests.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that handles requests related to users
 */

package com.example.greenhousemonitor.Network;

import com.android.volley.VolleyError;
import com.example.greenhousemonitor.User;

import org.json.JSONObject;


public class UserRequests extends NetworkRequests {
    private Result callback;

    public void authenticateUser(User user, Result callback) {
        this.callback = callback;
        // TODO
        // post
    }

    @Override
    protected void onFailure(VolleyError error) {
        super.onFailure(error);
        callback.onFailure(serverCode());
    }

    @Override
    protected void onSuccess(JSONObject object, int responseStatusCode) {
        super.onSuccess(object, responseStatusCode);
        callback.onSuccess(object, responseStatusCode);
    }

    public interface Result {
        void onSuccess(JSONObject object, int responseCode);
        void onFailure(int responseCode);
    }
}
