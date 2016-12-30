package com.qunyu.taoduoduo.bean;

/**
 * Created by Administrator on 2016/9/26.
 */

public class DefaultAddressBean {

    /**
     * addId : 64
     * address : 内蒙古自治区包头市达尔罕茂明安联合旗Hcmjnddhjf
     * isDefault : 1
     * name : Hfnhd
     * postcode : 515280
     * province : 6
     * tel : 13648085563
     */

    private String addId;//地址ID
    private String address;//地址详情
    private int isDefault;//是否为默认地址1是0否
    private String isRemote;//是否偏远0否1是
    private String name;//收货人名字
    private String postcode;//邮编
    private int province;//省份id
    private String tel;//收货人电话

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(String isRemote) {
        this.isRemote = isRemote;
    }
}
