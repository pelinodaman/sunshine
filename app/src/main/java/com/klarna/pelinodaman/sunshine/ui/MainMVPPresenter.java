package com.klarna.pelinodaman.sunshine.ui;

public interface MainMVPPresenter {

    void onPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void startGettingLocation();

    void onDestroy();
}
