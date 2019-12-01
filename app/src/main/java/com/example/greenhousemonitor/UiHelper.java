/**
 * Project: NAD A4
 * File: UiHelper.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Helper class for showing ui objects across any activity
 */

package com.example.greenhousemonitor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
public class UiHelper {

    /**
     * Method: showToast
     * @param activity
     * @param message
     * Descr: Show a toast in app
     * Returns: void
     */
    public static void showToast(Activity activity, String message) {
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
        View view = toast.getView();

        //To change the Background of Toast
        view.setBackgroundResource(R.color.edit_line);
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);
        TextView text = (TextView)layout;
        text.setText(message);
        toast.setView(layout);
        toast.show();
    }
}
