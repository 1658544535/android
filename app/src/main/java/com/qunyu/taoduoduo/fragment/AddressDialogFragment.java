package com.qunyu.taoduoduo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.activity.CitiesActivity;
import com.qunyu.taoduoduo.activity.MyAddressActivity;
import com.qunyu.taoduoduo.api.AddAddressApi;
import com.qunyu.taoduoduo.api.AddressDetailApi;
import com.qunyu.taoduoduo.api.EditAddressApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.AddressDetailBean;
import com.qunyu.taoduoduo.bean.MyAddressBean;
import com.qunyu.taoduoduo.global.Untool;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.ToastUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/5.
 * 添加地址弹窗
 */


public class AddressDialogFragment extends DialogFragment implements View.OnClickListener{

    @BindView(R.id.btn_commit)
    TextView btn_commit;
    @BindView(R.id.edt_shouhuoren)
    EditText edt_shouhuoren;
    @BindView(R.id.edt_lianxifangshi)
    EditText edt_lianxifangshi;
    @BindView(R.id.edt_addressDetial)
    EditText edt_addressDetial;
    @BindView(R.id.tv_address1)
    TextView tv_address1;
    @BindView(R.id.tv_address2)
    TextView tv_address2;
    @BindView(R.id.tv_address3)
    TextView tv_address3;

    private ArrayAdapter<String> adapter,adapter1,adapter2;

    public static Handler mhandler,mhandler1;
    View view, sp_layout;
    LayoutInflater inflater;
    PopupWindow sp_window;
    ListView lv_popuWindow;

    protected String[] mProvinceDatas;//所有省
    String[] cityNames;
    String[] areaNames;

    protected Map<String, String[]> mCitisDatasMap = new HashMap<>();//省
    protected Map<String, String[]> mAreaDatasMap = new HashMap<>();//市
    protected Map<String, Integer> map = new HashMap<>();//区
    protected Map<Integer,String> stringMap=new HashMap<>();
    protected Map<Integer,Integer> positionMap=new HashMap<>();//地区id---position

    protected String mCurrentProviceName;//当前省名称
    protected String mCurrentCityName;//当前市名称
    protected String mCurrentAreaName;//当前区名称

    protected Integer provinceId;
    protected Integer cityId;
    protected Integer areaId;

    private Integer addId = null;//地址id
    private Integer mCurrentPosition=null;//当前地址item
    private ArrayList<MyAddressBean> mlist;
    private MyAddressBean bean;

    private Integer isDefault;
    private int index;
    private final static String fileName = "province.json";

    //地址信息
    private AddAddressApi addAddressApi;
    private EditAddressApi editAddressApi;
    private AddressDetailApi addressDetailApi;
    private AddressDetailBean addressDetailBean;

    String text_shouhuoren, text_lianxifangshi, text_province, text_city, text_area;
    String text_detailAddress;




    @Override
    public void onStart() {
        super.onStart();
        //设置弹出框大小
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shap_corner_address);
        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.87), (int) (dm.heightPixels * 0.37));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.addaddress_dialog, null);
        ButterKnife.bind(this, view);
        mlist=new ArrayList<>();
        addressDetailBean=new AddressDetailBean();
        edt_lianxifangshi.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        init();
        return view;
    }

    private void init() {
        tv_address1.setOnClickListener(this);
        tv_address2.setOnClickListener(this);
        tv_address3.setOnClickListener(this);
        mCurrentPosition = MyAddressActivity.mCurrentPosition;

        //修改地址addid获取
        if (mCurrentPosition != null) {
            bean=new MyAddressBean();
            mlist=MyAddressActivity.list;
            bean=mlist.get(mCurrentPosition);
            isDefault= bean.getIsDefault();
            edt_shouhuoren.setText(bean.getName());
            edt_lianxifangshi.setText(bean.getTel());
            addId=Integer.parseInt(bean.getAddId());
           getAddressDetail();
        }
        //提交按钮
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_shouhuoren = edt_shouhuoren.getText().toString().trim();
                text_lianxifangshi = edt_lianxifangshi.getText().toString().trim();
                text_province = provinceId + "";
                text_city = cityId + "";
                text_area = areaId + "";
                text_detailAddress = edt_addressDetial.getText().toString().trim();

                if (StringUtils.isEmpty(text_shouhuoren)) {
                    ToastUtils.showShortToast(getActivity(), "请填写收货人!");
                    return;
                }
                if (StringUtils.isEmpty(text_lianxifangshi)) {
                    ToastUtils.showShortToast(getActivity(), "请输入正确的联系号码!");
                    return;
                }
                if (!StringUtils.isMobileNO(text_lianxifangshi)) {
                    ToastUtils.showShortToast(getActivity(), "请输入正确的联系号码!");
                    return;
                }

                if (!text_province.equals("")
                        && !text_city.equals("") && !text_area.equals("") && !text_detailAddress.equals("")) {
                    if (addId == null) {//addid不为空则修改地址、为空则新增地址
                        commitAddress();
                    } else {
                        edtAddress();
                    }
                } else {
                    ToastUtils.showShortToast(getActivity(), "填写的地址信息不完整！");
                }
            }
        });

    }
    //设置地址详情
    private void setAddresssDetail(){
        edt_addressDetial.setText(addressDetailBean.getAddress());
        tv_address1.setText(addressDetailBean.getProvinceName());
        tv_address2.setText(addressDetailBean.getCityName());
        tv_address3.setText(addressDetailBean.getAreaName());
        provinceId=Integer.parseInt(addressDetailBean.getProvince());
        cityId=Integer.parseInt(addressDetailBean.getCity());
        areaId=Integer.parseInt(addressDetailBean.getArea());

        mCurrentPosition=null;
        MyAddressActivity.mCurrentPosition=null;
    }

    //提交地址
    private void commitAddress() {
        addAddressApi = new AddAddressApi();
        if(MyAddressActivity.islistNull){
            addAddressApi.setIsDefault("1");
        }else{
            addAddressApi.setIsDefault("0");
        }

        addAddressApi.setAddress(text_detailAddress);
        addAddressApi.setArea(text_area);
        addAddressApi.setCity(text_city);
       // addAddressApi.setIsDefault("0");//默认地址（取业务字典：0否1是）
        addAddressApi.setName(text_shouhuoren);
        addAddressApi.setPostCode("");
        addAddressApi.setProvince(text_province);
        addAddressApi.setTel(text_lianxifangshi);
        addAddressApi.setUid(Untool.getUid());
        AbHttpUtil.getInstance(getActivity()).get(addAddressApi.getUrl(), addAddressApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int statusCode, String content) {
                AbResult result = new AbResult();
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    try {
                        JSONObject json = new JSONObject(content);
                        ToastUtils.showShortToast(getActivity(), json.optString("error_msg"));
                        if (json.optBoolean("success")) {
                            Dismiss();
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
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
                Dismiss();
            }
        });
    }

    //修改地址
    private void edtAddress() {
        editAddressApi = new EditAddressApi();
        editAddressApi.setAddId(addId + "");
        editAddressApi.setAddress(text_detailAddress);
        editAddressApi.setArea(text_area);
        editAddressApi.setCity(text_city);
        editAddressApi.setIsDefault(isDefault+"");
        editAddressApi.setName(text_shouhuoren);
        editAddressApi.setPostCode("");
        editAddressApi.setProvince(text_province);
        editAddressApi.setTel(text_lianxifangshi);
        editAddressApi.setUid(Untool.getUid());
        AbHttpUtil.getInstance(getActivity()).get(editAddressApi.getUrl(), editAddressApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String content) {
                AbResult result = new AbResult();
                if (result.getResultCode() == AbResult.RESULT_OK) {
                    try {
                        JSONObject json = new JSONObject(content);
                        ToastUtils.showShortToast(getActivity(), json.optString("error_msg"));
                        if (json.optBoolean("success")) {
                            mCurrentPosition=null;
                            MyAddressActivity.mCurrentPosition=null;
                            Dismiss();
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
                ToastUtils.showShortToast(getActivity(), "网络异常，数据加载失败");
                LogUtil.Log(error.getMessage());
            }
        });
    }

  //  获取详情地址
    private void getAddressDetail(){
        addressDetailApi=new AddressDetailApi();
        addressDetailApi.setUid(UserInfoUtils.GetUid());
        addressDetailApi.setAddId(addId+"");
        AbHttpUtil.getInstance(getActivity()).get(addressDetailApi.getUrl(), addressDetailApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                AbResult result=new AbResult(s);
                if(result.getResultCode()==AbResult.RESULT_OK){
                    Gson gson=new Gson();
                    Type type=new TypeToken<BaseModel<AddressDetailBean>>(){}.getType();
                    BaseModel<AddressDetailBean> base=gson.fromJson(s,type);
                    if(base.result!=null){
                        addressDetailBean=base.result;
                        setAddresssDetail();

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


    private void Dismiss() {
        Message msg = Message.obtain();
        msg.what = 2;
        MyAddressActivity.handler.sendMessage(msg);
        this.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_address1:
               // spinnerText1.performClick();
                startActivityForResult(new Intent(getActivity(),
                        CitiesActivity.class), 0);
                break;
            case R.id.tv_address2:
               // spinnerText2.performClick();
                startActivityForResult(new Intent(getActivity(),
                        CitiesActivity.class), 0);
                break;
            case R.id.tv_address3:
               // spinnerText3.performClick();
                startActivityForResult(new Intent(getActivity(),
                        CitiesActivity.class), 0);
                break;

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mCurrentProviceName = data.getStringExtra("mCurrentProviceName");
            mCurrentCityName = data.getStringExtra("mCurrentCityName");
            mCurrentAreaName = data.getStringExtra("mCurrentAreaName");
            tv_address1.setText(mCurrentProviceName);
            tv_address2.setText(mCurrentCityName);
            tv_address3.setText(mCurrentAreaName);

            provinceId = data.getIntExtra("provinceId", 0);
            cityId = data.getIntExtra("cityId", 0);
            areaId = data.getIntExtra("areaId", 0);
            LogUtil.Log("provinceId:" + provinceId + ";cityId:" + cityId
                    + ";areaId:" + areaId);
        }
    }

}
