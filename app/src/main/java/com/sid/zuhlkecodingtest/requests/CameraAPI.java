package com.sid.zuhlkecodingtest.requests;

import com.sid.zuhlkecodingtest.models.TrafficCamera;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CameraAPI {
    //Search request
    @GET("traffic-images")
    Call<TrafficCamera> getTrafficCamera();
}
