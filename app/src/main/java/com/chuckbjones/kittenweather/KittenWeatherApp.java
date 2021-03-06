package com.chuckbjones.kittenweather;

import android.app.Application;

import dagger.ObjectGraph;

public class KittenWeatherApp extends Application {
    private ObjectGraph graph;

    @Override public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(Modules.list());
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
