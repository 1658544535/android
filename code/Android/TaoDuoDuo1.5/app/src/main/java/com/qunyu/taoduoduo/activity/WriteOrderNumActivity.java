package com.qunyu.taoduoduo.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.KuaidigongsiApi;
import com.qunyu.taoduoduo.api.SubmitLogisticsApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.KuaiDiGongSiBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/28.
 * 填写运单号
 */

public class WriteOrderNumActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.tv_logisticsType)
    TextView tv_Type;
    @BindView(R.id.tv_logisticsNum)
    EditText tv_Num;
    @BindView(R.id.iv_submit)
    ImageView iv_submit;
    @BindView(R.id.layout_type)
    PercentRelativeLayout layout_type;
    String oid;//订单id
    String logisticsName;

    private LayoutInflater inflater;
    private View sp_layout;
    private ListView lv_popuWindow;
    private PopupWindow sp_window;

    private KuaidigongsiApi kuaidigongsiApi;
    private KuaiDiGongSiBean bean;
    private ArrayList<KuaiDiGongSiBean> list;
    private ArrayList<String> llist;
    private ArrayAdapter<String> adapter;

    private SubmitLogisticsApi submitLogisticsApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.write_order_num_activity);
        ButterKnife.bind(this);
        oid=getIntent().getStringExtra("oid");
        bean=new KuaiDiGongSiBean();
        list=new ArrayList<>();
        llist=new ArrayList<>();
        layout_type.setOnClickListener(this);
        getLogisticsMsg();
        onListener();

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void onListener() {
        iv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_Type.getText().toString().equals("")) {
                    ToastUtils.showShortToast(WriteOrderNumActivity.this, "快递类型必须填写！");
                } else if (tv_Num.getText().toString().equals("")) {
                    ToastUtils.showShortToast(WriteOrderNumActivity.this, "快递单号必须填写！");
                } else {
                    submitLogistics();
                }
            }
        });
    }

    private void submitLogistics(){
        submitLogisticsApi=new SubmitLogisticsApi();
        submitLogisticsApi.setLogisticsName(logisticsName);
        submitLogisticsApi.setLogisticsNum(tv_Num.getText().toString());
        submitLogisticsApi.setOid(oid);
        submitLogisticsApi.setUid(UserInfoUtils.GetUid());
        AbHttpUtil.getInstance(this).get(submitLogisticsApi.getUrl(), submitLogisticsApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    try {
                        JSONObject jsonObject=new JSONObject(content);
                        ToastUtils.showShortToast(WriteOrderNumActivity.this, jsonObject.optString("error_msg"));
                        if(jsonObject.optBoolean("success")){
                            finish();
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
                ToastUtils.showShortToast(WriteOrderNumActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    private void getLogisticsMsg(){
        kuaidigongsiApi=new KuaidigongsiApi();
        AbHttpUtil.getInstance(this).get(kuaidigongsiApi.getUrl(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result=new AbResult(s);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<ArrayList<KuaiDiGongSiBean>>>(){}.getType();
                    BaseModel<ArrayList<KuaiDiGongSiBean>> base=gson.fromJson(s,type);
                    if(base.result!=null){
                        list=base.result;
                        for(int a = 0; a<list.size(); a++){
                            llist.add(list.get(a).getName());
                        }
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
                ToastUtils.showShortToast(WriteOrderNumActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

    private void showPopuwindow( PercentRelativeLayout view, ArrayList<String> datas) {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sp_layout = inflater.inflate(R.layout.applyrefund_poplayout, null);
        lv_popuWindow = (ListView) sp_layout.findViewById(R.id.lv_popuWindow);
        adapter=new ArrayAdapter<String>(this,R.layout.textitem,datas);
        lv_popuWindow.setAdapter(adapter);
        sp_window = new PopupWindow(sp_layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sp_window.setWidth((int)(dm.widthPixels*0.97));
        sp_window.setHeight((int)(dm.widthPixels*0.7));

        sp_window.setFocusable(true);
        sp_window.setOutsideTouchable(true);
        sp_window.update();
        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.white));
        sp_window.setBackgroundDrawable(dw);
        sp_window.showAsDropDown(view,0,0);

        lv_popuWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_Type.setText(llist.get(position));
                logisticsName = list.get(position).getNameEn();
                sp_window.dismiss();
            }
        });

    }

    @Override
    protected void init() {
        super.init();
        baseSetText("填写运单号");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_type) {
            showPopuwindow(layout_type,llist);
        }
    }
}
