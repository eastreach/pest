package com.eastreach.pest.model;

import javax.persistence.*;

/**
 * 系统运行参数设置
 **/
@Entity
@Table
public class TZDParam extends RootBean {

    private Integer id;
    private Integer state = 1;      //状态,-1禁用,0未审核,1可用,非1不可用.

    private String code = "";       //参数代码
    private String value = "";       //参数值
    private String memo = "";       //备注说明

    public TZDParam() {
    }

    public TZDParam(String code, String value, String memo) {
        this.code = code;
        this.value = value;
        this.memo = memo;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
