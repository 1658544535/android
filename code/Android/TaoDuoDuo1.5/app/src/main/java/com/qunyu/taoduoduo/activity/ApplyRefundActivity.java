package com.qunyu.taoduoduo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
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
import com.qunyu.taoduoduo.api.ApplyRefundApi;
import com.qunyu.taoduoduo.api.OrderDetailApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.OrderDetailBean;
import com.qunyu.taoduoduo.config.AppConfig;
import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.PictureUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/9/28.
 * 申请售后退款
 */

public class ApplyRefundActivity extends BaseActivity implements View.OnClickListener{
    private LayoutInflater inflater;
    private View sp_layout;
    private ListView lv_popuWindow;
    private PopupWindow sp_window;
    private ArrayAdapter<String> adapter, spinnerAdapter;
    private ArrayList<String> list;

    private String imagePath1 = "";// 图片路径1
    private String imagePath2 = "";// 图片路径2
    private String imagePath3 = "";// 图片路径3

    //    @BindView(R.id.spinner)
//    Spinner spinner;
//    @BindView(R.id.spinner1)
//    Spinner spinner1;
    @BindView(R.id.layout_refundType)
    PercentRelativeLayout layout_refundType;
    @BindView(R.id.layout_refundMoney)
    PercentRelativeLayout layout_refundMoney;
    @BindView(R.id.layout_refundReason)
    PercentRelativeLayout layout_refundReason;
    @BindView(R.id.layout_refundDescribe)
    PercentRelativeLayout layout_refundDescribe;
    @BindView(R.id.tv_refundType)
    TextView tv_refundType;
    @BindView(R.id.tv_refundMoney)
    TextView tv_refundMoney;
    @BindView(R.id.tv_refundReason)
    TextView tv_refundReason;
    @BindView(R.id.tv_refundDescribe)
    TextView tv_refundDescribe;
    @BindView(R.id.tv_refundMoneyMost)
    TextView getTv_refundMoneyMost;
    @BindView(R.id.tv_contactWay)
    EditText tv_contactWay;


    @BindView(R.id.iv_uploadVoucher1)
    ImageView iv_voucher1;
    @BindView(R.id.iv_submitApply)
    ImageView iv_submit;
    @BindView(R.id.iv_uploadVoucher2)
    ImageView iv_voucher2;
    @BindView(R.id.iv_uploadVoucher3)
    ImageView iv_voucher3;

    String refundMoney;
    int refundType;
    int type;
    String oid;
    String phone;
    String orderStatus;
    private ApplyRefundApi applyRefundApi;

    OrderDetailBean orderDetailBean;
    OrderDetailBean.AddressInfo addressInfo;
    OrderDetailApi orderDetailApi;

    String[] mDatas;
    int mCurrentItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.apply_refund_activity);
        ButterKnife.bind(this);
        orderDetailBean=new OrderDetailBean();
        addressInfo=new OrderDetailBean().new AddressInfo();
        refundMoney=getIntent().getStringExtra("price");//退款价格
        oid=getIntent().getStringExtra("oid");//订单id
        orderStatus=getIntent().getStringExtra("orderStatus");
        //phone=getIntent().getStringExtra("phone");//联系方式
        list=new ArrayList<>();
        getPhone();
        onListener();
        // tv_refundMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tv_contactWay.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    protected void init() {
        super.init();
        baseSetText("申请退款");
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void onListener(){
        layout_refundType.setOnClickListener(this);
        layout_refundMoney.setOnClickListener(this);
        layout_refundReason.setOnClickListener(this);
        layout_refundDescribe.setOnClickListener(this);
        iv_voucher1.setOnClickListener(this);
        iv_voucher2.setOnClickListener(this);
        iv_voucher3.setOnClickListener(this);
        iv_submit.setOnClickListener(this);
        if(tv_refundMoney!=null) {
            tv_refundMoney.setText(refundMoney);
        }
        getTv_refundMoneyMost.setText("最高可退￥"+refundMoney);

    }

    private void getPhone(){
        orderDetailApi=new OrderDetailApi();
        orderDetailApi.setOid(oid);
        AbHttpUtil.getInstance(this).get(orderDetailApi.getUrl(), orderDetailApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result=new AbResult(s);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<OrderDetailBean>>(){}.getType();
                    BaseModel<OrderDetailBean> base=gson.fromJson(s,type);
                    if(base!=null){
                        addressInfo=base.result.getAddressInfo();
                        phone=addressInfo.getTel();
                        tv_contactWay.setText(phone);
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
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });
    }

    private void onText(final TextView v){
        lv_popuWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                v.setText(list.get(position));
                setTextToNum();
                sp_window.dismiss();
            }
        });
    }

    /*
    * 根据文本获取数字
    * */
    private void setTextToNum() {
        String refundReason = tv_refundReason.getText().toString().trim();
        if (StringUtils.isNotBlank(refundReason)) {
            switch (refundReason) {
                case "没有收到货":
                    refundType = 1;
                    break;
                case "商品有质量问题":
                    refundType = 2;
                    break;
                case "商品与描述不一致":
                    refundType = 3;
                    break;
                case "商品少发漏发发错":
                    refundType = 4;
                    break;
                case "商品有划痕":
                    refundType = 5;
                    break;
                case "质疑假货":
                    refundType = 6;
                    break;
                case "其他":
                    refundType = 7;
                    break;
            }

        }
        String refundTypes = tv_refundType.getText().toString();
        if (refundTypes != "") {
            switch (refundTypes) {
                case "仅退款":
                    type = 1;
                    break;
                case "我要退货":
                    type = 2;
                    break;
            }
        }
    }

    private ArrayList<String> getData(int index){
        list.clear();
        switch(index){
            case 1:
                if(StringUtils.isNotBlank(orderStatus)&&orderStatus.equals("2")){
                    list.add("仅退款");
                }else {
                    list.add("仅退款");
                    list.add("我要退货");
                }
                break;
            case 2:
                list.add("商品有质量问题");
                list.add("没有收到货");
                list.add("商品少发漏发发错");
                list.add("商品与描述不一致");
                list.add("收到商品时有划痕或破损");
                list.add("质疑假货");
                list.add("其他");
                break;
        }
        return list;
    }

    private void showPopuwindow(TextView tv,PercentRelativeLayout view,ArrayList<String> datas) {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sp_layout = inflater.inflate(R.layout.applyrefund_poplayout, null);
        lv_popuWindow = (ListView) sp_layout.findViewById(R.id.lv_popuWindow);
        adapter=new ArrayAdapter<String>(this,R.layout.textitem,datas);
        lv_popuWindow.setAdapter(adapter);
        sp_window = new PopupWindow(sp_layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sp_window.setWidth((int)(dm.widthPixels*0.97));
        sp_window.setAnimationStyle(R.style.AnimBottom);
        sp_window.setFocusable(true);
        sp_window.setOutsideTouchable(true);
        sp_window.update();
        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.white));
        sp_window.setBackgroundDrawable(dw);
        sp_window.showAsDropDown(view,0,0);
        onText(tv);
    }

//    private void showSpinner(final TextView v,Spinner spinner){
//        spinnerAdapter=new ArrayAdapter<String>(this,R.layout.textitem,getData(2));
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(spinnerAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                v.setText(parent.getItemAtPosition(position).toString().trim());
//                String refundReason=tv_refundReason.getText().toString().trim();
//                if(StringUtils.isNotBlank(refundReason)){
//                    switch (refundReason){
//                        case "没有收到货":
//                            refundType=1;
//                            break;
//                        case "商品有质量问题":
//                            refundType=2;
//                            break;
//                        case "商品与描述不一致":
//                            refundType=3;
//                            break;
//                        case "商品少发漏发发错":
//                            refundType=4;
//                            break;
//                        case "商品有划痕":
//                            refundType=5;
//                            break;
//                        case "质疑假货":
//                            refundType=6;
//                            break;
//                        case "其他":
//                            refundType=7;
//                            break;
//                    }
//                }
//                String refundTypes=tv_refundType.getText().toString();
//                if(refundTypes!=""){
//                    switch (refundTypes){
//                        case "仅退款":
//                            type=1;
//                            break;
//                        case "我要退货":
//                            type=2;
//                            break;
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }

    private void applyRefund(){
        AbHttpUtil httpUtil=AbHttpUtil.getInstance(this);
        applyRefundApi=new ApplyRefundApi();
        if(imagePath1!=""){
            try {
                applyRefundApi.setImage1(new File(imagePath1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(imagePath2!=""){
            try {
                applyRefundApi.setImage2(new File(imagePath2));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(imagePath3!=""){
            try {
                applyRefundApi.setImage3(new File(imagePath3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        applyRefundApi.setOid(oid);
        applyRefundApi.setPhone(tv_contactWay.getText().toString());
        applyRefundApi.setPrice(tv_refundMoney.getText().toString());
        applyRefundApi.setRefundReason(tv_refundDescribe.getText().toString());
        applyRefundApi.setRefundType(refundType+"");
        applyRefundApi.setType(type+"");
        applyRefundApi.setUid(UserInfoUtils.GetUid());
        LogUtil.Log(applyRefundApi.getUrl() + "?" + applyRefundApi.getParamMap().getParamString());

        httpUtil.post(applyRefundApi.getUrl(), applyRefundApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result=new AbResult(content);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    try {
                        JSONObject jsonObject=new JSONObject(content);
                        ToastUtils.showShortToast(ApplyRefundActivity.this, jsonObject.optString("error-msg"));
                        if(jsonObject.optBoolean("success")){
                            ToastUtils.showShortToast(ApplyRefundActivity.this, "提交申请成功");
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    ToastUtils.showShortToast(ApplyRefundActivity.this, "网络异常，数据加载失败");
                    LogUtil.Log(result.getResultMessage());
                    if(result.getResultMessage()==null){
                        ToastUtils.showShortToast(ApplyRefundActivity.this, "提交申请失败");
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
                ToastUtils.showShortToast(ApplyRefundActivity.this, "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
                if(error.getMessage()==null){
                    ToastUtils.showShortToast(ApplyRefundActivity.this, "提交申请失败");
                }
            }
        });





    }

    private void initDataForIntent(int index, int requestcode) {
        mDatas = getData(index).toArray(new String[getData(index).size()]);//将ArrayList转化为数组
        Intent intent = new Intent(ApplyRefundActivity.this, ApplyRefundSelectListActivity.class);
        Bundle b = new Bundle();
        b.putStringArray("mDatas", mDatas);
        intent.putExtras(b);
        startActivityForResult(intent, requestcode);
    }
    @Override
    public void onClick(View v) {
//        Double refundMoney1=Double.parseDouble(refundMoney);
//        Double writeM= Double.parseDouble(tv_refundMoney.getText().toString());
//        if(writeM>refundMoney1){
//            tv_refundMoney.setText(refundMoney);
//        }
        switch(v.getId()){

            case R.id.iv_submitApply:
                if(!tv_refundType.getText().equals("")&&!tv_refundMoney.getText().equals("")&&!tv_refundReason.getText().equals("") && !tv_contactWay.getText().equals("")){
                    applyRefund();
                }else{
                    ToastUtils.showShortToast(ApplyRefundActivity.this, "售后信息填写不完整");
                }

                break;
            case R.id.layout_refundType:

                /**
                 * 下面是使用wheelview样式
                 */
                initDataForIntent(1, 1);

                break;
            case R.id.layout_refundReason:
                initDataForIntent(2, 2);
                break;
            case R.id.iv_uploadVoucher1:
                Intent intent1 = new Intent(this, SelectPicActivity.class);
                //startActivity(intent1);
                intent1.putExtra("imgId", R.id.iv_uploadVoucher1);
                startActivityForResult(intent1,1);
                break;
            case R.id.iv_uploadVoucher2:
                Intent intent2 = new Intent(this, SelectPicActivity.class);
                intent2.putExtra("imgId", R.id.iv_uploadVoucher2);
                startActivityForResult(intent2, 2);
                break;
            case R.id.iv_uploadVoucher3:
                Intent intent3 = new Intent(this, SelectPicActivity.class);
                intent3.putExtra("imgId", R.id.iv_uploadVoucher3);
                startActivityForResult(intent3, 3);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            ImageView img=null;
            switch(requestCode){
                case 1:
                    img = (ImageView) findViewById(R.id.iv_uploadVoucher1);
                    break;
                case 2:
                    img = (ImageView) findViewById(R.id.iv_uploadVoucher2);
                    break;
                case 3:
                    img = (ImageView) findViewById(R.id.iv_uploadVoucher3);
                    break;
            }

                img.setImageBitmap(null);
                String picPath = data
                        .getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
                LogUtil.Log(picPath);
                if (picPath != null && !StringUtils.isBlank(picPath)) {
                    LogUtil.Log(requestCode + "最终选择的图片=" + picPath);
                    Bitmap bm = PictureUtil.getSmallBitmap(picPath);
                    img.setImageBitmap(bm);
                    switch(requestCode){
                        case 1:
                            iv_voucher2.setVisibility(View.VISIBLE);
                            imagePath1=picPath;
                            break;
                        case 2:
                            iv_voucher3.setVisibility(View.VISIBLE);
                            imagePath2=picPath;
                            break;
                        case 3:
                            imagePath3=picPath;
                            break;
                    }
                }
        }
        /*
        * 使用wheelview样式*/
        else if (resultCode == AppConfig.RESULT_REFUND) {
            mCurrentItem = data.getIntExtra("currentItem", 0);
            switch (requestCode) {
                case 1:
                    tv_refundType.setText(mDatas[mCurrentItem]);
                    break;
                case 2:
                    tv_refundReason.setText(mDatas[mCurrentItem]);
                    break;
            }
            setTextToNum();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
