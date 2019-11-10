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

public class NetworkRequestManager {
    static NetworkRequestManager instance = null;
    Context ctx = null;
    RequestQueue requestQueue = null;

    public NetworkRequestManager(Context ctx) {
        this(ctx, null);
    }

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

    public <T> void performRequest(Request<T> r, String id) {
        r.setTag(id);
        requestQueue.add(r);
    }

    public void cancelRequest(String id) {
        requestQueue.cancelAll(id);
    }
}

