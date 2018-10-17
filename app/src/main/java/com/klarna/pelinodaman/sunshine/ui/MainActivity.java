package com.klarna.pelinodaman.sunshine.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.klarna.pelinodaman.sunshine.R;
import com.klarna.pelinodaman.sunshine.model.Weather;
import com.klarna.pelinodaman.sunshine.utils.Const;

public class MainActivity extends AppCompatActivity implements MainMVPView {

    private TextView textViewSummary;
    private TextView textViewTimezone;
    private TextView textViewTemperature;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        presenter = new MainPresenter(this, this);
        presenter.startGettingLocation();

    }

    private void initializeViews() {
        textViewSummary = findViewById(R.id.textViewSummary);
        textViewTimezone = findViewById(R.id.textViewTimezone);
        textViewTemperature = findViewById(R.id.textViewTemperature);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        presenter.onPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void setFields(Weather weather) {
        textViewSummary.setText(weather.getCurrently().getSummary());
        textViewTemperature.setText((weather.getCurrently().getTemperature()).intValue() + Const.CELCIUS);
        textViewTimezone.setText(weather.getTimezone());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
