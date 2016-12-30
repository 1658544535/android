package com.qunyu.taoduoduo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.meiqia.core.MQManager;
import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQAgent;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.qunyu.taoduoduo.fragment.PersonalCenterFragment;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.view.BadgeView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取 ACTION
        final String action = intent.getAction();
        // 接收新消息
        if (MQMessageManager.ACTION_NEW_MESSAGE_RECEIVED.equals(action)) {
            // 从 intent 获取消息 id
//            String msgId = intent.getStringExtra("msgId");
//            // 从 MCMessageManager 获取消息对象
//            MQMessageManager messageManager = MQMessageManager.getInstance(context);
//            MQMessage message = messageManager.getMQMessage(msgId);
            // do something
            MQManager.getInstance(context).getUnreadMessages(Untool.getUid(), new OnGetMessageListCallback() {
                @Override
                public void onSuccess(List<MQMessage> list) {
                    LogUtil.Log("消息未读数:" + list.size());
//                    PersonalCenterFragment.setInt(list.size());
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }

        // 客服正在输入
        else if (MQMessageManager.ACTION_AGENT_INPUTTING.equals(action)) {
            // do something
        }

        // 客服转接
        else if (MQMessageManager.ACTION_AGENT_CHANGE_EVENT.equals(action)) {
            // 获取转接后的客服
//            MQAgent mqAgent = messageManager.getCurrentAgent();
            // do something
        }
    }
}