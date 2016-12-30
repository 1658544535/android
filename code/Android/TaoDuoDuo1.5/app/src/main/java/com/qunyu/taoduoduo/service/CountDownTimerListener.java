package com.qunyu.taoduoduo.service;

/**
 * Created by Administrator on 2016/10/6.
 */

public interface CountDownTimerListener {
    void onFinish();

    void onTick(long millisUntilFinished);
}
