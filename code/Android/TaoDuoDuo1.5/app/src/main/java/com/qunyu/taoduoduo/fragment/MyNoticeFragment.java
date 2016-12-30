package com.qunyu.taoduoduo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbRequestParams;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.MyNoticeAdapter;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.MyNoticeBean;
import com.qunyu.taoduoduo.bean.UserOrderNoticeBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.Constant;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/5.
 * 我的消息
 */

public class MyNoticeFragment extends Fragment {
    private View view;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_msg)
    PullableListView lv_msg;

    private ArrayList<MyNoticeBean> list;
    MyNoticeAdapter adapter;
    int pageNo = 0;
    private Integer myNoticeSize = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.message_fragment, container, false);
        ButterKnife.bind(this, view);
        list = new ArrayList<>();

        onListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMsg();

    }

    private void onListener() {
        refreshLayout.setOnPullListener(new MyPullListener());
        refreshLayout.setPullDownEnable(true);

    }

    private void getMsg() {
        AbRequestParams params = new AbRequestParams();
        params.put("userId", UserInfoUtils.GetUid());
        LogUtil.Log(Constant.myNoticeApi + "?" + params.getParamString());
        AbHttpUtil.getInstance(getActivity()).get(Constant.myNoticeApi, params, new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result = new AbResult(s);
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<MyNoticeBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<MyNoticeBean>> base = gson.fromJson(s, type);
                    if (base != null && base.result.size() > 0) {
                        list = base.result;
                        adapter = new MyNoticeAdapter(getActivity(), list, myNoticeSize);
                        lv_msg.setAdapter(adapter);
                    } else {

                    }
                } else {
                    ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                try {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });

    }


    //当选中当前fragment时调用
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.Log(isVisibleToUser + "");
        if (isVisibleToUser) {
            getMsg();
        }
    }


    public class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo = 0;
                    getMsg();
                }
            }.sendEmptyMessageDelayed(0, 800);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            try {
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
