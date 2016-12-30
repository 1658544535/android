package com.qunyu.taoduoduo.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.api.GainCouponApi;
import com.qunyu.taoduoduo.api.GetProductSkusApi;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.ProductSkuBean;
import com.qunyu.taoduoduo.mvpview.BaseView;
import com.qunyu.taoduoduo.mvpview.EventActivityView;
import com.qunyu.taoduoduo.mvpview.SkuModeView;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.StringUtils;
import com.qunyu.taoduoduo.utils.UserInfoUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * sku模块
 * Created by Administrator on 2016/12/21
 */


public class SkuPresenter {
    SkuModeView view;
    Context context;
    GetProductSkusApi productSkusApi;
    BaseView baseView;

    ProductSkuBean productSkuBean;
    public List<ProductSkuBean.SkuValue> skuColorValue;
    public List<ProductSkuBean.SkuValue> skuFormatValue;
    public List<ProductSkuBean.ValidSKu> validSKu;
    public List<ProductSkuBean.SkuList> skuList;
    public int skucolorMAxsize = 0;
    public int skuformatMAxsize = 0;

    public String skuColor;
    public String skuFormat;

    public String skuimage = null;//sku图片

    public final int SKU_MODEL_SINGLE = 1; //一种规格
    public final int SKU_MODEL_TWO = 2; //两种规格

    public int SKU_MODEL;

    public int num = 1;

    public SkuPresenter(SkuModeView view, Context context, BaseView baseView) {
        this.view = view;
        this.context = context;
        this.baseView = baseView;
        productSkusApi = new GetProductSkusApi();


    }

    public void loadSku(String pid) {
        productSkusApi.setPid(pid);
        LogUtil.Log(productSkusApi.getUrl() + "?" + productSkusApi.getParamMap().getParamString());
        try {
            AbHttpUtil.getInstance(context).get(productSkusApi.getUrl(), productSkusApi.getParamMap(), new AbStringHttpResponseListener() {
                @Override
                public void onSuccess(int i, String s) {

                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<ProductSkuBean>>() {
                        }.getType();
                        BaseModel<ProductSkuBean> base = gson.fromJson(s, type);
                        if (base.success && base.result != null) {
                            productSkuBean = base.result;
                            if (StringUtils.isNotBlank(productSkuBean.spceType) && productSkuBean.spceType.equals("1")) {
                                //一种规格
                                SKU_MODEL = SKU_MODEL_SINGLE;
                            } else {
                                //2种规格
                                SKU_MODEL = SKU_MODEL_TWO;
                            }
                            LogUtil.Log("SKU_MODEL", SKU_MODEL + "");
                            validSKu = productSkuBean.validSKu;
                            skuList = productSkuBean.skuList;
                            skuColorValue = skuList.get(0).skuValue;
                            for (ProductSkuBean.SkuValue sKu :
                                    skuColorValue) {
                                if (skucolorMAxsize < sKu.optionValue.length()) {
                                    skucolorMAxsize = sKu.optionValue.length();
                                }

                            }
                            if (SKU_MODEL == SKU_MODEL_TWO) {
                                skuFormatValue = skuList.get(1).skuValue;
                                for (ProductSkuBean.SkuValue sKu :
                                        skuFormatValue) {
                                    if (skuformatMAxsize < sKu.optionValue.length()) {
                                        skuformatMAxsize = sKu.optionValue.length();
                                    }
                                }
                            }


                            if (validSKu != null && validSKu.size() == 1) {
                                //如果sku只有一个直接提交订单
                                skuColor = validSKu.get(0).skuColor;
                                if(SKU_MODEL == SKU_MODEL_TWO){
                                    skuFormat = validSKu.get(0).skuFormat;
                                }

                            } else if (validSKu != null && validSKu.size() > 1) {
                                if(SKU_MODEL == SKU_MODEL_TWO){
                                    skuFormat = validSKu.get(0).skuFormat;
                                }else {
                                    skuColor = validSKu.get(0).skuColor;
                                }

                            }

                            view.showSkuPopuWindow();
                        } else {
                            baseView.toastMessage(base.error_msg);
                        }


                    } else {
                        baseView.toastMessage("网络异常，数据加载失败");
                    }

                }

                @Override
                public void onStart() {
                    baseView.showProgressDialog("加载中...");
                }

                @Override
                public void onFinish() {
                    baseView.removeDialog();
                }

                @Override
                public void onFailure(int i, String s, Throwable throwable) {
                    LogUtil.Log(throwable.getMessage());
                    baseView.toastMessage("数据加载失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String forValidSKuId() {
        try {
            if (SKU_MODEL == SKU_MODEL_TWO) {
                if (StringUtils.isNotBlank(skuColor) && StringUtils.isNotBlank(skuFormat)) {
                    for (int i = 0; i < validSKu.size(); i++) {
                        ProductSkuBean.ValidSKu valid = validSKu.get(i);
                        if (skuColor.equals(valid.skuColor) && skuFormat.equals(valid.skuFormat)) {
                            skuimage = valid.skuImg;
                            return valid.id;
                        }
                    }
                }
            } else {

                if (StringUtils.isNotBlank(skuColor)) {
                    for (int i = 0; i < validSKu.size(); i++) {
                        ProductSkuBean.ValidSKu valid = validSKu.get(i);
                        if (skuColor.equals(valid.skuColor)) {
                            skuimage = valid.skuImg;
                            return valid.id;
                        }
                    }
                }

            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getNumColumns(int i) {
        int num = 4;
        if (i >= 11) {
            num = 1;
        } else if (i >= 7 && i <= 10) {
            num = 2;
        } else if (i >= 5 && i <= 6) {
            num = 3;
        } else if (i >= 3 & i <= 4) {
            num = 4;
        } else if (i <= 2) {
            num = 5;
        }


        return num;
    }

    public int forValidSKuClickable(String skuColor, String skuFormat) {
        if (StringUtils.isNotBlank(skuColor) && StringUtils.isNotBlank(skuFormat)) {
            for (int i = 0; i < validSKu.size(); i++) {
                ProductSkuBean.ValidSKu valid = validSKu.get(i);
                if (skuColor.equals(valid.skuColor) && skuFormat.equals(valid.skuFormat)) {
                    return 0;
                }
            }
            return 1;
        }

        return 0;
    }

    public void setBtn_offirm(Button btn_offirm, ImageView iv_iamge, View.OnClickListener onClickListener) {
        try {
            String skuid = forValidSKuId();
            LogUtil.Log("skuid:" + skuid);
            if (StringUtils.isBlank(skuid)) {
                btn_offirm.setBackgroundResource(R.drawable.gray_radius_5dp);
                btn_offirm.setOnClickListener(null);
            } else {
                LogUtil.Log(skuimage);
                Glide.with(context).load(skuimage).placeholder(R.mipmap.default_load).error(R.mipmap.default_load).into(iv_iamge);
                btn_offirm.setBackgroundResource(R.drawable.red_radius_5dp);
                btn_offirm.setOnClickListener(onClickListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public void onDestroy() {
        view = null;
        context = null;
        baseView = null;
    }

}
