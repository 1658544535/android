package com.qunyu.taoduoduo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.MyAddressListAdapter;
import com.qunyu.taoduoduo.api.DeleteAddressApi;
import com.qunyu.taoduoduo.api.EditAddressApi;
import com.qunyu.taoduoduo.api.MyAddressListApi;
import com.qunyu.taoduoduo.api.SelectAddressApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.MyAddressBean;
import com.qunyu.taoduoduo.fragment.AddressDialogFragment;
import com.qunyu.taoduoduo.pulltorefresh.PullToRefreshLayout;
import com.qunyu.taoduoduo.pulltorefresh.PullableListView;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/28.
 * 我的地址
 */

public class MyAddressActivity extends BaseActivity {
    public static  Handler handler;
    public static boolean islistNull;

    private MyAddressListApi addressListApi;
    private DeleteAddressApi deleteAddressApi;
    private EditAddressApi editAddressApi;
    private SelectAddressApi selectAddressApi;

    public static ArrayList<MyAddressBean> list;
    private MyAddressListAdapter adapter;

    @BindView(R.id.refresh_view)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.lv_myAddress)
    PullableListView listView;
    @BindView(R.id.btn_addAddress)
    TextView btn_addAddress;
    @BindView(R.id.tv_orderNull)
    TextView tv_orderNull;
    @BindView(R.id.iv_orderNull)
    ImageView iv_orderNull;

    int pageId;//页码
    public static Integer addId=null;//地址ID
    public static Integer mCurrentPosition=null;//当前position


    //添加地址弹出框
    AddressDialogFragment fragment;

    String tag;//选择地址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.my_address_activity);
        addressListApi=new MyAddressListApi();
        list=new ArrayList<MyAddressBean>();
        pageId=1;
        ButterKnife.bind(this);
        tag = getIntent().getStringExtra("tag");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(StringUtils.isNotBlank(tag)&&tag.equals("selectaddress")){
                    MyAddressBean myAddressBean = (MyAddressBean) parent.getAdapter().getItem(position);
                    Intent intent = new Intent();
                    intent.putExtra("myAddressBean", myAddressBean);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressListMsg();
        setListener();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void setListener() {
        refreshLayout.setOnPullListener(new MyPullListener());
        refreshLayout.setPullDownEnable(true);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    addId = msg.arg2;
                    mCurrentPosition = msg.arg1;
                    new AlertDialog.Builder(MyAddressActivity.this)
                            .setMessage("确定删除该地址")
                            .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteAddress();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();

                } else if (msg.what == 2) {
                    getAddressListMsg();
                }else if(msg.what==3){
                    mCurrentPosition=msg.arg1;
                     addId=msg.arg2;
                    fragment=new AddressDialogFragment();
                    fragment.show(getSupportFragmentManager(),"AddressDialogFragment");
                }else if(msg.what==4){
                    addId=msg.arg2;
                    selectAddress();
                }

            }
        };
        //添加地址
        btn_addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new AddressDialogFragment();
                fragment.show(getSupportFragmentManager(),"AddressDialogFragment");
            }
        });


    }

    //地址列表信息
    private void getAddressListMsg() {
        addressListApi.setPageId(String.valueOf(pageId));
        addressListApi.setUid(UserInfoUtils.GetUid());
        AbHttpUtil.getInstance(this).get(addressListApi.getUrl(), addressListApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult();
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<BaseModel<ArrayList<MyAddressBean>>>() {
                    }.getType();
                    BaseModel<ArrayList<MyAddressBean>> base = gson.fromJson(content, type);
                    if (base.result != null) {
                        list.clear();
                        if (pageId == 1) {
                            if (base.result.size() > 0) {
                                list = base.result;
                                adapter = new MyAddressListAdapter(MyAddressActivity.this, list, handler);
                                listView.setAdapter(adapter);
                                tv_orderNull.setVisibility(View.GONE);
                                iv_orderNull.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.VISIBLE);
                            } else {
                                tv_orderNull.setVisibility(View.VISIBLE);
                                iv_orderNull.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                                listView.setVisibility(View.GONE);
                            }
                        } else {
                            if (base.result.size() > 0) {
                                list.addAll(base.result);
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    } else {
                        tv_orderNull.setVisibility(View.VISIBLE);
                        iv_orderNull.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                        listView.setVisibility(View.GONE);
                    }
                    //判断地址列表知否为空
                    if(base.result.size()==0){
                        islistNull=true;
                    }else{
                        islistNull=false;
                    }
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
                ToastUtils.showShortToast(MyAddressActivity.this, "网络异常，数据加载失败");
            }
        });
    }

    //删除地址
    private void deleteAddress() {
        deleteAddressApi = new DeleteAddressApi();
        deleteAddressApi.setAddId(addId + "");
        deleteAddressApi.setUid(UserInfoUtils.GetUid());
        AbHttpUtil.getInstance(this).get(deleteAddressApi.getUrl(), deleteAddressApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult();
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    try {
                        JSONObject json = new JSONObject(content);
                        ToastUtils.showShortToast(MyAddressActivity.this, json.optString("error_msg"));
                        if (json.optBoolean("success")) {
                            getAddressListMsg();
                            addId = null;
                            mCurrentPosition=null;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(MyAddressActivity.this, "网络异常，数据加载失败");

            }
        });
    }

    //设置默认地址
    private void selectAddress() {
        selectAddressApi = new SelectAddressApi();
        selectAddressApi.setAddId(addId + "");
        selectAddressApi.setUid(UserInfoUtils.GetUid());
        AbHttpUtil.getInstance(this).get(selectAddressApi.getUrl(), selectAddressApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult();
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    try {
                        JSONObject json = new JSONObject(content);
                        ToastUtils.showShortToast(MyAddressActivity.this, json.optString("error_msg"));
                        if (json.optBoolean("success")) {
                            getAddressListMsg();
                            addId = null;
                            mCurrentPosition=null;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailure(int i, String s, Throwable error) {
                ToastUtils.showShortToast(MyAddressActivity.this, "网络异常，数据加载失败");

            }
        });
    }

    class MyPullListener implements PullToRefreshLayout.OnPullListener {

        @Override
        public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageId=1;
                    getAddressListMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }

        @Override
        public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    pageId++;
                    getAddressListMsg();
                }
            }.sendEmptyMessageDelayed(0,1600);
        }
    }
    @Override
    protected void init() {
        super.init();
        baseSetText("我的地址");
    }
}
