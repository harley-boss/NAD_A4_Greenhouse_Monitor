/**
 * Project: NAD A4
 * File: NetworkRequests.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Base class that all network related requests will implement. Allows requests
 *  to be made to remote server
 */

package com.example.greenhousemonitor.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.greenhousemonitor.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Class: NetworkRequests
 * Descr: Base class for post/get requests to the server
 */
public class NetworkRequests {
    protected NetworkRequestManager networkRequestManager;
    protected NetworkResponse networkResponse = null;
    protected int responseStatusCode;
    protected String urlPrefix = "https://nadtest.azurewebsites.net/api/";
    protected User user;

    /**
     * Method: setNetworkRequestManager
     * @param networkRequestManager
     * Descr: set the class variable
     * Returns: void
     */
    public void setNetworkRequestManager(NetworkRequestManager networkRequestManager) {
        this.networkRequestManager = networkRequestManager;
    }

    /**
     * Method: setUser
     * @param user
     * Descr: set the class variable
     * Returns: void
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Method: getArray
     * @param resource
     * Descr: method called to obtain a json array of data
     * Returns: void
     */
    protected void getArray(String resource) {
        performArrayRequest(Request.Method.GET, resource);
    }

    /**
     * Method: performArrayRequest
     * @param method
     * @param url
     * Descr: performs a request which expects a json array returned from the server
     * Returns: void
     */
    protected void performArrayRequest(final int method, String url) {
        networkResponse = null;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, new JsonArrayListener(), new ErrorListener()) {
            @Override
            public Map<String, String> getHeaders() {
                return requestHeaders();
            }

            @Override
            public int getMethod() {
                return method;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                responseStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };
        jsonObjectRequest.setRetryPolicy(getRetryPolicy());
        networkRequestManager.performRequest(jsonObjectRequest, url);
    }

    /**
     * Method: onFailure
     * @param error
     * Descr: Method called when there was a network error
     * Returns: void
     */
    protected void onFailure(VolleyError error) {
        this.networkResponse = error.networkResponse;
        if (networkResponse != null) {
            this.responseStatusCode = networkResponse.statusCode;
        }
    }

    /**
     * Class: JsonArrayListener
     * Implements: Response.Listener<JSONArray>
     * Descr: Class is used when instantiating a class in order for the overridden method
     * to be triggered
     */
    private class JsonArrayListener implements Response.Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray response) {
            onArraySuccess(response, responseStatusCode);
        }
    }

    /**
     * Method: requestHeaders
     * Descr: Gets the headers for the request
     * @return: Map of the key value pair needed for headers
     */
    protected Map<String, String> requestHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("AUTHORIZATION", "Bearer " + user.getToken());
        return headers;
    }

    /**
     * Method: serverCode
     * Descr: returns the last server code obtained form the server
     * @return int
     */
    protected int serverCode() {
        return responseStatusCode;
    }

    protected RetryPolicy getRetryPolicy() {
        int timeout = 30000; // 30 seconds
        int retryCount = 0; // Don't retry
        float backoffMultiplier = 1F;
        return new DefaultRetryPolicy(timeout, retryCount, backoffMultiplier);
    }

    // ----- Reply Listeners -----
    private class ErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            onFailure(error);
        }
    }

    // Overridable method
    protected void onSuccess(JSONObject object, int responseStatusCode) { }

    protected void onArraySuccess(JSONArray response, int responseStatusCode) { }
}
