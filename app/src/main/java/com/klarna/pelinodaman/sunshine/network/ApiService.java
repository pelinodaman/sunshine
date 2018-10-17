package com.klarna.pelinodaman.sunshine.network;

import com.klarna.pelinodaman.sunshine.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/forecast/{apiKey}/{latitude},{longitude}?units=si&exclude=daily,hourly,flags")
    Call<Weather> getWeather(@Path("apiKey") String apiKey, @Path("latitude") String latitude,
                             @Path("longitude") String longitude);

}
