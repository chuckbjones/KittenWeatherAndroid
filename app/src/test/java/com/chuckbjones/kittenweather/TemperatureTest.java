package com.chuckbjones.kittenweather;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.InRange;

import org.junit.Test;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

@RunWith(Theories.class)
public class TemperatureTest {

    private static final float FREEZING_KELVIN = 273.15f;
    private static final float BOILING_KELVIN = 373.15f;

    private static final float FREEZING_IMPERIAL = 32.0f;
    private static final float BOILING_IMPERIAL = 212.0f;

    private static final float FREEZING_CELSIUS = 0.0f;
    private static final float BOILING_CELSIUS = 100.0f;

    private static final float MATCHING_TOLERANCE = 0.001f;

    @Test
    public void freezingImperial() {
        Temperature temperature = Temperature.fromImperial(FREEZING_IMPERIAL);
        assertThat((double)temperature.imperialDegrees(), is(closeTo(FREEZING_IMPERIAL, MATCHING_TOLERANCE)));
        assertThat((double)temperature.kelvinDegrees(), is(closeTo(FREEZING_KELVIN, MATCHING_TOLERANCE)));
    }

    @Test
    public void freezingCelsius() {
        Temperature temperature = Temperature.fromCelsius(FREEZING_CELSIUS);
        assertThat(temperature.celsiusDegrees(), is(equalTo(FREEZING_CELSIUS)));
        assertThat(temperature.kelvinDegrees(), is(equalTo(FREEZING_KELVIN)));
    }

    @Test
    public void boilingImperial() {
        Temperature temperature = Temperature.fromImperial(BOILING_IMPERIAL);
        assertThat((double)temperature.imperialDegrees(), is(closeTo(BOILING_IMPERIAL, MATCHING_TOLERANCE)));
        assertThat((double)temperature.kelvinDegrees(), is(closeTo(BOILING_KELVIN, MATCHING_TOLERANCE)));
    }

    @Test
    public void boilingCelsius() {
        Temperature temperature = Temperature.fromCelsius(BOILING_CELSIUS);
        assertThat(temperature.celsiusDegrees(), is(equalTo(BOILING_CELSIUS)));
        assertThat(temperature.kelvinDegrees(), is(equalTo(BOILING_KELVIN)));
    }


    //region QuickCheck Tests

    @Theory
    public void identityKelvin(@ForAll(sampleSize = 10000) @InRange(min = "-10000", max = "10000") float kelvinTemperatureNumber) {
        Temperature temperature = Temperature.fromKelvin(kelvinTemperatureNumber);
        float difference = Math.abs(kelvinTemperatureNumber - temperature.kelvinDegrees());
        assertThat(difference, is(lessThan(MATCHING_TOLERANCE)));
    }

    @Theory
    public void identityCelsius(@ForAll(sampleSize = 10000) @InRange(min = "-10000", max = "10000") float celsiusTemperatureNumber) {
        Temperature temperature = Temperature.fromCelsius(celsiusTemperatureNumber);
        float difference = Math.abs(celsiusTemperatureNumber - temperature.celsiusDegrees());
        assertThat(difference, is(lessThan(MATCHING_TOLERANCE)));
    }

    @Theory
    public void identityImperial(@ForAll(sampleSize = 10000) @InRange(min = "-10000", max = "10000") float imperialTemperatureNumber) {
        Temperature temperature = Temperature.fromImperial(imperialTemperatureNumber);
        float difference = Math.abs(imperialTemperatureNumber - temperature.imperialDegrees());
        assertThat(difference, is(lessThan(MATCHING_TOLERANCE)));
    }

    //endregion

}