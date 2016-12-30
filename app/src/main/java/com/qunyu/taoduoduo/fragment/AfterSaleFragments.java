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

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.GoodsDetailActivity;
import com.qunyu.taoduoduo.adapter.AfterSaleAdapter;
import com.qunyu.taoduoduo.api.AfterSaleListApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.AfterSaleListBean;
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
 * Created by Administrator on 2016/9/28.
 */

public class AfterSaleFragments extends Fragment {
    private View view;
    public String status;//售后状态(0全部,1审核中,2审核通过,3审核不同,4完成)
    private ArrayList<AfterSaleListBean> list;
    private AfterSaleListApi afterSaleListApi;

    int pageNo;
    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_afterSale)
    PullableListView listView;
    private AfterSaleAdapter adapter;





   public static final AfterSaleFragments newInstance(String status){
       AfterSaleFragments f=new AfterSaleFragments();
       Bundle bundle=new Bundle();
       bundle.putString("status",status);
       f.setArguments(bundle);
       return f;
   }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.after_sale_fragment,null,false);
        status=getArguments().getString("status");
        ButterKnife.bind(this,view);
        pageNo=1;
        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getRefundListMsg();

    }

    private void init(){
        list=new ArrayList<>();
        afterSaleListApi=new AfterSaleListApi();
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setOnPullListener(new MyPullListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("pid", list.get(position).getProductId());
                intent.putExtra("tag", "caijia");
                startActivity(intent);
            }
        });
    }

    private void getRefundListMsg(){
        afterSaleListApi.setPageNo(pageNo+"");
        afterSaleListApi.setStatus(status);
        afterSaleListApi.setUserId(UserInfoUtils.GetUid());
        LogUtil.Log(afterSaleListApi.getUrl() + "?" + afterSaleListApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(getActivity()).get(afterSaleListApi.getUrl(), afterSaleListApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<ArrayList<AfterSaleListBean>>>(){}.getType();
                    BaseModel<ArrayList<AfterSaleListBean>> base =gson.fromJson(content,type);
                    if(base.result!=null && base.result.size()>0){
                        list.clear();
                        if(pageNo==1){
                            list=base.result;
                            adapter=new AfterSaleAdapter(getActivity(),list);
                            listView.setAdapter(adapter);
                        }else{
                            list.addAll(base.result);
                            if(adapter!=null){
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }else{
                    ToastUtils.showShortToast(getActivity(),"网络异常，数据加载失败");
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
                ToastUtils.showShortToast(getActivity(),"网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    class MyPullListener implements PullToRefreshLayout.OnPullListener{

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageNo=1;
                    getRefundListMsg();
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
                    getRefundListMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }
    }
}
