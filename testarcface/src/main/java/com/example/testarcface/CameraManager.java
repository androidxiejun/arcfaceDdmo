package com.example.testarcface;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AndroidXJ on 2019/7/23.
 */

public class CameraManager implements SurfaceHolder.Callback {

    private static CameraManager mInstance;
    private SurfaceView mPreSurfaceView;
    private Camera mCamera;
    private Activity mContext;


    //相机的位置
    private int mCameraId;
    //相机的方向
    private int mDegress;
    //相机预览的宽高
    private int mPreviewSizeX, mPreviewSizeY;
    //人脸框的颜色
    private int mColor;

    private ICameraListenner mListenner;

    public static CameraManager getInstance() {
        synchronized (CameraManager.class) {
            if (mInstance == null) {
                mInstance = new CameraManager();
            }
            return mInstance;
        }
    }

    public void setListenner(ICameraListenner listenner) {
        this.mListenner = listenner;
    }

    public void init(int cameraId, int degress, int width, int height, int color) {
        this.mCameraId = cameraId;
        this.mDegress = degress;
        this.mPreviewSizeX = width;
        this.mPreviewSizeY = height;
        this.mColor = color;
    }


    public void openCamera(SurfaceView preSurfaceView, Activity context) {
        this.mPreSurfaceView = preSurfaceView;
        this.mContext = context;
        mPreSurfaceView.getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open(mCameraId);
            DisplayMetrics metrics = new DisplayMetrics();
            mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            Camera.Parameters parameters = mCamera.getParameters();

            parameters.setPreviewFormat(ImageFormat.NV21);

            if (mPreviewSizeX == 0 || mPreviewSizeY == 0) {
                Camera.Size previewSize = getBestSupportedSize(parameters.getSupportedPreviewSizes(), metrics);
                mPreviewSizeX = previewSize.width;
                mPreviewSizeY = previewSize.height;
            }

            parameters.setPreviewSize(mPreviewSizeX, mPreviewSizeY);
            mCamera.setParameters(parameters);

            //设置画面旋转角度
            mCamera.setDisplayOrientation(mDegress);
            mCamera.setPreviewDisplay(holder);

            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
//                    Log.d(MainActivity.TAG, "像素数据---------" + Arrays.toString(data));
                    mListenner.onPreview(data, camera);
                }
            });
            mCamera.startPreview();

            mListenner.onCameraOpened(mCamera, mCameraId, mDegress, false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            mContext = null;
        }
    }

    /**
     * 获取最佳的预览大小
     *
     * @param sizes
     * @param metrics
     * @return
     */
    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, DisplayMetrics metrics) {
        Camera.Size bestSize = sizes.get(0);
        float screenRatio = (float) metrics.widthPixels / (float) metrics.heightPixels;
        if (screenRatio > 1) {
            screenRatio = 1 / screenRatio;
        }
        for (Camera.Size s : sizes) {
            Log.d("=======", "width:" + s.width + "  , height:" + s.height);
            if (Math.abs((s.height / (float) s.width) - screenRatio) < Math.abs(bestSize.height /
                    (float) bestSize.width - screenRatio)) {
                bestSize = s;
            }
        }
        return bestSize;
    }

    public void onDestroy() {

    }
}
