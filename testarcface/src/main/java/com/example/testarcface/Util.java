package com.example.testarcface;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by AndroidXJ on 2019/7/24.
 */

public class Util {
    /**
     * 判断是否拥有权限
     *
     * @param neededPermissions
     * @return
     */
    public static boolean checkPermissions(Context context, String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(context, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

}
