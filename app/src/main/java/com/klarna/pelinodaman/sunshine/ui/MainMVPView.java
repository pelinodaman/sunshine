package com.klarna.pelinodaman.sunshine.ui;

import android.app.Activity;

import com.klarna.pelinodaman.sunshine.model.Weather;

public interface MainMVPView {

    public Activity getViewActivity();

    void showLoading();

    void hideLoading();

    void onError(String message);

    boolean isNetworkConnected();

    void setFields(Weather body);
}
