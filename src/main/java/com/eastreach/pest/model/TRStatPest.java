package com.eastreach.pest.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * 害虫数量统计
 **/
@Entity
@Table
public class TRStatPest extends RootBean {

    private Integer id;
    private Integer state = 1;
    private String code = UUID.randomUUID().toString();           //发布信息唯一流水号,后台系统生成.

    private Date dt;                //统计时间
    private Date fromDt;            //统计开始时间
    private Date toDt;              //统计结束时间

    private String areaCode = "";     //区域代码
    private String pestCode = "";     //害虫代码
    private Double pestValue;       //害虫数量
    private Double longitude;            //经度
    private Double latitude;             //纬度
    private Double temperature;         //温度
    private Double humidity;            //湿度
    private String memo;
    private String province;
    private String city;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Date getFromDt() {
        return fromDt;
    }

    public void setFromDt(Date fromDt) {
        this.fromDt = fromDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPestCode() {
        return pestCode;
    }

    public void setPestCode(String pestCode) {
        this.pestCode = pestCode;
    }

    public Double getPestValue() {
        return pestValue;
    }

    public void setPestValue(Double pestValue) {
        this.pestValue = pestValue;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
