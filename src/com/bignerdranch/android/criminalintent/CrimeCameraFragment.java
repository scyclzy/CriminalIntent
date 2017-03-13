package com.bignerdranch.android.criminalintent;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {
	
	private static final String TAG = "CrimeCameraFragment";
	
	private Camera mCamera;
	private SurfaceView mSurfaceView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_crime_camera, container, false);
		
		Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
		takePictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				getActivity().finish();
			}
		});
		
		mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceview);
		
		return v;
	}

	@Override
	public void onPause() {
		
		super.onPause();
		
		if(mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD) @Override
	public void onResume() {
		
		super.onResume();
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCamera = Camera.open(0);
		} else {
			mCamera = Camera.open();
		}
	}
	
}
