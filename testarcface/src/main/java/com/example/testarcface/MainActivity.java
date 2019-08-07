package com.example.testarcface;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.Face3DAngle;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.VersionInfo;
import com.example.testarcface.arc.ArcHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    //相机ID，角度
    private final int CAMERA_ID = 0;
    private final int DEGRESS = 270;
    private final int PRE_WIDTH = 1280;
    private final int PRE_HEIGHT = 720;

    private SurfaceView mSurfaceView;
    private FaceRectView mFaceRectView;
    private DrawHelper drawHelper;

    private Camera.Size previewSize;


    private int afCode = -1;

    private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;

    private FaceEngine faceEngine = new FaceEngine();

    private static final int MAX_DETECT_NUM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initCamera();
        activeEngine();
//        initEngine();
        faceEngine();
    }

    private void initCamera() {
        ICameraListenner listenner = new ICameraListenner() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, mSurfaceView.getWidth(), mSurfaceView.getHeight(), displayOrientation
                        , cameraId, isMirror, false, false);
            }

            @Override
            public void onPreview(byte[] nv21, Camera camera) {
                Log.d(TAG, "返回的像素数据------" + Arrays.toString(nv21));
//                ArcHelper.getInstance().setEngine(faceEngine);
//
//                ArcHelper.getInstance().onPreviewFrame(nv21, previewSize);


                List<FaceInfo> faceInfoList = new ArrayList<>();
                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
                    if (code != ErrorInfo.MOK) {
                        return;
                    }
                } else {
                    return;
                }

                List<AgeInfo> ageInfoList = new ArrayList<>();
                List<GenderInfo> genderInfoList = new ArrayList<>();
                List<Face3DAngle> face3DAngleList = new ArrayList<>();
                List<LivenessInfo> faceLivenessInfoList = new ArrayList<>();
                int ageCode = faceEngine.getAge(ageInfoList);
                int genderCode = faceEngine.getGender(genderInfoList);
                int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
                int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);

                //有其中一个的错误码不为0，return
                if ((ageCode | genderCode | face3DAngleCode | livenessCode) != ErrorInfo.MOK) {
                    return;
                }

                if (mFaceRectView != null && drawHelper != null) {
                    List<DrawInfo> drawInfoList = new ArrayList<>();
                    for (int i = 0; i < faceInfoList.size(); i++) {
                        drawInfoList.add(new DrawInfo(drawHelper.adjustRect(faceInfoList.get(i).getRect()), genderInfoList.get(i).getGender(), ageInfoList.get(i).getAge(), faceLivenessInfoList.get(i).getLiveness(), null));
                    }
                    drawHelper.draw(mFaceRectView, drawInfoList);
                }


            }

            @Override
            public void onCameraClosed() {

            }
        };

        CameraManager.getInstance().init(CAMERA_ID, DEGRESS, PRE_WIDTH, PRE_HEIGHT, Color.BLUE);
        CameraManager.getInstance().openCamera(mSurfaceView, this);
        CameraManager.getInstance().setListenner(listenner);


    }

    private void initView() {
        mSurfaceView = findViewById(R.id.face);
        mFaceRectView = findViewById(R.id.face_rect);
    }

    /**
     * 激活引擎
     */
    public void activeEngine() {
        if (!Util.checkPermissions(MainActivity.this, Const.NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, Const.NEEDED_PERMISSIONS, Const.ACTION_REQUEST_PERMISSIONS);
            return;
        }
        int activeCode = faceEngine.activeOnline(MainActivity.this, Const.APP_ID, Const.SDK_KEY);

        if (activeCode == ErrorInfo.MOK) {
            Log.d(TAG, "激活成功------");
        } else {
            Log.d(TAG, "激活失败-----" + activeCode);
        }

        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        int res = faceEngine.getActiveFileInfo(MainActivity.this, activeFileInfo);
        if (res == ErrorInfo.MOK) {
            Log.d(TAG, "成功--------------" + activeFileInfo.toString());
        } else {
            Log.d(TAG, "失败----------" + res);
        }

    }

    /**
     * 激活人脸相关引擎
     */
    private void faceEngine() {
        afCode = faceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, FaceEngine.ASF_OP_0_HIGHER_EXT,
                16, 20, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);
        if (afCode != ErrorInfo.MOK) {
            Log.d(TAG, "引擎激活失败-------=" + afCode);
        } else {
            Log.d(TAG, "引擎激活成功-------");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Const.ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine();
            } else {
                Log.d(TAG, "权限开启失败-----");
            }
        }
    }

    /**
     * 初始化引擎
     */
    private void initEngine() {
        faceEngine = new FaceEngine();
        afCode = faceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, Const.FACE_ORIEN,
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.d(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);
    }

}
