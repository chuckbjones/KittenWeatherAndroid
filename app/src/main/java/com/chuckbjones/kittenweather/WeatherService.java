package com.chuckbjones.kittenweather;

import retrofit.Callback;
import retrofit.http.GET;

public interface WeatherService {
    @GET("/weather?q=austin,tx&units=imperial")
    void GetTemperature(Callback<Weather> cb);
}
