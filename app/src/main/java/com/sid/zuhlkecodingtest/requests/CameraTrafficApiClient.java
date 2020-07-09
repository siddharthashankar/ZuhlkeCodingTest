package com.sid.zuhlkecodingtest.requests;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sid.zuhlkecodingtest.models.TrafficCamera;
import com.sid.zuhlkecodingtest.models.TrafficsCamera;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Response;

public class CameraTrafficApiClient {
    private static final String TAG = "CameraTrafficApiClient";
    private static CameraTrafficApiClient mInstance = null;
    private MutableLiveData<List<TrafficsCamera>> mCameras;
    private RetrieveCameraTrafficRunnable mRetrieveCameraTrafficRunnable;

    public static CameraTrafficApiClient getInstance(){
        if (mInstance == null){
            mInstance = new CameraTrafficApiClient();
        }
        return mInstance;
    }

    private CameraTrafficApiClient(){
        mCameras = new MutableLiveData<>();
    }

    public LiveData<List<TrafficsCamera>> getCameras(){
        return mCameras;
    }


    public void searchCameraTrafficApi(){
        if(mRetrieveCameraTrafficRunnable != null){
            mRetrieveCameraTrafficRunnable = null;
        }
        mRetrieveCameraTrafficRunnable = new RetrieveCameraTrafficRunnable();
        final Future handller = AppExecutor.getInstance().networkIO().submit(mRetrieveCameraTrafficRunnable);

        AppExecutor.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handller.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    // Retrieve the traffic camera value from webservice using retrofit
    private class RetrieveCameraTrafficRunnable implements Runnable{
        private boolean cancelRequest;

        public RetrieveCameraTrafficRunnable(){
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getCamera().execute();
                if(cancelRequest){
                    return;
                }
                if (response.code()== 200){
                    Log.d(TAG,"Response: "+response.code());
                    List<TrafficsCamera> list = new ArrayList<>(((TrafficCamera)response.body()).getItems().get(0).getCameras());
                    Log.d(TAG,"Response: "+response.code()+" , "+list.size());
                    mCameras.postValue(list);
                }else {
                    String error = response.errorBody().string();
                    Log.d(TAG,"Error: "+error);
                    mCameras.postValue(null);
                }
            }catch (IOException e){
                Log.d(TAG,"Error: "+e.getMessage());
                mCameras.postValue(null);
                e.printStackTrace();
            }
        }

        private Call<TrafficCamera> getCamera(){
            CameraAPI cameraAPI = ServiceGenerator.getCameraAPI();
            Call<TrafficCamera> cameraSearchResponse = cameraAPI.getTrafficCamera();
            return cameraSearchResponse;
        }

        private void cancelRequest(){
            Log.d(TAG,"Cancel Request : Canceling the search request");
            cancelRequest = true;
        }

    }
}
