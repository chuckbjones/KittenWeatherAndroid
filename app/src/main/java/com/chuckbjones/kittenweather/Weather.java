package com.chuckbjones.kittenweather;

public class Weather {

    public Main main;

    public Weather(float temperature) {
        setTemperature(temperature);
    }

    public void setTemperature(float temperature) {
        main = new Main(temperature);
    }

    public class Main {
        float temp;

        public Main(float temperature) {
            temp = temperature;
        }
    }

    public float getTemperature() {
        return main.temp;
    }
}
