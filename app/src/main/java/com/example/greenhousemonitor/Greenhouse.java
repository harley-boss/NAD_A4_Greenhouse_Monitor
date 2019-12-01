/**
 * Project: NAD A4
 * File: Greenhouse.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Shallow class representing the attributes of a greenhouse
 */

package com.example.greenhousemonitor;

import java.util.ArrayList;

public class Greenhouse {
    private int greenhouseId;
    private String greenhouseName;
    private ArrayList<Sensor> sensors;

    public Greenhouse(int greenhouseId, String greenhouseName) {
        this.greenhouseId = greenhouseId;
        this.greenhouseName = greenhouseName;
        sensors = new ArrayList<>();
    }

    public int getGreenhouseId() {
        return greenhouseId;
    }

    public String getGreenhouseName() {
        return greenhouseName;
    }

    public void addSensor(Sensor sensor) {
        if (!sensors.contains(sensor)) {
            sensors.add(sensor);
        }
    }

    public ArrayList<Sensor> getAllSensors() {
        return sensors;
    }
}
