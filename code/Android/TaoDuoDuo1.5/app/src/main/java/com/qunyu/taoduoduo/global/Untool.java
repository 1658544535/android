package com.qunyu.taoduoduo.global;

import android.app.ActivityManager;
import android.content.Context;

import com.andbase.library.util.AbSharedUtil;
import com.andbase.library.util.AbStrUtil;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.util.List;

public class Untool {
    static Context context = new MyApplication();

    /**
     * 判断一个程序是否显示在前端,根据测试此方法执行效率在11毫秒,无需担心此方法的执行效率
     *
     * @param packageName程序包名
     * @param context上下文环境
     * @return true--->在前端,false--->不在前端
     */
    public static boolean isApplicationShowing(String packageName,
                                               Context context) {
        boolean result = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : appProcesses) {
                if (runningAppProcessInfo.processName.equals(packageName)) {
                    int status = runningAppProcessInfo.importance;
                    if (status == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
                            || status == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    // 是否登录
    public static Boolean getIsdl() {
        return UserInfoUtils.isLogin();
    }

    // 登录提示
//	public static void showdlts(Context context) {
//		Intent intent2 = new Intent();
//		intent2.setClass(context, DltsActivity.class);
//		context.startActivity(intent2);
//	}
    // 是否第一次打开
    public static Boolean getIsdk() {
        return AbSharedUtil.getBoolean(context, "dk", true);
    }

    // 是否第一次打开
    public static void setIsdk() {
        AbSharedUtil.putBoolean(context, "dk", false);
    }

    // 获取用户id
    public static String getUid() {
        return UserInfoUtils.GetUid();
    }

    // 获取用户昵称
    public static String getName() {
        if (getIsdl()) {
            if (UserInfoUtils.getUserInfo().name != null) {
                return UserInfoUtils.getUserInfo().name;
            } else {
                return "游客";
            }
        } else {
            return "游客";
        }
    }

    // 获取用户手机号码
    public static String getPhone() {
        if (getIsdl()) {
            return UserInfoUtils.getUserInfo().phone;
        } else {
            return "";
        }
    }

    // 获取用户头像
    public static String getImage() {
        if (getIsdl()) {
            return UserInfoUtils.getUserInfo().image;
        } else {
            return "";
        }
    }

    // 设置用户信息并设置登录状态
    public static void setUser(String uid, String u_nickname, String u_phone,
                               String u_image) {
        if (!AbStrUtil.isEmpty(uid)) {
            AbSharedUtil.putString(context, "uid", uid);
        } else {
            AbSharedUtil.putString(context, "uid", null);
        }
        if (!AbStrUtil.isEmpty(u_nickname)) {
            AbSharedUtil.putString(context, "u_nickname", u_nickname);
        } else {
            AbSharedUtil.putString(context, "u_nickname", null);
        }
        if (!AbStrUtil.isEmpty(u_phone)) {
            AbSharedUtil.putString(context, "u_phone", u_phone);
        } else {
            AbSharedUtil.putString(context, "u_phone", null);
        }
        if (!AbStrUtil.isEmpty(u_image)) {
            AbSharedUtil.putString(context, "u_image", u_image);
        } else {
            AbSharedUtil.putString(context, "u_image", null);
        }
        AbSharedUtil.putBoolean(context, "dl", true);
    }

    // 清空用户数据并取消登录状态
    public static void clearUser() {
        AbSharedUtil.putString(context, "uid", null);
        AbSharedUtil.putString(context, "u_nickname", null);
        AbSharedUtil.putString(context, "u_phone", null);
        AbSharedUtil.putString(context, "u_image", null);
        AbSharedUtil.putBoolean(context, "dl", false);
        AbSharedUtil.putString(context, "gwc", null);
    }
}
