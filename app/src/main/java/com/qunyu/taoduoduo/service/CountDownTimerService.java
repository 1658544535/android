package com.qunyu.taoduoduo.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.qunyu.taoduoduo.utils.LogUtil;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/10/6.
 */

public class CountDownTimerService extends Service {
    CountDownTimerListener mcountDownTimerListener;
    TimeCount timeCount;

    public ServiceBinder mBinder = new ServiceBinder();

    public void setCountDownTimerListener(CountDownTimerListener countDownTimerListener) {
        mcountDownTimerListener = countDownTimerListener;
    }
    public class ServiceBinder  extends Binder {

    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (mcountDownTimerListener != null) {
                mcountDownTimerListener.onFinish();
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            if (mcountDownTimerListener != null) {
                mcountDownTimerListener.onTick(millisUntilFinished);
            }

        }

    }


    public CountDownTimerService() {
        super();
    }

    @Override
    public void onCreate() {
        LogUtil.Log("onCreate================");
        //timeCount = new TimeCount(60000, 1000);
        //timeCount.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.Log("onStartCommand================");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.Log("onDestroy================");
        //timeCount.cancel();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.Log("onBind================");
        return mBinder;
    }



    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.Log("onUnbind================");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        LogUtil.Log("onRebind================");
        super.onRebind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(fd, writer, args);
    }
}
