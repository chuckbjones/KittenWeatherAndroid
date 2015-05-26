package com.chuckbjones.kittenweather;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Inject
    WeatherService weatherService;

    @Inject
    KittenService kittenService;

    @InjectView(R.id.image) ImageView kittenView;
    @InjectView(R.id.temp) TextView temperatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Perform injection so that when this call returns all dependencies will be available for use.
        ((KittenWeatherApp) getApplication()).inject(this);
        ButterKnife.inject(this);

        ViewTreeObserver vto = kittenView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                kittenView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                refreshKitten();
            }
        });

        refreshTemp();
    }

    @OnClick(R.id.about)
    void showAboutView() {
        final RelativeLayout layout = (RelativeLayout)findViewById(R.id.container);
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.about);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        int m = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
        params.setMargins(m, m, m, m);
        iv.setLayoutParams(params);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(iv);
            }
        });

        layout.addView(iv);
    }

    @OnClick(R.id.refresh)
    void refresh() {
        refreshKitten();
        refreshTemp();
    }

    private void refreshTemp() {
        weatherService.GetTemperature(new Callback<Weather>() {
            @Override
            public void success(Weather weather, Response response) {
                temperatureView.setText(String.format("%.1f°F", weather.getTemperature()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Failed to get Weather", Toast.LENGTH_SHORT).show();
                Log.e("RefreshTemp", "Error: " + error.toString());
            }
        });
    }

    private void refreshKitten() {
        kittenService.GetKitten(kittenView);
    }
}
