package com.sid.zuhlkecodingtest.requests;

import com.sid.zuhlkecodingtest.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static CameraAPI cameraAPI = retrofit.create(CameraAPI.class);

    public static CameraAPI getCameraAPI(){
        return cameraAPI;
    }
}
