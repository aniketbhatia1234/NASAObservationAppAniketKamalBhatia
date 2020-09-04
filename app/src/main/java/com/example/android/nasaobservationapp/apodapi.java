package com.example.android.nasaobservationapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apodapi {

    @GET("apod")
    Call<APODObject> getadpodobj(@Query("api_key") String api_key, @Query("date") String date);


}
