package com.sid.zuhlkecodingtest.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.sid.zuhlkecodingtest.models.TrafficsCamera;
import com.sid.zuhlkecodingtest.requests.CameraTrafficApiClient;

import java.util.List;

public class CameraRepository {
    private static final String TAG = "CameraRepository";
    private static CameraRepository mInstance = null;
    private CameraTrafficApiClient mCameraTrafficApiClient;
    private MediatorLiveData<List<TrafficsCamera>> mCameras = new MediatorLiveData<>();

    public static CameraRepository getInstance(){
        if(mInstance == null){
            mInstance = new CameraRepository();
        }
        return mInstance;
    }

    private CameraRepository(){
        mCameraTrafficApiClient = CameraTrafficApiClient.getInstance();
        initMediators();
    }

    private void initMediators(){
        LiveData<List<TrafficsCamera>> cameraListApiSource = mCameraTrafficApiClient.getCameras();
        mCameras.addSource(cameraListApiSource, new Observer<List<TrafficsCamera>>() {
            @Override
            public void onChanged(List<TrafficsCamera> cameras) {
                if(cameras != null){
                    mCameras.setValue(cameras);
                }
            }
        });
    }

    public void searchCameraApi(){
        mCameraTrafficApiClient.searchCameraTrafficApi();
    }

    public LiveData<List<TrafficsCamera>> getCameras(){
        return mCameras;
    }

}
