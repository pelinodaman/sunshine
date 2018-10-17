package com.klarna.pelinodaman.sunshine.ui;

import android.app.Activity;

import com.klarna.pelinodaman.sunshine.model.Weather;

public interface MainMVPView {

    Activity getViewActivity();

    void onError(String message);

    void setFields(Weather weather);
}
