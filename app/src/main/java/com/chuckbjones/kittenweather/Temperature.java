package com.chuckbjones.kittenweather;

public class Temperature {
    private static final float KELVIN_TO_CELSIUS_OFFSET = 273.15f;
    private static final float KELVIN_TO_IMPERIAL_OFFSET = 459.67f;

    private static final float KELVIN_TO_IMPERIAL_RATIO = 9.0f / 5.0f;
    private static final float IMPERIAL_TO_KELVIN_RATIO = 5.0f / 9.0f;

    private float kelvinDegrees;

    private Temperature(float kelvinDegrees) {
        this.kelvinDegrees = kelvinDegrees;
    }

    public static Temperature fromKelvin(float kelvinDegrees) {
        return new Temperature(kelvinDegrees);
    }

    public static Temperature fromImperial(float imperialDegrees) {
        float t = imperialDegrees + KELVIN_TO_IMPERIAL_OFFSET;
        t = t * IMPERIAL_TO_KELVIN_RATIO;
        return new Temperature(t);
    }

    public static Temperature fromCelsius(float celsiusDegrees) {
        return new Temperature(celsiusDegrees + KELVIN_TO_CELSIUS_OFFSET);
    }

    public float imperialDegrees() {
        return ( kelvinDegrees * KELVIN_TO_IMPERIAL_RATIO ) - KELVIN_TO_IMPERIAL_OFFSET;
    }

    public float celsiusDegrees() {
        return kelvinDegrees - KELVIN_TO_CELSIUS_OFFSET;
    }

    public float kelvinDegrees() {
        return kelvinDegrees;
    }

}
