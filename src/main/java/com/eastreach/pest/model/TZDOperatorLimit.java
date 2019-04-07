package com.eastreach.pest.model;

import javax.persistence.*;

/**
 * 操作员属性权限管理
 **/
@Entity
@Table
public class TZDOperatorLimit extends RootBean {

    private Integer id;
    private Integer state = 1;      //状态,-1禁用,0未审核,1可用,非1不可用.

    private String account = "";        //操作员账号
    private String url = "";            //权限代码
    private Integer ifLimit = 1;        //是否有权限, 1授权, -1限权
    private Integer limitLevel = 0;    //权限等级, 0系统, 1用户
    private String memo = "";          //备注


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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIfLimit() {
        return ifLimit;
    }

    public void setIfLimit(Integer ifLimit) {
        this.ifLimit = ifLimit;
    }

    public Integer getLimitLevel() {
        return limitLevel;
    }

    public void setLimitLevel(Integer limitLevel) {
        this.limitLevel = limitLevel;
    }
}
