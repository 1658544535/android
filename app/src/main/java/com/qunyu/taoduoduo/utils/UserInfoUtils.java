package com.qunyu.taoduoduo.utils;

import com.andbase.library.util.AbSharedUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.bean.UserInfoBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.global.MyApplicationLike;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class UserInfoUtils {
    private final static String USERINFO = "userinfo";
    private final static String SEARCHHISTORY = "searchhistory";
    private final static String FIRSTLOGIN = "firstloginv141";


    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserInfoBean getUserInfo() {
        String json = AbSharedUtil.getString(MyApplicationLike.context, USERINFO, null);
        UserInfoBean user = null;
        if (!StringUtils.isBlank(json)) {
            // json不为空
            Gson gson = new Gson();
            Type type = new TypeToken<UserInfoBean>() {
            }.getType();
            try {
                user = gson.fromJson(json, type);
            } catch (Exception e) {
                LogUtil.ErrorLog(e);
                return null;
            }
        }
        return user;

    }

    /**
     * 设置用户信息
     *
     * @param info
     */
    public static void setUserInfo(UserInfoBean info) {
        Gson gson = new Gson();
        String json = gson.toJson(info);
        AbSharedUtil.putString(MyApplicationLike.context, USERINFO, json);
        MyApplicationLike.setLoginUser(info);
    }


    /**
     * 用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        UserInfoBean u = MyApplicationLike.getLoginUser();
        return u == null ? false : true;

    }

    /**
     * 退出
     */
    public static void signOut() {
        MyApplicationLike.setLoginUser(null);
        AbSharedUtil.remove(MyApplicationLike.context, USERINFO);
        AppConfig.MYNOTIC_SIZE = 0;//退出登录时我的消息size重置为0
    }

    /**
     * 更新用户信息
     *
     * @param info
     */
    public static void updateUserInfo(UserInfoBean info) {
        signOut();
        setUserInfo(info);
    }

    public static String GetUid() {
        String uid;
        if (UserInfoUtils.isLogin()) {
            uid = MyApplicationLike.getLoginUser().uid;
        } else {
            uid = "0";
        }

        return uid;
    }

    /**
     * 获取历史缓存
     *
     * @return
     */
    public static List<String> getSearchHistory() {
        String json = AbSharedUtil.getString(MyApplicationLike.context, SEARCHHISTORY, null);
//        String json=MyApplication.getInstance().sp.getString(SEARCHHISTORY,null);
        ArrayList<String> list = new ArrayList<String>();
        if (!StringUtils.isBlank(json)) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            try {
                list = gson.fromJson(json, type);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return new ArrayList<String>();
            }
        }
        return list;
    }

    /**
     * 设置搜索历史缓存
     *
     * @param list
     */
    public static void setSearchHistory(
            List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        AbSharedUtil.putString(MyApplicationLike.context, SEARCHHISTORY, json);
        //MyApplication.getInstance().sp.setString(SEARCHHISTORY, json);
    }

    /**
     * 清空搜索历史缓存
     */
    public static void clearSearchHistory() {
        AbSharedUtil.remove(MyApplicationLike.context, SEARCHHISTORY);
    }

    /**
     * 设置Device Token
     *
     * @param devicetoken
     */
    public static void setDeviceToken(String devicetoken) {
        AbSharedUtil.putString(MyApplicationLike.context, "devicetoken", devicetoken);
    }

    /**
     * 获取Device Token
     */
    public static String getDeviceToken() {
        return AbSharedUtil.getString(MyApplicationLike.context, "devicetoken", null);
    }

    /**
     * 记录是否第一次打开
     *
     * @param isFirst
     */
    public static void setFirst(Boolean isFirst) {
        AbSharedUtil.putBoolean(MyApplicationLike.context, FIRSTLOGIN, isFirst);
    }

    /**
     * 是否第一次打开
     *
     * @return
     */
    public static Boolean getIsFirst() {
        return AbSharedUtil.getBoolean(MyApplicationLike.context, FIRSTLOGIN, true);
    }
}
