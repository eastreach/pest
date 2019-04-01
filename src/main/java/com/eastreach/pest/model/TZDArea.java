package com.eastreach.pest.model;

import javax.persistence.*;

/**
 *
 **/
@Entity
@Table
public class TZDArea extends RootBean {

    private Integer id;
    private Integer state = 1;  //状态码, 1可用,非1禁用.
    private String code = "";   //代码, 唯一标识
    private String name = "";   //区域名称, 青藏高原储量区

    private String memo = "";   //备注
    private String pic = "";    //图片
    private String pics = "";   //多图.
    private String areaDesc = "";       //主要地区, 西藏...
    private String featureDesc = "";    //生态特点.
    private String grainDesc = "";      //主要粮食作物

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }

    public String getFeatureDesc() {
        return featureDesc;
    }

    public void setFeatureDesc(String featureDesc) {
        this.featureDesc = featureDesc;
    }

    public String getGrainDesc() {
        return grainDesc;
    }

    public void setGrainDesc(String grainDesc) {
        this.grainDesc = grainDesc;
    }
}
