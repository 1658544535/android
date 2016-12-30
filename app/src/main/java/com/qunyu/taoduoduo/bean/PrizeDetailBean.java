package com.qunyu.taoduoduo.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/28.
 * 中奖信息详情
 */

public class PrizeDetailBean {
    private String activityId;//活动id
    private String activityType;//活动类型：5-0.1抽奖
    private ArrayList<prizelists> prizelist;//中奖列表
    private String productId;//商品ID
    private String productImage;//商品图片
    private String productName;//商品名称
    private String status;//中奖状态：0-未开奖1-已开奖2-拼团失败
    private String productPrice;//商品价格


    public class prizelists {
        private String isHead;//是否团长0-否1-是
        private String loginname;//帐号
        private String name;//昵称
        private String orderNo;//订单号
        private String userlogo;//头像


        private ArrayList<groupLists> groupList;


        public String getIsHead() {
            return isHead;
        }

        public void setIsHead(String isHead) {
            this.isHead = isHead;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getUserlogo() {
            return userlogo;
        }

        public void setUserlogo(String userlogo) {
            this.userlogo = userlogo;
        }

        public ArrayList<groupLists> getGroupList() {
            return groupList;
        }

        public void setGroupList(ArrayList<groupLists> groupList) {
            this.groupList = groupList;
        }

        public class groupLists {
            private String isHead;//是否团长0-否1-是
            private String loginname;//帐号
            private String name;//昵称
            private String orderNo;//订单号
            private String userlogo;//头像
            private String attendTime;//成团时间

            public String getIsHead() {
                return isHead;
            }

            public void setIsHead(String isHead) {
                this.isHead = isHead;
            }

            public String getLoginname() {
                return loginname;
            }

            public void setLoginname(String loginname) {
                this.loginname = loginname;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public String getUserlogo() {
                return userlogo;
            }

            public void setUserlogo(String userlogo) {
                this.userlogo = userlogo;
            }

            public String getAttendTime() {
                return attendTime;
            }

            public void setAttendTime(String attendTime) {
                this.attendTime = attendTime;
            }
        }
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public ArrayList<prizelists> getPrizelist() {
        return prizelist;
    }

    public void setPrizelist(ArrayList<prizelists> prizelist) {
        this.prizelist = prizelist;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
