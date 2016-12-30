package com.qunyu.taoduoduo.receiver;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.CustomToast;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtil.Log(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            UserInfoUtils.setDeviceToken(regId);
            LogUtil.Log(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.Log(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            LogUtil.Log(TAG, "[MyReceiver] 接收到推送下来的自定义消息title: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //全局吐司
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            //获得当前正在运行的activity
//            List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager
//                    .getRunningTasks(1000);
            String runningActivity = mActivityManager.getRunningTasks(1).get(0).topActivity.getClassName();
            String runningActivity1 = getTaskPackname(context);
            LogUtil.Log("Packname:" + runningActivity1);
//            List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager
//                    .getRunningTasks(1000);
//            for (ActivityManager.RunningTaskInfo running : appList3) {
//                System.out.println(running.baseActivity.getClassName() + "+++++++" + runningActivity);
//            }
//            if(Untool.isAppOnForeground()){
            if (runningActivity1.equals("com.qunyu.taoduoduo")) {
                if (runningActivity.equals("com.qunyu.taoduoduo.activity.GoodsDetailActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.PhoneLoginActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.ConfirmOrderActivity")
                        || runningActivity.equals("com.qunyu.taoduoduo.activity.AfterSaleActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.CouponsActivity")
                        || runningActivity.equals("com.qunyu.taoduoduo.activity.MyAddressActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.MyCaiJiaActivity")
                        || runningActivity.equals("com.qunyu.taoduoduo.activity.MyCollectListActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.MyDrawActivity")
                        || runningActivity.equals("com.qunyu.taoduoduo.activity.MyGroupListActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.OrdersActivity")
                        || runningActivity.equals("com.qunyu.taoduoduo.activity.PhoneLoginActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.TuanMianActivity")
                        || runningActivity.equals("com.qunyu.taoduoduo.activity.AboutPdhActivity") || runningActivity.equals("com.qunyu.taoduoduo.activity.ZhouBianActivity")
                        ) {
                    LogUtil.Log(TAG, "666" + bundle.getString(JPushInterface.EXTRA_EXTRA));
                    //全局吐司
                    return;
                }
//        }
//            Toast.makeText(context, "我被点了", Toast.LENGTH_LONG).show();
//            }
                CustomToast.show(context, bundle.getString(JPushInterface.EXTRA_EXTRA));
            }
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtil.Log(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogUtil.Log(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.Log(TAG, "[MyReceiver] 用户点击打开了通知");

            //打开自定义的Activity
//        	Intent i = new Intent(context, TestActivity.class);
//        	i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        	context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtil.Log(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtil.Log(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtil.Log(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    LogUtil.Log(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.Log(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
    }

    /**
     * 获取程序包名(本程序包名5.0版本上下都可获取)
     *
     * @return
     */
    public String getTaskPackname(Context context) {
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int START_TASK_TO_FRONT = 2;
        String currentApp = "CurrentNULL";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            try {
                field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo app : appList) {
                if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Integer state = null;
                    try {
                        state = field.getInt(app);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (state != null && state == START_TASK_TO_FRONT) {
                        currentInfo = app;
                        break;
                    }
                }
            }
            if (currentInfo != null) {
                currentApp = currentInfo.processName;
            }
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }
//        Log.e("TAG", "Current App in foreground is: " + currentApp);
        return currentApp;
    }
}
