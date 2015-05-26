package com.chuckbjones.kittenweather;

public class Weather {

    public Main main;

    public class Main {
        float temp;
    }

    public float getTemperature() {
        return main.temp;
    }
}
