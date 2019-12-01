/**
 * Project: NAD A4
 * File: Sensor.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Shallow class representing the attributes of a sensor
 */

package com.example.greenhousemonitor;

public class Sensor {
    private int sensorId;
    private int greenhouseId;
    private int sensorType;
    private String sensorTypeDescription;
    private int sensorValue;
    private String timeStamp;

    public Sensor(int sensorId, int greenhouseId, int sensorType, String sensorTypeDescription, int sensorValue, String timeStamp) {
        this.sensorId = sensorId;
        this.greenhouseId = greenhouseId;
        this.sensorType = sensorType;
        this.sensorTypeDescription = sensorTypeDescription;
        this.sensorValue = sensorValue;
        this.timeStamp = timeStamp;
    }

    public int getSensorId() {
        return sensorId;
    }

    public int getGreenhouseId() {
        return greenhouseId;
    }

    public int getSensorType() {
        return sensorType;
    }

    public String getSensorTypeDescription() {
        return sensorTypeDescription;
    }

    public int getSensorValue() {
        return sensorValue;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}

