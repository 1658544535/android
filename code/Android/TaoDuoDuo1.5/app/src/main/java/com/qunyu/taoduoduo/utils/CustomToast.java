package com.qunyu.taoduoduo.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GroupDetailActivity;
import com.qunyu.taoduoduo.activity.PhoneLoginActivity;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.view.CircleTransform;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/11/16.
 */

public class CustomToast {
    private static TextView mTextView;
    private static ImageView mImageView;
    /**
     * 窗体管理者
     */
    private static WindowManager wm;
    private static WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private static View mView;
    private static TextView tv;
    private static Context context1;
    private static boolean is = true;
    public static ArrayList<String> ss = new ArrayList<String>();

    /**
     * 显示自定义吐司
     *
     * @param
     * @param context
     */
    public static void show(final Context context, String message) {
        context1 = context;
        ss.add(message);
        if (is) {
            is = false;
            handler.post(task);//立即调用
        }

    }

    public static Handler handler = new Handler();

    public static Runnable task = new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            wm = (WindowManager) context1.getSystemService(Context.WINDOW_SERVICE);
            //获取屏幕高度
            int height = wm.getDefaultDisplay().getHeight();
            int w = wm.getDefaultDisplay().getWidth();
            try {
                if (ss.size() == 0) {
                    is = true;
                    handler.removeCallbacks(task);
                    return;
                }
                JSONObject json = new JSONObject(ss.get(0));
                final View toastRoot = LayoutInflater.from(context1).inflate(R.layout.push_toast, null);
                //初始化布局控件
                mTextView = (TextView) toastRoot.findViewById(R.id.message);
                mImageView = (ImageView) toastRoot.findViewById(R.id.imageView);
                //为控件设置属性
                mTextView.setText(json.getString("title"));
                mTextView.setTag(json.getString("groupID"));
                LogUtil.Log("666", "666");
//        mImageView.setImageResource(R.mipmap.ic_launcher);
                Glide.with(context1).load(json.getString("image")).transform(new CircleTransform(context1)).placeholder(R.mipmap.default_load).into(mImageView);
                toastRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (UserInfoUtils.isLogin()) {
                            Bundle bundle7 = new Bundle();
                            bundle7.putString("recordId", mTextView.getTag().toString());
                            BaseUtil.ToAcb(context1, GroupDetailActivity.class, bundle7);
                        } else {
                            Bundle b = new Bundle();
                            b.putInt("tag", 99);
                            BaseUtil.ToAcb(context1, PhoneLoginActivity.class, b);//没有登录跳转自登录界面
                        }
                    }
                });
                // 原来TN所做的工作
                WindowManager.LayoutParams params = mParams;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.format = PixelFormat.TRANSLUCENT;
                params.type = WindowManager.LayoutParams.TYPE_TOAST;// 将类型修改成打电话的级别
// 让自定义吐司,在屏幕的左上角
                params.gravity = Gravity.LEFT + Gravity.TOP;
                params.x = w / 40;
                params.y = (int) (height / 6.5);
                params.setTitle("Toast");
                params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        // 删掉了原有吐司中定义的不能被触摸flag
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

                wm.addView(toastRoot, params);
                LogUtil.Log("666", "666");
                new Handler().postDelayed(new Runnable() {

                    public void run() {

                        //execute the task
                        wm.removeView(toastRoot);
                    }

                }, 4000);

                ss.remove(0);//每次删除后jdk都会整理一次集合，效率低
            } catch (Exception js) {
                js.printStackTrace();
            }
            handler.postDelayed(this, 2 * 1000);//设置延迟时间，此处是5秒
            //需要执行的代码
        }
    };

    public static void showToast(final Context context, String message) {
        try {
            JSONObject json = new JSONObject(message);
            //加载Toast布局
            View toastRoot = LayoutInflater.from(context).inflate(R.layout.push_toast, null);
            //初始化布局控件
            mTextView = (TextView) toastRoot.findViewById(R.id.message);
            mImageView = (ImageView) toastRoot.findViewById(R.id.imageView);
            //为控件设置属性
            mTextView.setText(json.getString("title"));
//        mImageView.setImageResource(R.mipmap.ic_launcher);
            Glide.with(context).load(json.getString("image")).transform(new CircleTransform(context)).placeholder(R.mipmap.default_load).into(mImageView);
            toastRoot.setOnTouchListener(new View.OnTouchListener() {
                int startX;
                int startY;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://按下事件
                            Toast.makeText(context, "我被点了", Toast.LENGTH_LONG).show();
                            break;
                        case MotionEvent.ACTION_MOVE://移动事件
                            break;
                        case MotionEvent.ACTION_UP://抬起事件
                            Toast.makeText(context, "我被点了", Toast.LENGTH_LONG).show();
                            break;
                    }
                    return true;
                }
            });
            //Toast的初始化
            Toast toastStart = new Toast(context);
            //获取屏幕高度
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            int w = wm.getDefaultDisplay().getWidth();
            //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
            toastStart.setGravity(Gravity.TOP | Gravity.LEFT, w / 40, (int) (height / 6.5));
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastRoot);
//            toastRoot.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "我被点了", Toast.LENGTH_LONG).show();
//                }
//            });

            showMyToast(toastStart, 4 * 1000);
        } catch (Exception js) {
            js.printStackTrace();
        }


    }

    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }

    public static void showMyToasts(final View toast, final WindowManager.LayoutParams params, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                wm.addView(toast, params);
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                wm.removeView(toast);
                timer.cancel();
            }
        }, cnt);
    }
}
