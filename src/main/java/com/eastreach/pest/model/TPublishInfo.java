package com.eastreach.pest.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 *
 **/
@Entity
@Table
public class TPublishInfo extends RootBean {

    private Integer id;
    private Integer state = 1;          //状态

    private String code = UUID.randomUUID().toString();           //发布信息唯一流水号,后台系统生成.
    private String name = "";           //发布信息名称title.
    private Date createDt = new Date();  //发布时间
    private String createOper = "";      //发布操作员工号
    private String content = "";         //发布内容
    private String memo = "";            //备注

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

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getCreateOper() {
        return createOper;
    }

    public void setCreateOper(String createOper) {
        this.createOper = createOper;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
