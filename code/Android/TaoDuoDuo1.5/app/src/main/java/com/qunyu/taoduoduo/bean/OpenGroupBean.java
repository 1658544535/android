package com.qunyu.taoduoduo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */

public class OpenGroupBean {
    /**
     * 拼团-开团页面
     */
    public String activityId;//活动ID
    public String productSketch;//商品简介
    public String groupNum;//n人团
    public String isGroupFree;//	是否团免(0:否1:是)
    public String nowTime;//当前时间
    public String productId;//商品id
    public String sellingPrice;//原价
    public String proSellrNum;//商品销量
    public String alonePrice;//单独价
    public String productStatus;//商品状态(1上架,0下架)
    public String activityStatus;//活动状态(0-未开始1-活动中2-活动结束)
    public String activityType;//活动类型(1-普通拼团2-团免3-猜价5-0.1抽奖6-限时秒杀7-免费抽奖)
    public String productPrice;//	拼团价
    public String productName;//	商品名称
    public List<WaitGroup> waitGroupList;//同一商品(待组团)
    public List<Banner> banners;
    public  String isCollect;//	是否收藏1是0否
    public String productImage;//商品图片
    public String limitNum;//	活动限量
    public String activityETime;//	活动结束时间
    public String activitySTime;//活动开始时间
    public String startTime;//活动开始时间
    public String isSellOut;//是否售罄(1是0否)
    //public String isGroup;//是否参团(0-否1-是)
    public String isOpen;//是否开过团(0-否1-是)
    public String isWaitOpen;//	开奖状态(0-活动进行中1-待开奖2-已开奖/活动已结束3-活动未开始)
    public String isGroup;//		是否参团(0-否1-是)




    public class WaitGroup {
        public String groupRecId;//开团记录id
        public String userImage;
        public String oweNum;//	差几人团
        public String userName;
        public String beginTime;
        public String endTime;
        public String nowTime;
        public String Hour;
        public String Min;
        public String Ss;
    }

    public class Banner implements Serializable {
        public String bannerImage;
    }


}
