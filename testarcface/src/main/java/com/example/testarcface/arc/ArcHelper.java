package com.example.testarcface.arc;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.LivenessInfo;
import com.example.testarcface.Const;
import com.example.testarcface.MainActivity;
import com.example.testarcface.bean.FacePreviewInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by AndroidXJ on 2019/7/24.
 */

public class ArcHelper {
    private static ArcHelper mInstance;
    private FaceEngine faceEngine;
    private List<Integer> formerTrackIdList = new ArrayList<>();
    private List<FaceInfo> faceInfoList = new ArrayList<>();
    private List<Integer> currentTrackIdList = new ArrayList<>();
    private List<FaceInfo> formerFaceInfoList = new ArrayList<>();
    private List<LivenessInfo> livenessInfoList = new ArrayList<>();
    private List<FacePreviewInfo> facePreviewInfoList = new ArrayList<>();
    private ConcurrentHashMap<Integer, String> nameMap = new ConcurrentHashMap<>();

    private int processMask = FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;

    private int currentTrackId = 0;

    public static ArcHelper getInstance() {
        synchronized (ArcHelper.class) {
            if (mInstance == null) {
                mInstance = new ArcHelper();
            }
            return mInstance;
        }
    }

    public void setEngine(FaceEngine faceEngine) {
        this.faceEngine = faceEngine;
    }

//    public boolean init(Context context) {
//        if (faceEngine == null && context != null) {
//            faceEngine = new FaceEngine();
//            int engineCode = faceEngine.init(context, FaceEngine.ASF_DETECT_MODE_VIDEO, Const.FACE_ORIEN, 16, 1, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT);
//            Log.d(MainActivity.TAG, "ArcHelper---init----=" + engineCode);
//            if (engineCode == ErrorInfo.MOK) {
//                return true;
//            } else {
//                faceEngine = null;
//                return false;
//            }
//        }
//        return true;
//    }

    public List<FacePreviewInfo> onPreviewFrame(byte[] nv21, Camera.Size size) {
        if (faceEngine != null) {
            faceInfoList.clear();
            long ftStartTime = System.currentTimeMillis();
            int code = faceEngine.detectFaces(nv21, size.width, size.height, FaceEngine.CP_PAF_NV21, faceInfoList);
            Log.d(MainActivity.TAG, "分析人脸特征--1---=====" + code);
            if (code != ErrorInfo.MOK) {
                return null;
            }
              /*
                 * 活体检测只支持一个人脸，所以只保留最大的人脸
                 * 若需要多人脸搜索，删除此行代码，并且关闭活体判断
                 */
            TrackUtil.keepMaxFace(faceInfoList);
            refreshTrackId(faceInfoList);

            code = faceEngine.process(nv21, size.width, size.height, FaceEngine.CP_PAF_NV21, faceInfoList, FaceEngine.ASF_LIVENESS);
            Log.d(MainActivity.TAG, "分析人脸特征--2---=====" + code);
            if (code != ErrorInfo.MOK) {
                return null;
            }

            code = faceEngine.getLiveness(livenessInfoList);
            Log.d(MainActivity.TAG, "分析人脸特征--3---=====" + code);
            if (code != ErrorInfo.MOK) {
                return null;
            }

            facePreviewInfoList.clear();
            if (livenessInfoList.size() == faceInfoList.size()) {
                for (int i = 0; i < faceInfoList.size(); i++) {
                    facePreviewInfoList.add(new FacePreviewInfo(faceInfoList.get(i), livenessInfoList.get(i), currentTrackIdList.get(i)));
                }
            }
            Log.d(MainActivity.TAG, "人脸的数量-------" + facePreviewInfoList.size());
            return facePreviewInfoList;
        }
        return null;
    }

    /**
     * 由人脸数据生成人脸特征
     *
     * @param context
     * @param nv21
     * @param width
     * @param height
     * @param faceInfo
     */
    public boolean registerNv21(Context context, byte[] nv21, int width, int height, FaceInfo faceInfo) {
        synchronized (ArcHelper.class) {
            if (context == null || nv21 == null || width % 4 != 0 || nv21.length != width * height * 3 / 2) {
                return false;
            }
            FaceFeature faceFeature = new FaceFeature();
            //特征提取
            int code = faceEngine.extractFaceFeature(nv21, width, height, FaceEngine.CP_PAF_NV21, faceInfo, faceFeature);
            Log.d(MainActivity.TAG, "特征提取code=======" + code);
        }
        return false;
    }

    /**
     * 刷新trackId
     *
     * @param ftFaceList 传入的人脸列表
     */
    private void refreshTrackId(List<FaceInfo> ftFaceList) {
        currentTrackIdList.clear();
        //每项预先填充-1
        for (int i = 0; i < ftFaceList.size(); i++) {
            currentTrackIdList.add(-1);
        }
        //前一次无人脸现在有人脸，填充新增TrackId
        if (formerTrackIdList.size() == 0) {
            for (int i = 0; i < ftFaceList.size(); i++) {
                currentTrackIdList.set(i, ++currentTrackId);
            }
        } else {
            //前后都有人脸,对于每一个人脸框
            for (int i = 0; i < ftFaceList.size(); i++) {
                //遍历上一次人脸框
                for (int j = 0; j < formerFaceInfoList.size(); j++) {
                    //若是同一张人脸
                    if (TrackUtil.isSameFace(formerFaceInfoList.get(j), ftFaceList.get(i))) {
                        //记录ID
                        currentTrackIdList.set(i, formerTrackIdList.get(j));
                        break;
                    }
                }
            }
        }
        //上一次人脸框不存在此人脸，新增
        for (int i = 0; i < currentTrackIdList.size(); i++) {
            if (currentTrackIdList.get(i) == -1) {
                currentTrackIdList.set(i, ++currentTrackId);
            }
        }
        formerTrackIdList.clear();
        formerFaceInfoList.clear();
        for (int i = 0; i < ftFaceList.size(); i++) {
            formerFaceInfoList.add(ftFaceList.get(i));
            formerTrackIdList.add(currentTrackIdList.get(i));
        }

        //刷新nameMap
        clearLeftName(currentTrackIdList);
    }

    /**
     * 清除map中已经离开的人脸
     *
     * @param trackIdList 最新的trackIdList
     */
    private void clearLeftName(List<Integer> trackIdList) {
        Set<Integer> keySet = nameMap.keySet();
        for (Integer integer : keySet) {
            if (!trackIdList.contains(integer)) {
                nameMap.remove(integer);
            }
        }
    }
}
