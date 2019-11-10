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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetworkRequests {
    protected NetworkRequestManager networkRequestManager;
    protected NetworkResponse networkResponse = null;
    protected int responseStatusCode;

    public void setNetworkRequestManager(NetworkRequestManager networkRequestManager) {
        this.networkRequestManager = networkRequestManager;
    }

    protected void post(String resource, String url, JSONObject params) {
        performRequest(Request.Method.POST, url, resource, params);
    }

    protected void get(String resource, String url, JSONObject params) {
        performRequest(Request.Method.GET, url, resource, params);
    }

    protected void performRequest(int method, String url, String resource, JSONObject params) {
        performResolvedRequest(method, url, resource, params);
    }

    protected void performResolvedRequest(int method, String url, String resource, JSONObject params) {
        networkResponse = null;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, params, new JsonObjectListener(), new ErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return requestHeaders();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                responseStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        jsonObjectRequest.setRetryPolicy(getRetryPolicy());
        networkRequestManager.performRequest(jsonObjectRequest, resource);
    }

    protected void onFailure(VolleyError error) {
        this.networkResponse = error.networkResponse;
        if (networkResponse != null) {
            this.responseStatusCode = networkResponse.statusCode;
        }
    }

    protected Map<String, String> requestHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        return headers;
    }

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

    private class JsonObjectListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject response) {
            onSuccess(response, responseStatusCode);
        }
    }

    // Overridable method
    protected void onSuccess(JSONObject object, int responseStatusCode) { }
}
