package com.qunyu.taoduoduo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.adapter.MyGroupListAdapter;
import com.qunyu.taoduoduo.api.MyGroupListApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.MyGroupListBean;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout.OnPullListener;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/12.
 */

public class MyGroupListFragment extends Fragment {
    private View view;
    private String mstatus;//0-全部1-拼团中2-拼团结束3-拼团失败

    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_mygroup)
    PullableListView listView;
    @BindView(R.id.tv_orderNull)
    TextView tv_orderNull;
    @BindView(R.id.iv_orderNull)
    ImageView iv_orderNull;

    int pageNo;

    private MyGroupListApi myGroupListApi;
    private ArrayList<MyGroupListBean> list;
    private MyGroupListAdapter adapter;



    public static MyGroupListFragment newInstance(String status){
        MyGroupListFragment fragment=new MyGroupListFragment();
        Bundle b=new Bundle();
        b.putString("status",status);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.mygroup_list_fragment,null,false);
        ButterKnife.bind(this,view);
        mstatus=getArguments().getString("status");
        list=new ArrayList<>();
        pageNo=1;
        onListener();
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getMsg();
    }

    private void init(){

    }

    private void onListener(){
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("activityId",list.get(position).getActivityId());
                intent.putExtra("pid",list.get(position).getProductId());
                startActivity(intent);
            }
        });
    }

    private void getMsg(){
        myGroupListApi=new MyGroupListApi();
        myGroupListApi.setPageNo(pageNo+"");
        myGroupListApi.setStatus(mstatus);
        myGroupListApi.setUserId(UserInfoUtils.GetUid());
        LogUtil.Log(myGroupListApi.getUrl() + "?" + myGroupListApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(myGroupListApi.getUrl(), myGroupListApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result=new AbResult(s);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<ArrayList<MyGroupListBean>>>(){}.getType();
                    BaseModel<ArrayList<MyGroupListBean>> base=gson.fromJson(s,type);
                    if (base.result != null) {
                        list.clear();
                        if(pageNo==1){
                            if (base.result.size() > 0) {
                                list = base.result;
                                adapter = new MyGroupListAdapter(getActivity(), list);
                                listView.setAdapter(adapter);
                            } else {
                                tv_orderNull.setVisibility(View.VISIBLE);
                                iv_orderNull.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                                listView.setVisibility(View.GONE);
                            }

                        }else{
                            if (base.result.size() > 0) {
                                list.addAll(base.result);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        tv_orderNull.setVisibility(View.VISIBLE);
                        iv_orderNull.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                    }
                }else{
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
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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


    private class MyPullListener implements OnPullListener{

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo=1;
                    getMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo++;
                    getMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }
    }
}
