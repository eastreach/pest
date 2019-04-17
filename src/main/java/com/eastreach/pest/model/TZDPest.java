package com.eastreach.pest.model;

import javax.persistence.*;

/**
 *
 **/
@Entity
@Table
public class TZDPest extends RootBean {

    private Integer id;
    private Integer state = 1;
    private String code = "";       //害虫代码
    private String name = "";       //害虫名称
    private String memo = "";
    private String pic = "";
    private String pics = "";
    private String areas = "";      //害虫分布区域,逗号分割
    private String grains = "";     //害虫关联作物

    //属性
    private String danger = "";     //危险等级
    private String family = "";     //科属
    private String category = "";   //类别
    private String feature = "";    //特征
    private String habit = "";      //生活习性
    private String damage = "";     //危害
    private String distribution = "";   //分布
    private String prevention = "";     //防治方法


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

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getGrains() {
        return grains;
    }

    public void setGrains(String grains) {
        this.grains = grains;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(length = 3000)
    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Column(length = 3000)
    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    @Column(length = 3000)
    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    @Column(length = 3000)
    public String getPrevention() {
        return prevention;
    }

    public void setPrevention(String prevention) {
        this.prevention = prevention;
    }

    @Column(length = 3000)
    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }
}
