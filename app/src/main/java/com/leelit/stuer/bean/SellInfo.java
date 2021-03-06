package com.leelit.stuer.bean;

/**
 * Created by Leelit on 2016/3/15.
 */
public class SellInfo {
    int id;
    String name;
    String tel;
    String shortTel;
    String wechat;
    String datetime;
    String imei;
    String picAddress;
    String state;
    String uniquecode; // attention 这里的flag和carpool/date模块不同，唯一订单号，相当于前两个的uniquecode
    String status; // on // off

    @Override
    public String toString() {
        return "SellInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", shortTel='" + shortTel + '\'' +
                ", wechat='" + wechat + '\'' +
                ", datetime='" + datetime + '\'' +
                ", imei='" + imei + '\'' +
                ", picAddress='" + picAddress + '\'' +
                ", state='" + state + '\'' +
                ", uniquecode='" + uniquecode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShortTel() {
        return shortTel;
    }

    public void setShortTel(String shortTel) {
        this.shortTel = shortTel;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
