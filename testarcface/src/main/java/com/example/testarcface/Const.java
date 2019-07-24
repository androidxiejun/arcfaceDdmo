package com.example.testarcface;

import android.Manifest;

import com.arcsoft.face.FaceEngine;

/**
 * Created by AndroidXJ on 2019/7/24.
 */

public class Const {
    public static final String APP_ID = "8zRUsoSbXtdYgbh1gQhd2c93uz1c6EzkRgBZep6PYbH8";
    public static final String SDK_KEY = "ENC4W44Wm1fQ7zXLgmQTN532x5fr38k9KwK6iLs3iE7k";
    public static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    public static final int FACE_ORIEN = FaceEngine.ASF_OP_0_HIGHER_EXT;

    public static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
}
