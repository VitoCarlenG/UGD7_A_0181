package com.example.ugd7_a_0181;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraView(Context context, Camera camera)
    {
        super(context);
        mCamera = camera;
        mCamera.setDisplayOrientation(90);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder)
    {
        try
        {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch(IOException e)
        {
            Log.d("Error", "Camera Error on SurfaceCreated" + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {
        if(mHolder.getSurface()==null)
        {
            return;
        }
        try
        {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch (IOException e)
        {
            Log.d("Error", "Camera Error on SurfaceCreated" + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder)
    {
        mCamera.stopPreview();
        mCamera.release();
    }
}