package com.chuckbjones.kittenweather;

import android.widget.ImageView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Callback;
import retrofit.RestAdapter;

@Module(
        addsTo = AndroidModule.class,
        overrides = true
)
public class TestModule {

    @Provides
    @Singleton
    WeatherService provideWeatherService() {
        return new WeatherService() {
            @Override
            public void GetTemperature(Callback<Weather> cb) {
                if (cb != null) {
                    cb.success(new Weather(98.6f), null);
                }
            }
        };
    }

    @Provides
    @Singleton
    KittenService provideKittenService() {
        return new KittenService() {
            @Override
            public void GetKitten(ImageView iv) {
                iv.setImageResource(R.color.mockbg);
            }
        };
    }
}
