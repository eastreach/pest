package com.eastreach.pest.model;

import javax.persistence.*;

/**
 *
 **/
@Entity
@Table
public class TZDFeature extends RootBean {

    private Integer id;
    private Integer state;
    private String code;
    private String name;
    private String mem;


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

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }
}
