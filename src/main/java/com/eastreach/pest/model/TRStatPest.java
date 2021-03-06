package com.eastreach.pest.model;

import com.eastreach.pest.util.Utils;

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
    private String code = Utils.uuid();           //发布信息唯一流水号,后台系统生成.

    private Date dt;                //统计时间
//    private Date fromDt;            //统计开始时间
//    private Date toDt;              //统计结束时间
//
    private Integer year;             //年份
    private Integer month;            //月份
    private String areaCode = "";     //区域代码
    private String grainCode = "";     //作物代码
    private String pestCode = "";     //害虫代码
    private Double pestValue;       //害虫数量
    private Double longitude;            //经度
    private Double latitude;             //纬度
    private Double temperature;         //温度
    private Double humidity;            //湿度
    private String memo;
    private String province;           //省份
    private String city;                //城市
    private String pic;
    private String pics;
    private String role;            //角色
    private String depotCode;       //仓库代码


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

//    @Column(name = "fromDt")
//    @Temporal(TemporalType.TIMESTAMP)
//    public Date getFromDt() {
//        return fromDt;
//    }
//
//    public void setFromDt(Date fromDt) {
//        this.fromDt = fromDt;
//    }

//    public Date getToDt() {
//        return toDt;
//    }
//
//    public void setToDt(Date toDt) {
//        this.toDt = toDt;
//    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getGrainCode() {
        return grainCode;
    }

    public void setGrainCode(String grainCode) {
        this.grainCode = grainCode;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepotCode() {
        return depotCode;
    }

    public void setDepotCode(String depotCode) {
        this.depotCode = depotCode;
    }
}
