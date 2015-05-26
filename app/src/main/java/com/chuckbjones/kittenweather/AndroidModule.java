package com.chuckbjones.kittenweather;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        injects = MainActivity.class,
        complete = false,
        library = true
)
public class AndroidModule {

    @Provides
    @Singleton
    WeatherService provideWeatherService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org/data/2.5")
                .build();
        return restAdapter.create(WeatherService.class);
    }

    @Provides
    @Singleton
    KittenService provideKittenService() {
        return new PicassoKittenService();
    }
}
