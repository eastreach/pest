package com.eastreach.pest.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 *
 **/
@Entity
@Table
public class TZDLog extends RootBean {

    private Integer id;
    private String code = UUID.randomUUID().toString();     //流水号
    private Date dt = new Date();
    private String account = "";    //操作员账号
    private String url = "";    //资源访问地址
    private String state = "";      //调用结果
    private Integer errCode = 0;    //状态码
    private String request = "";    //请求内容
    private String response = "";   //返回内容

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    @Column(length = 4000)
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Column(length = 4000)
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
