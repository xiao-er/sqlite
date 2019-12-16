package com.xiaoxiao.sqlite.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 潇潇
 * @create on:  2019/12/10
 * @describe:DOTO
 */

public class PermissionUtils {
    public static List<String> permissionList = new ArrayList<>();

    public static final String permission[] = {//权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE, // SD写入卡权限
            Manifest.permission.READ_CONTACTS, // SD写入卡权限
            Manifest.permission.READ_EXTERNAL_STORAGE, // 读取权限
    };

    /**
     * 检查权限权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static List<String> checkAndRequestPermission(Activity activity, String[] permis) {
        if (permissionList != null && permissionList.size() > 0) {
            permissionList.clear();
        }
        int checkSelfPermission;
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permis) {
                checkSelfPermission = PermissionChecker.checkSelfPermission(activity,permission);
                if (PackageManager.PERMISSION_GRANTED == checkSelfPermission) {
                    continue;
                }
                permissionList.add(permission);
            }
        }
        return permissionList;
    }


    /**
     * 请求权限
     */
    public static boolean isPowerRequest(Activity activity, String[] permisson, int requestCode) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        List<String> permissionList = PermissionUtils.checkAndRequestPermission(activity, permisson);
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), requestCode);
            return false;
        } else {
            return true;
        }
    }




    public static boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }


}
