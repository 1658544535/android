package com.qunyu.taoduoduo.bean;

import java.util.List;

/**
 * 拼团-参团页面
 */

public class GroupDetailBean {

    public String recordId;//团记录id
    public String status;//拼团状态(0-拼团中;1-成功;2-失败)
    public String productImage;//商品图片
    public String beginTime;//团开始时间
    public String groupNum;//成团人数

    public String groupPrice;//拼团价
    public String endTime;//团结束时间
    public String userIsHead;//用户是否团长(0否;1是)
    public String nowTime;//当前时间
    public String productId;//商品id
    public String activityId;//	团活动ID
    public String alonePrice;//原价
    public String productStatus;//商品状态(1上架,0下架)
    public String joinNum;//参团人数
    public String isGroup;//是否参团(0-否1-是)
    public String poorNum;//参团还差人数
    public String productName;//商品名称
    public String activityType;//活动类型：1-普通2-团免3-猜价5-0.1抽奖6-限时秒杀7-免费抽奖
    public String isSellOut;//是否售罄(1是0否)
    public String isStart;//是否进行中(1是0否)
    public String isOpen;//是否开团(1是0否)


    public List<GroupUser> groupUserList;//参团的人列表
    public List<Bannar> banners;//商品轮播图数组

    public class Bannar {
        public String bannerImage;//轮播图片
    }


    public class GroupUser {
        public String joinTime;//参加时间
        public String isHead;//是否团长(0否;1是)
        public String userImage;//用户头像
        public String userName;//用户名称
    }


}
