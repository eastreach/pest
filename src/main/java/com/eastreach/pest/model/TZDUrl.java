package com.eastreach.pest.model;

import javax.persistence.*;

/**
 * 微服务管理
 **/
@Entity
@Table
public class TZDUrl extends RootBean {

    private Integer id;
    private Integer state = 1;      //状态,-1禁用,0未审核,1可用,非1不可用.

    private String url = "";    //资源地址
    private String memo = "";       //备注
    private Integer ifRoot = 0;     //是否需要系统管理员权限, 0不需要, 1需要只有管理员可以操作
    private Integer limitType = 0;    //权限模式, 0黑名单限权, 1白名单授权
    private Integer logLevel = 0;   //日志模式, 0不记录日志, 1记录日志

    public TZDUrl() {
    }

    public TZDUrl(String url, String memo) {
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public Integer getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }

    public Integer getIfRoot() {
        return ifRoot;
    }

    public void setIfRoot(Integer ifRoot) {
        this.ifRoot = ifRoot;
    }
}
