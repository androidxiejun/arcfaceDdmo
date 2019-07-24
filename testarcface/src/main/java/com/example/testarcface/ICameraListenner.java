package com.example.testarcface;

import android.hardware.Camera;

/**
 * Created by AndroidXJ on 2019/7/24.
 */

public interface ICameraListenner {
    void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror);

    void onPreview(byte[] data, Camera camera);

    void onCameraClosed();
}
