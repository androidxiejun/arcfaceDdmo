package com.example.testarcface;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * CreateDate：2018/10/18
 * Creator： VNBear
 * Description:
 **/
public class ArcCamera implements SurfaceHolder.Callback {

    private SurfaceView mPreSurface;
    private Camera mCamera;

    private WeakReference<Activity> mActivity;


    //相机的位置
    private int mCameraId;
    //相机的方向
    private int mDegress;
    //相机预览的宽高
    private int mPreviewSizeX, mPreviewSizeY;
    //人脸框的颜色
    private int mColor;


    private ArcCamera() {
    }

    private static final class InstanceHolder {
        private static ArcCamera INSTANCE = new ArcCamera();
    }

    public static ArcCamera getInstance() {
        return InstanceHolder.INSTANCE;
    }


    /**
     * 初始化相机参数
     *
     * @param cameraId 相机id
     * @param degress  画面旋转角度
     * @param color    人脸框颜色
     */
    public void init(int cameraId, int degress, int color) {
        init(cameraId, degress, 0, 0, color);
    }

    public void init(int cameraId, int degress, int width, int height, int color) {
        this.mCameraId = cameraId;
        this.mDegress = degress;
        this.mPreviewSizeX = width;
        this.mPreviewSizeY = height;
        this.mColor = color;
    }

    /**
     * 开始预览
     *
     * @param activity
     * @param surfacePreview
     */
    public void openCamera(Activity activity, SurfaceView surfacePreview) {
        this.mActivity = new WeakReference<>(activity);
        this.mPreSurface = surfacePreview;
        mPreSurface.getHolder().addCallback(this);
    }


    public void closeCamera(){
        mActivity = null;
        if (mCamera !=null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            mActivity = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //打开指定相机
            mCamera = Camera.open();
            DisplayMetrics metrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            //设置相机预览大小
            Camera.Parameters parameters = mCamera.getParameters();
            if (mPreviewSizeX == 0 || mPreviewSizeY == 0) {
                Camera.Size previewSize = getBestSupportedSize(parameters.getSupportedPreviewSizes(), metrics);
                mPreviewSizeX = previewSize.width;
                mPreviewSizeY = previewSize.height;
            }

//            String param = parameters.flatten();
//            Log.i("MainA", "相机参数-----" + param);

            parameters.setPreviewSize(mPreviewSizeX, mPreviewSizeY);

//            if (mCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                //设置镜像效果，支持的值为flip-mode-values=off,flip-v,flip-h,flip-vh;
//                 parameters.set("preview-flip", "flip-h");
//            }

            mCamera.setParameters(parameters);
            //设置画面旋转角度
            mCamera.setDisplayOrientation(mDegress);
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(final byte[] data, Camera camera) {

                }
            });
            mCamera.startPreview();
        } catch (Exception e) {
            mCamera = null;
            e.printStackTrace();
//            Toast.makeText(mActivity.get().getApplicationContext(), "摄像头开启失败", Toast.LENGTH_SHORT).show();
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
            mActivity = null;
        }
    }




    private Activity getActivity() {
        return mActivity.get();
    }

//    private void drawFace(List<AFT_FSDKFace> ftList) {
//        if (mRectSurface != null) {
//
//            Canvas canvas = mRectSurface.getHolder().lockCanvas();
//            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//            if (ftList != null && ftList.size() > 0) {
//                /*Paint paint = new Paint();
//                paint.setColor(Color.RED);
//                paint.setStyle(Paint.Style.FILL);
//                paint.setStrokeWidth(5);
//                paint.setTextSize(80);*/
//
//                for (AFT_FSDKFace fsdkFace : ftList) {
//                    Rect raw = fsdkFace.getRect();
//                    Rect rect = new Rect(raw);
//                    if (rect != null) {
//                        //根据相机ID和画面预览大小调整人脸框位置
//                        Rect adjustedRect = ArcUtils.adjustRect(rect, mPreviewSizeX, mPreviewSizeY,
//                                canvas.getWidth(), canvas.getHeight(), mDegress, mCameraId, false);
//
//
//                        //画人脸框
//                        ArcUtils.drawFaceRect(canvas, adjustedRect, mColor, 1);
//
//                        //画人员姓名
//                        /*if (rect.right < mPreviewSizeX - 100) {
//                            canvas.drawText("张三", rect.right + 30, rect.bottom, paint);
//                        } else {
//                            canvas.drawText("张三", rect.left - 30, rect.bottom, paint);
//                        }*/
//
//                        //回调接口给外部人脸的位置信息
//                        if (mCameraPreviewListener != null) {
//                            Canvas temp = mCameraPreviewListener.onDrawFace(canvas, adjustedRect, mPreviewSizeX, mPreviewSizeY, mDegress, mCameraId);
//                            if (temp != null) {
//
//                            }
//                        }
//                    }
//                }
//            }
//            mRectSurface.getHolder().unlockCanvasAndPost(canvas);
//        }
//    }

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
}
