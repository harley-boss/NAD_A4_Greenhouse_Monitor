/**
 * Project: NAD A4
 * File: NetworkRequestManager.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Class that manages network connections
 */

package com.example.greenhousemonitor.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class: Network request manager
 * Descr: Handles all the actual network requests
 */
public class NetworkRequestManager {
    static NetworkRequestManager instance = null;
    Context ctx = null;
    RequestQueue requestQueue = null;

    /**
     * Method: NetworkRequestManager
     * @param ctx
     * Descr: Constructor
     * Returns: n/a
     */
    public NetworkRequestManager(Context ctx) {
        this(ctx, null);
    }

    /**
     * Method: NetworkRequestManager
     * @param ctx
     * @param rq
     * Descr: Initiates a network request manager and a network request
     * Returns: n/a
     */
    public NetworkRequestManager(Context ctx, RequestQueue rq) {
        instance = this;
        this.ctx = ctx;
        if (rq == null) {
            requestQueue = Volley.newRequestQueue(this.ctx, new HurlStack() {
                @Override
                protected HttpURLConnection createConnection(URL url) throws IOException {
                    HttpURLConnection connection = super.createConnection(url);
                    connection.setInstanceFollowRedirects(false);
                    return connection;
                }
            });

        } else {
            requestQueue = rq;
        }
    }

    /**
     * Method: performRequest
     * @param r
     * @param id
     * @param <T>
     * Descr: performs a network request of any derived class
     * Returns: void
     */
    public <T> void performRequest(Request<T> r, String id) {
        r.setTag(id);
        requestQueue.add(r);
    }

    /**
     * Method: cancelRequest
     * @param id
     * Descr: cancels a request based on id
     * Returns: void
     */
    public void cancelRequest(String id) {
        requestQueue.cancelAll(id);
    }
}

