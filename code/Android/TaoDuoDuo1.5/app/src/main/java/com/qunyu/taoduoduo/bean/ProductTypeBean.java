package com.qunyu.taoduoduo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */

public class ProductTypeBean {

    /**
     * oneId : 764
     * oneName : 111
     * oneIcon : http://pin.taozhuma.com/upfiles/productType/
     * twoLevelList : [{"threeLevelList":[{"threeId":"767","threeIcom":"http://pin.taozhuma.com/upfiles/productType/","threeName":"444"}],"twoIcom":"http://pin.taozhuma.com/upfiles/productType/","twoName":"222","twoId":"765"},{"threeLevelList":[],"twoIcom":"http://pin.taozhuma.com/upfiles/productType/","twoName":"333","twoId":"766"}]
     */
    @SerializedName("oneFlag")
    public String oneFlag;
    @SerializedName("oneId")
    public String oneId;
    @SerializedName("oneName")
    public String oneName;
    @SerializedName("oneIcon")
    public String oneIcon;
    @SerializedName("twoLevelList")
    public List<TwoLevelListBean> twoLevelList;

    public static class TwoLevelListBean {
        /**
         * threeLevelList : [{"threeId":"767","threeIcom":"http://pin.taozhuma.com/upfiles/productType/","threeName":"444"}]
         * twoIcom : http://pin.taozhuma.com/upfiles/productType/
         * twoName : 222
         * twoId : 765
         */

        @SerializedName("twoIcon")
        public String twoIcon;
        @SerializedName("twoName")
        public String twoName;
        @SerializedName("twoId")
        public String twoId;
        @SerializedName("threeLevelList")
        public List<ThreeLevelListBean> threeLevelList;

        public static class ThreeLevelListBean {
            /**
             * threeId : 767
             * threeIcom : http://pin.taozhuma.com/upfiles/productType/
             * threeName : 444
             */

            @SerializedName("threeId")
            public String threeId;
            @SerializedName("threeIcon")
            public String threeIcon;
            @SerializedName("threeName")
            public String threeName;
        }
    }
}
