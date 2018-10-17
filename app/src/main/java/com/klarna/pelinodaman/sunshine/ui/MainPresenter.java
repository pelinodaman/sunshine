package com.klarna.pelinodaman.sunshine.ui;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.klarna.pelinodaman.sunshine.model.Weather;
import com.klarna.pelinodaman.sunshine.network.ApiClient;
import com.klarna.pelinodaman.sunshine.network.ApiService;
import com.klarna.pelinodaman.sunshine.utils.Const;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainMVPPresenter {

    private MainMVPView mView;
    private Context mContext;

    private FusedLocationProviderClient mFusedLocationClient;

    private Handler handler;

    public MainPresenter(Context context, MainMVPView view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void startGettingLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        handler = new Handler();
        handler.post(myRunnable);
    }

    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            getLocation();
            handler.postDelayed(this, Const.CALL_PERIOD);
        }
    };

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions();
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(mView.getViewActivity(), location -> {
                        if (location != null) {
                            getWeatherData(String.valueOf(location.getLatitude()),
                                    String.valueOf(location.getLongitude()));
                        }
                    });
        }
    }

    private void getWeatherData(String latitude, String longitude) {

        ApiService service = ApiClient.getInstance().create(ApiService.class);
        Call<Weather> weather = service.getWeather(Const.API_KEY, latitude, longitude);

        weather.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Log.i("Response", response.toString());
                if(response.body() != null) {
                    mView.setFields(response.body());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.i("Failure", t.toString());
                mView.onError(t.getMessage());
            }
        });

    }

    private void requestPermissions() {
        String[] permissions = new String[] {Manifest.permission.ACCESS_FINE_LOCATION};
        mView.getViewActivity().requestPermissions(permissions, Const.PERMISSION_ACCESS_FINE_LOCATION);
    }

    @Override
    public void onPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            if(requestCode == Const.PERMISSION_ACCESS_FINE_LOCATION) {
                getLocation();
            }
        }
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(myRunnable);
    }


}
