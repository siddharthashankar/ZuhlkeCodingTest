package com.sid.zuhlkecodingtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sid.zuhlkecodingtest.models.TrafficsCamera;
import com.sid.zuhlkecodingtest.viewmodels.CameraListViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrafficCameraActivity extends BaseActivity implements OnMapReadyCallback {
    private static final String TAG = "TrafficCameraActivity";
    private CameraListViewModel mCameraListViewModel;
    private List<TrafficsCamera> mTrafficCamera = new ArrayList<>();
    private AppCompatImageView mLatestPhoto;
    private GoogleMap mMap;
    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mLatestPhoto = (AppCompatImageView) findViewById(R.id.iv_latestPhoto);
        //ViewModel
        mCameraListViewModel = ViewModelProviders.of(this).get(CameraListViewModel.class);
        mCameraListViewModel.searchCameraApi();
        showProgressBar(true);
        subscribeObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTrafficCamera != null && mTrafficCamera.size() > 0) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(TrafficCameraActivity.this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < mTrafficCamera.size(); i++) {
            LatLng latLng = new LatLng(mTrafficCamera.get(i).getLocation().getLatitude(), mTrafficCamera.get(i).getLocation().getLongitude());
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Camera");
            markerOptions.snippet(String.valueOf(i));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            showProgressBar(false);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showProgressBar(true);
                RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
                Glide.with(TrafficCameraActivity.this)
                        .setDefaultRequestOptions(requestOptions)
                        .load(mTrafficCamera.get(Integer.parseInt(marker.getSnippet())).getImage())
                        .into(mLatestPhoto);
                showProgressBar(false);
                return true;
            }
        });
    }

    private void subscribeObservers() {
        mCameraListViewModel.getCameras().observe(this, new Observer<List<TrafficsCamera>>() {
            @Override
            public void onChanged(List<TrafficsCamera> cameras) {
                if (cameras != null) {
                    mTrafficCamera.clear();
                    mTrafficCamera.addAll(cameras);
                }
            }
        });
    }
}
