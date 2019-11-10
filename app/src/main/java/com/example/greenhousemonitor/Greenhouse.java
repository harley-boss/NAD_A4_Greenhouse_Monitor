/**
 * Project: NAD A4
 * File: Greenhouse.java
 * Developer: Harley Boss
 * Date: November 10th 2019
 * Class: Network Application Development
 * Description: Shallow class representing the attributes of a greenhouse
 */

package com.example.greenhousemonitor;

public class Greenhouse {
    private float lightLevel;
    private float temperature;
    private float humidity;

    public Greenhouse(float lightLevel, float temperature, float humidity) {
        this.lightLevel = lightLevel;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public void setLightLevel(float lightLevel) {
        this.lightLevel = lightLevel;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getLightLevel() {
        return lightLevel;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }
}
