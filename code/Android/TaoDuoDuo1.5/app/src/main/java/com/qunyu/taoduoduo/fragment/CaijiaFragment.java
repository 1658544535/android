package com.qunyu.taoduoduo.fragment;

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
import com.qunyu.taoduoduo.activity.CaiGoodsDetailActivity;
import com.qunyu.taoduoduo.adapter.MyCaijiaListAdapter;
import com.qunyu.taoduoduo.api.MyCaijiaListApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.base.BaseUtil;
import com.qunyu.taoduoduo.bean.MyCaijiaListBean;
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
 * Created by Administrator on 2016/9/27.
 * 我的猜价fragment
 */

public class CaijiaFragment extends Fragment {
    View view;
    String type;//列表类型(0全部,1进行中,2未得奖,3已得奖)
    int pageNo;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    PullableListView listView;
    @BindView(R.id.tv_orderNull)
    TextView tv_orderNull;
    @BindView(R.id.iv_orderNull)
    ImageView iv_orderNull;

    private MyCaijiaListAdapter adapter;
    private ArrayList<MyCaijiaListBean> list;

    public static final CaijiaFragment newInstance(String type){
        CaijiaFragment f=new CaijiaFragment();
        Bundle b=new Bundle();
        b.putString("type",type);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.caijia_fragment,null,false);
        type=getArguments().getString("type");
        ButterKnife.bind(this,view);
        listView= (PullableListView) view.findViewById(R.id.lv_mycaijia);
        list=new ArrayList<>();
        pageNo=1;
        init();
        onListener();
        return view;
    }

    private void init(){
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        getCaijiaMsg();
    }

    private void onListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("activityId", list.get(position).getActivityId());
                b.putString("productId", list.get(position).getProductId());
                BaseUtil.ToAcb(getActivity(), CaiGoodsDetailActivity.class, b);
            }
        });
    }

    private void getCaijiaMsg(){
        MyCaijiaListApi  myCaijiaListApi=new MyCaijiaListApi();
        myCaijiaListApi.setPageNo(pageNo+"");
        myCaijiaListApi.setType(type);
        myCaijiaListApi.setUserId(UserInfoUtils.GetUid());
        LogUtil.Log(myCaijiaListApi.getUrl() + "?" + myCaijiaListApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(myCaijiaListApi.getUrl(), myCaijiaListApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<ArrayList<MyCaijiaListBean>>>(){}.getType();
                    BaseModel<ArrayList<MyCaijiaListBean>> base=gson.fromJson(content,type);
                    if (base.success && base.result != null && !base.result.isEmpty()) {
                        if(pageNo==1){
                            list = base.result;
                            adapter = new MyCaijiaListAdapter(getActivity(), list);
                            listView.setAdapter(adapter);
                        }else{
                            list.addAll(base.result);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        if (pageNo == 1) {
                            tv_orderNull.setVisibility(View.VISIBLE);
                            iv_orderNull.setVisibility(View.VISIBLE);
                            refreshLayout.setVisibility(View.GONE);
                            listView.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                try {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
            }
        });

    }

    public class MyPullListener implements PullToRefreshLayout.OnPullListener{

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo=1;
                    getCaijiaMsg();
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
                    getCaijiaMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }
    }
}
