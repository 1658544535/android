package com.qunyu.taoduoduo.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.global.MyApplicationLike;

/**
 * Created by Administrator on 2016/10/16.
 */

public class PermissionUtil {
    public static String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE};

    public static boolean isLacksOfPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(
                    MyApplicationLike.context, permission) == PackageManager.PERMISSION_DENIED;
        }
        return false;
    }

}
