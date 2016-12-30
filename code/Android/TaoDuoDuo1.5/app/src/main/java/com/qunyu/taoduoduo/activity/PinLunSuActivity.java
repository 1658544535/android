package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.andbase.library.http.AbHttpUtil;
import com.andbase.library.http.listener.AbStringHttpResponseListener;
import com.andbase.library.http.model.AbResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.GuessYourLikeAdapter;
import com.qunyu.taoduoduo.adapter.PinLunSuGuessYourLikeAdapter;
import com.qunyu.taoduoduo.api.GuessYourLikeApi;
import com.qunyu.taoduoduo.base.BaseActivity;
import com.qunyu.taoduoduo.base.BaseModel;
import com.qunyu.taoduoduo.bean.GuessYourLikeBean;
import com.qunyu.taoduoduo.utils.LogUtil;
import com.qunyu.taoduoduo.utils.UserInfoUtils;
import com.qunyu.taoduoduo.widget.GridViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.List;

public class PinLunSuActivity extends BaseActivity {
    GridViewForScrollView gv_cainixihuan;
    PinLunSuGuessYourLikeAdapter guessYourLikeAdapter;
    GuessYourLikeApi guessYourLikeApi;
    public List<GuessYourLikeBean> guessYourLikeBeanList;
    String activityId;
    ImageView iv_gylike;//猜你喜欢头部

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView(R.layout.activity_pin_lun_su);
        baseSetText("评论成功");
        activityId = getIntent().getStringExtra("activityId");
        iv_gylike = (ImageView) findViewById(R.id.iv_gylike);
        gv_cainixihuan = (GridViewForScrollView) findViewById(R.id.gv_cainixihuan);
        gv_cainixihuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GuessYourLikeBean guessYourLikeBean = (GuessYourLikeBean) parent.getAdapter().getItem(position);
                Intent intent = new Intent(PinLunSuActivity.this, GoodsDetailActivity.class);
                intent.putExtra("pid", guessYourLikeBean.productId);
                intent.putExtra("activityId", guessYourLikeBean.activityId);
                startActivity(intent);
                finish();
            }

        });
        gv_cainixihuan.setFocusable(false);
        guessYourLikeApi = new GuessYourLikeApi();
        guessYourLike(activityId);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    public void guessYourLike(String activityId) {
        guessYourLikeApi.setActivityId(activityId);
        guessYourLikeApi.setUserId(UserInfoUtils.GetUid());
        LogUtil.Log(guessYourLikeApi.getUrl() + "?" + guessYourLikeApi.getParamMap().getParamString());
        AbHttpUtil.getInstance(PinLunSuActivity.this).get(guessYourLikeApi.getUrl(), guessYourLikeApi.getParamMap(), new AbStringHttpResponseListener() {
            @Override
            public void onSuccess(int i, String s) {
                {
                    LogUtil.Log(s);
                    AbResult result = new AbResult(s);
                    if (result.getResultCode() == AbResult.RESULT_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<BaseModel<List<GuessYourLikeBean>>>() {
                        }.getType();
                        BaseModel<List<GuessYourLikeBean>> base = gson.fromJson(s, type);
                        if (base.success && base.result != null) {
                            guessYourLikeBeanList = base.result;
                            iv_gylike.setVisibility(View.VISIBLE);
                            guessYourLikeAdapter = new PinLunSuGuessYourLikeAdapter(PinLunSuActivity.this, guessYourLikeBeanList);
                            gv_cainixihuan.setAdapter(guessYourLikeAdapter);

                        }
//                        else {
//                            view.toastMessage(base.error_msg);
//                        }


                    } else {
                        // view.toastMessage(result.getResultMessage());
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
                // view.toastMessage(throwable.getMessage());
            }
        });
    }
}
