package com.sid.zuhlkecodingtest.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.sid.zuhlkecodingtest.models.TrafficsCamera;
import com.sid.zuhlkecodingtest.repositories.CameraRepository;
import java.util.List;

public class CameraListViewModel extends ViewModel {
    private CameraRepository mCameraRepository;

    public CameraListViewModel(){
        mCameraRepository = CameraRepository.getInstance();
    }

    public LiveData<List<TrafficsCamera>> getCameras(){
        return mCameraRepository.getCameras();
    }

    public void searchCameraApi(){
        mCameraRepository.searchCameraApi();
    }

}
