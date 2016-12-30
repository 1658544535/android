package com.qunyu.taoduoduo.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/26.
 */

public class MyAddressBean implements Serializable{

    private String addId;
    private String address;
    private int isDefault;
    private String name;
    private String postcode;
    private int area;
    private int city;
    private int province;
    private String tel;
    private  String isRemote;//	是否偏远0否1是

    public String getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(String isRemote) {
        this.isRemote = isRemote;
    }

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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }
}
