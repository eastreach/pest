package com.eastreach.pest.model;

import javax.persistence.*;

/**
 *
 **/
@Entity
@Table
public class TRGrainArea extends RootBean {

    private Integer id;
    private Integer state;

    private String grainCode;
    private String areaCode;
    private String memo;

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

    public String getGrainCode() {
        return grainCode;
    }

    public void setGrainCode(String grainCode) {
        this.grainCode = grainCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
