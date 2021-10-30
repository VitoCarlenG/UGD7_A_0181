package com.example.ugd7_a_0181;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class FrontCameraView extends AppCompatActivity implements SensorEventListener
{
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Camera mCamera = null;
    private CameraView mCameraView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

        if(mySensor.getType() == Sensor.TYPE_PROXIMITY){
            if(sensorEvent.values[0]!=0){
                if(mCamera!=null)
                {
                    mCamera.stopPreview();
                    mCamera.release();
                }

                try
                {
                    mCamera = Camera.open(1);
                    Toast.makeText(getApplicationContext(), "Kamera Depan", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    Log.d("Error", "Failed to Get Front Camera" + e.getMessage());
                }

                if(mCamera != null){
                    mCameraView = new CameraView(this, mCamera);
                    FrameLayout camera_view = (FrameLayout)findViewById(R.id.FLCamera);
                    camera_view.addView(mCameraView);
                }
                ImageButton imageClose = (ImageButton)findViewById(R.id.imgClose);
                imageClose.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        startActivity(new Intent(FrontCameraView.this, MainActivity.class));
                        finish();
                    }
                });
            }
            else{
                if(mCamera!=null)
                {
                    mCamera.stopPreview();
                    mCamera.release();
                }

                try
                {
                    mCamera = Camera.open(0);
                    Toast.makeText(getApplicationContext(), "Kamera Belakang", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    Log.d("Error", "Failed to Get Back Camera" + e.getMessage());
                }

                if(mCamera != null){
                    mCameraView = new CameraView(this, mCamera);
                    FrameLayout camera_view = (FrameLayout)findViewById(R.id.FLCamera);
                    camera_view.addView(mCameraView);
                }
                ImageButton imageClose = (ImageButton)findViewById(R.id.imgClose);
                imageClose.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        startActivity(new Intent(FrontCameraView.this, MainActivity.class));
                        finish();
                    }
                });
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }
}