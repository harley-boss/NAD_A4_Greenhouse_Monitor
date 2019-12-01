/**
 * Project: NAD A4
 * File: MainActivity.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Main class of android app. Sets up tabs and views, etc.
 */

package com.example.greenhousemonitor;

import android.content.Intent;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.greenhousemonitor.Network.GreenhouseRequests;
import com.example.greenhousemonitor.Network.NetworkRequestManager;
import com.example.greenhousemonitor.Network.NetworkRequests;
import com.example.greenhousemonitor.Network.RequestFactory;
import com.example.greenhousemonitor.Network.SensorRequests;
import com.example.greenhousemonitor.Tabs.TabAccount;
import com.example.greenhousemonitor.Tabs.TabGreenhouse;
import com.example.greenhousemonitor.Tabs.TabSensors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class: MainActivity
 * Extends: AppCompatActivity
 * Descr: Class that essentially acts as a main() in Android
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private SectionsPagerAdapter adapter;
    private RequestFactory requestFactory;
    public static User user;
    public static String selectedGreenhouse;

    /**
     * Method: onCreate
     * @param savedInstanceState
     * Descr: method called upon instantiation of the class. Used to kick off the app flow
     * Returns: void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestFactory = new RequestFactory(getApplicationContext());
        setContentView(R.layout.activity_main);
        setNotificationBarColor();
        loginUser();
    }

    /**
     * Method: setNotificationBarColor
     * Descr: matches the notification bar to that of the app
     * Returns: void
     */
    private void setNotificationBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.edit_line));
    }

    /**
     * Method: onActivityResult
     * @param requestCode
     * @param resultCode
     * @param data
     * Descr: Overridden method that gets called whenever an activity finishes that was
     * initially launched from the main activity
     * Returns: void
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED) {
            finish();
            System.exit(0);
        } else if (resultCode == RESULT_OK) {
            if (requestCode == LoginActivity.REQUEST_CODE) {
                user = data.getParcelableExtra("user");
                getMainApplication().setUser(user);
                UiHelper.showToast(this, "Successfully logged in");
                getGreenhouseInfoForUser(user);
            }
        }
    }

    /**
     * Method: getSensorsForGreenhouse
     * @param index
     * Descr: Gets all the greenhouses for the currently signed in user
     * Returns: void
     */
    private void getSensorsForGreenhouse(int index) {
        if (user == null) {
            System.out.println("User was null when attempting to get sensors  for greenhouse");
            return;
        }
        if (user.getGreenhouses().isEmpty()) {
            System.out.println("User has no associated greenhouses");
            return;
        }
        if (user.getGreenhouses().size() > index) {
            int id = user.getGreenhouses().get(index).getGreenhouseId();
            System.out.println("Getting sensors for greenhouse id: " + id);
            getSensorsForGreenhouses(id);
        }
    }

    /**
     * Method: getGreenhouseInfoForUser
     * @param user
     * Descr: Sets up a request to obtain all the greenhouses from the server for a given user
     * Returns: void
     */
    private void getGreenhouseInfoForUser(User user) {
        GreenhouseRequests request = (GreenhouseRequests) requestFactory.create(RequestFactory.Type.GREENHOUSE, user);
        request.getGreenhouses(user, new GreenhouseRequests.ReplyHandler() {
            @Override
            public void onArraySuccess(JSONArray array, int responseCode) {
                if (responseCode == 200) {
                    getGreenhouses(array);
                    getSensorsForGreenhouse(0);
                }
            }
            @Override public void onSuccess(JSONObject object, int responseCode) { }
            @Override public void onFailure(int responseCode) { }
        });
    }

    /**
     * Method: getSensorsForGreenhouses
     * @param greenhouseId
     * Descr: Gets all the sensors and the associated data for a given greenhouse
     * Returns: void
     */
    private void getSensorsForGreenhouses(final int greenhouseId) {
        SensorRequests request = (SensorRequests) requestFactory.create(RequestFactory.Type.SENSOR, user);
        request.getAllSensorsForAGreenhouse(greenhouseId, new SensorRequests.ReplyHandler() {
            @Override
            public void onArraySuccess(JSONArray array, int responseCode) {
                if (responseCode == 200) {
                    getSensors(array, greenhouseId);
                }
            }
            @Override public void onSuccess(JSONObject object, int responseCode) { }
            @Override public void onFailure(int responseCode) { }
        });
    }

    /**
     * Method: getSensors
     * @param array
     * @param gId
     * Desc: Gets all the sensors from a JSON array response from the server
     * Returns: void
     */
    private void getSensors(JSONArray array, int gId) {
        Greenhouse greenhouse = null;
        for (Greenhouse g : user.getGreenhouses()) {
            if (g.getGreenhouseId() == gId) {
                greenhouse = g;
                break;
            }
        }
        if (greenhouse != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject jsonobject = array.getJSONObject(i);
                    int id = jsonobject.getInt("sensorID");
                    int greenhouseId = jsonobject.getInt("greenhouseID");
                    int type = jsonobject.getInt("sensorType");
                    String desc = jsonobject.getString("sensorTypeDescription");
                    int value = jsonobject.getInt("sensorValue");
                    String date = jsonobject.getString("dateStamp");
                    Sensor sensor = new Sensor(id, greenhouseId, type, desc, value, date);
                    greenhouse.addSensor(sensor);
                } catch (JSONException jex) {
                    System.out.println(jex.getLocalizedMessage());
                }
            }
        }
        setupViewPager();
        setupTabIcons();
    }

    /**
     * Method: getGreenhouses
     * @param array
     * Descr: Gets all the greenhouses a user has access to from a JSON array returned
     * from the server
     * Returns: void
     */
    private void getGreenhouses(JSONArray array) {
        ArrayList<Greenhouse> greenhouses = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonobject = array.getJSONObject(i);
                String name = jsonobject.getString("greenhouseName");
                int id = jsonobject.getInt("greenhouseID");
                Greenhouse greenhouse = new Greenhouse(id, name);
                greenhouses.add(greenhouse);
            } catch (JSONException jex) {
                System.out.println(jex.getLocalizedMessage());
            }
        }
        user.setGreenhouses(greenhouses);
    }

    /**
     * Method: getMainApplication
     * @return MainApplication
     * Descr: Returns the main application for use with saving data to shared preferences
     */
    private MainApplication getMainApplication() {
        Application application = getApplication();
        return (MainApplication)application;
    }

    /**
     * Method: loginUser
     * Descr: Launches the login activity to allow the user to enter their credentials
     * with which to log into the app
     * Returns: void
     */
    private void loginUser() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, LoginActivity.REQUEST_CODE);
    }

    /**
     * Method: setupViewPager
     * Descr: setups up the viewpager which houses the different tabs in the app
     * Returns: void
     */
    private void setupViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        setupViewPager(mViewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.edit_line));
    }

    /**
     * Method: setupTabIcons
     * Descr: Adds icons to the different tabs
     * Returns: void
     */
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_organization);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_sensor);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_user);
    }

    /**
     * Method: setupViewPager
     * Descr: setups up the viewpager which houses the different tabs in the app
     * @param viewPager
     * Returns: void
     */
    private void setupViewPager(ViewPager viewPager) {
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TabGreenhouse());
        adapter.addFrag(new TabSensors());
        adapter.addFrag(new TabAccount());
        viewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    /**
     * Method: setSelectedGreenhouse
     * @param greenhouse
     * Descr: Sets the name of the currently selected greenhouse
     * Returns: void
     */
    public void setSelectedGreenhouse(String greenhouse) {
        selectedGreenhouse = greenhouse;
    }

    /**
     * Class: SectionsPagerAdapter
     * Descr: Custom implementation of a pager adapter for use in the application
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch(position) {
               case 0:
                   return new TabGreenhouse();
               case 1:
                   return new TabSensors();
               case 2:
                   return new TabAccount();
               default:
                   return null;
           }
        }

        public void addFrag(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}