package com.eastreach.pest.model;

import javax.persistence.*;

/**
 * 操作员管理
 * 权限模型
 * 自主访问控制: DAC-discretionary access control.
 * 强制访问控制: MAC-mandatory access control.
 * 基于角色访问控制: RBAC-role based access control.
 * 基于属性访问控制: ABAC- attribute based access control.
 **/
@Entity
@Table
public class TZDOperator extends RootBean{

    private Integer id;
    private Integer state = 1;      //状态,-1禁用,0未审核,1可用,非1不可用.
    private Integer ifRoot = 0;     //是否系统管理员,系统管理员有所有权限.
    private String account = "";    //登录账号
    private String password = "";   //登录密码
    private String role = "";       //操作员角色,权限模型采用简化单个操作员设置,

    private String name = "";           //姓名.
    private String telephone = "";      //电话
    private String place="";            //住址
    private String province="";         //省份
    private String city="";             //城市


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

    public Integer getIfRoot() {
        return ifRoot;
    }

    public void setIfRoot(Integer ifRoot) {
        this.ifRoot = ifRoot;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
