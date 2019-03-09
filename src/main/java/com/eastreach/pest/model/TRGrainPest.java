package com.eastreach.pest.model;

import javax.persistence.*;

/**
 *
 **/
@Entity
@Table
public class TRGrainPest extends RootBean {

    private Integer id;
    private Integer state;
    private String grainCode;
    private String pestCode;

    private String name;
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

    public String getPestCode() {
        return pestCode;
    }

    public void setPestCode(String pestCode) {
        this.pestCode = pestCode;
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
}
