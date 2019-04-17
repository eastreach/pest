package com.eastreach.pest.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 粮仓
 **/
@Entity
@Table
public class TZDDepot extends RootBean {

    private Integer id;
    private Integer state = 1;  //状态码, 1可用,非1禁用.


    private String code = "";   //代码, 唯一标识
    private String name = "";   //区域名称, 青藏高原储量区

    private String memo = "";   //备注
    private String pic = "";    //图片
    private String pics = "";   //多图.

    private String kind = "";   //类型
    private String volumnName = "";     //容量名字
    private String grainName = "";      //作物名称
    private String storeKind = "";      //存储形式
    private Double mountValue = 0.0;     //当前储量
    private String grainArea = "";      //粮食产地
    private Date inDt;              //入库时间
    private Date outDt;             //出口时间


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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVolumnName() {
        return volumnName;
    }

    public void setVolumnName(String volumnName) {
        this.volumnName = volumnName;
    }

    public String getGrainName() {
        return grainName;
    }

    public void setGrainName(String grainName) {
        this.grainName = grainName;
    }

    public String getStoreKind() {
        return storeKind;
    }

    public void setStoreKind(String storeKind) {
        this.storeKind = storeKind;
    }

    public Double getMountValue() {
        return mountValue;
    }

    public void setMountValue(Double mountValue) {
        this.mountValue = mountValue;
    }

    public String getGrainArea() {
        return grainArea;
    }

    public void setGrainArea(String grainArea) {
        this.grainArea = grainArea;
    }

    public Date getInDt() {
        return inDt;
    }

    public void setInDt(Date inDt) {
        this.inDt = inDt;
    }

    public Date getOutDt() {
        return outDt;
    }

    public void setOutDt(Date outDt) {
        this.outDt = outDt;
    }
}
