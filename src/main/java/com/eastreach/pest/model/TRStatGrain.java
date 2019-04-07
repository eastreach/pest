package com.eastreach.pest.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * 农作物产量统计
 **/
@Entity
@Table
public class TRStatGrain extends RootBean {

    private Integer id;
    private Integer state = 1;
    private String code = UUID.randomUUID().toString();           //发布信息唯一流水号,后台系统生成.

    private Date dt;                //统计时间
    private Date fromDt;            //统计开始时间
    private Date toDt;              //统计结束时间

    private Integer year;             //年份
    private Integer month;            //月份
    private String areaCode = "";     //区域代码
    private String grainCode = "";    //农作物代码
    private Double grainValue;      //农作物产量

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

    public String getGrainCode() {
        return grainCode;
    }

    public void setGrainCode(String grainCode) {
        this.grainCode = grainCode;
    }

    public Double getGrainValue() {
        return grainValue;
    }

    public void setGrainValue(Double grainValue) {
        this.grainValue = grainValue;
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
}
