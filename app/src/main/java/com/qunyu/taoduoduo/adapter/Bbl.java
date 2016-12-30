//package com.qunyu.taoduoduo.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.ruiyu.taozhuma.R;
//import com.ruiyu.taozhuma.StringUtils;
//import com.ruiyu.taozhuma.base.GlideCircleTransform;
//import com.ruiyu.taozhuma.utils.LogUtil;
//import com.ruiyu.taozhuma.v3.activity.MainTabActivity;
//import com.ruiyu.taozhuma.v3.bean.TalentSiftAbilityNoteBean;
//import com.ruiyu.taozhuma.widget.PercentLinearLayout;
//
//import java.math.BigDecimal;
//import java.math.MathContext;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * Created by Administrator on 2016/11/16.
// */
//
//public class Bbl extends RecyclerView.Adapter<MyViewHolder1> {
//    public List<TalentSiftAbilityNoteBean> list;
//    Context c;
//
//    public Bbl(Context mc, List<TalentSiftAbilityNoteBean> mDatas) {
//        this.list = mDatas;
//        this.c = mc;
//
//    }
//
//    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
//
//    @Override
//    public MyViewHolder1 onCreateViewHolder(ViewGroup viewGroup, int a) {
//        View view = null;
//        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_t_home_item, viewGroup, false);
//        return new MyViewHolder1(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder1 viewHolder, int position) {
////
//        final TalentSiftAbilityNoteBean info = this.list.get(position);
//        try {
//            ViewGroup.LayoutParams linearParams = viewHolder.sdv_banner.getLayoutParams();
//            BigDecimal beichushu = new BigDecimal(
//                    Double.parseDouble(info.bannerWidth));
//            BigDecimal chushu = new BigDecimal(
//                    Double.parseDouble(info.bannerHeight));
//            BigDecimal result = beichushu.divide(chushu, new MathContext(4));
//            BigDecimal width = new BigDecimal(
//                    Double.parseDouble(MainTabActivity.width + ""));
//            BigDecimal cs = new BigDecimal(Double.parseDouble("2.037735849056604"));
//            BigDecimal kkk = width.divide(cs, new MathContext(4));// 图片宽度
//            BigDecimal ggg = kkk.divide(result, new MathContext(4));// 图片高度
//            linearParams.height = ggg.intValue();// 图片高度
////                    linearParams.width = MainTabActivity.width / 2;
//            viewHolder.sdv_banner.setLayoutParams(linearParams);
//            viewHolder.tv_title.setText(info.postTitle);
//            viewHolder.tv_collectNum.setText(info.collectNum);
//            viewHolder.tv_author.setText(info.authorName);
//            if (StringUtils.isNotBlank(info.content)) {
//                viewHolder.tv_context.setText(info.content);
//                viewHolder.tv_context.setVisibility(View.VISIBLE);
//            } else {
//                viewHolder.tv_context.setVisibility(View.GONE);
//            }
//            String newStr = info.banner.substring(info.banner.length() - 3,
//                    info.banner.length());
//            if (newStr.equals("gif")) {
//                Glide.with(c).load(info.banner).asGif().fitCenter()
//                        .placeholder(R.drawable.loding_defult)
//                        .into(viewHolder.sdv_banner);
//            } else {
//                Glide.with(c).load(info.banner).asBitmap().thumbnail(0.01f)
//                        .override(kkk.intValue(), ggg.intValue()).fitCenter()
//                        .placeholder(R.drawable.loding_defult)
//                        .into(viewHolder.sdv_banner);
//            }
//            Glide.with(c).load(info.authorLogo)
//                    .transform(new GlideCircleTransform(c))
//                    .placeholder(R.drawable.new_t3_pbl)
//                    .into(viewHolder.sdv_userlogo);
//            int tag = Integer.parseInt(info.postCollect);
//            if (tag == 1) {
//                viewHolder.iv_isCollect
//                        .setImageResource(R.drawable.new_t3_sc_on);
//            } else {
//                viewHolder.iv_isCollect
//                        .setImageResource(R.drawable.new_t3_sc_off);
//            }
////            viewHolder.ptrf_rl.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Intent intent2 = new Intent(c, BaoDianActivity.class);
////                    intent2.putExtra("id", info.postId);
////                    intent2.putExtra("authorName", info.authorName);
////                    intent2.putExtra("authorLogo", info.authorLogo);
////                    intent2.putExtra("authorSex", info.authorSex);
////                    intent2.putExtra("authorFollow", info.authorFollow);
////                    intent2.putExtra("authorFansNum", info.authorSex);
////                    intent2.putExtra("authorCollectNum", info.authorFollow);
////                    intent2.putExtra("authorId", info.authorId);
////                    intent2.putExtra("postCollect", info.postCollect);
////                    intent2.putExtra("postTitle", info.postTitle);
////                    intent2.putExtra("userId", UserInfoUtils.GetUid());
////                    c.startActivity(intent2);
////                }
////            });
//        } catch (Exception e) {
//            // TODO: handle exception
//            LogUtil.Log(e + "999");
//        }
////        int viewHeight = map.get(position);
////        if (viewHeight != 0) {
////            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.ptrf_rl.getLayoutParams();
////            layoutParams.height = viewHeight;
////            viewHolder.ptrf_rl.setLayoutParams(layoutParams);
////        } else {
////            //网络请求获取到图片的Drawable或者bitmap,得到图片宽高比例，并得到View高度viewHeight
////            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.ptrf_rl.getLayoutParams();
////            viewHeight = layoutParams.height;
////            viewHolder.ptrf_rl.setLayoutParams(layoutParams);
////            map.put(position, viewHeight);
////        }
//        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.ptrf_rl.getLayoutParams();
//        if (mHeights.size() <= position) {
//            mHeights.add((int) layoutParams.height);
//            mWeights.add((int) layoutParams.width);
//        }
//        layoutParams.height = mHeights.get(position);
//        layoutParams.width = mWeights.get(position);
//        viewHolder.ptrf_rl.setLayoutParams(layoutParams);
//
//    }
//
//    private List<Integer> mHeights = new ArrayList<Integer>();
//    private List<Integer> mWeights = new ArrayList<Integer>();
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//}
//
//class MyViewHolder1 extends RecyclerView.ViewHolder {
//    ImageView iv_isCollect;
//    PercentLinearLayout ptrf_rl;
//    TextView tv_title, tv_collectNum, tv_author, tv_context;
//    ImageView sdv_banner, sdv_userlogo;
//    PercentLinearLayout pl_001;
//
//    public MyViewHolder1(View view) {
//        super(view);
//        ptrf_rl = (PercentLinearLayout) view
//                .findViewById(R.id.ptrf_rl);
//        pl_001 = (PercentLinearLayout) view
//                .findViewById(R.id.pl_001);
//        iv_isCollect = (ImageView) view
//                .findViewById(R.id.iv_isCollect);
//        tv_title = (TextView) view.findViewById(R.id.tv_title);
//        tv_collectNum = (TextView) view
//                .findViewById(R.id.tv_collectNum);
//        tv_author = (TextView) view.findViewById(R.id.tv_author);
//        tv_context = (TextView) view
//                .findViewById(R.id.tv_context);
//        sdv_banner = (ImageView) view
//                .findViewById(R.id.sdv_banner);
//        sdv_userlogo = (ImageView) view
//                .findViewById(R.id.sdv_userlogo);
//    }
//}