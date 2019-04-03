package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TRGrainArea;
import com.eastreach.pest.model.TRStatPest;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDPest;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/statPest")
public class TRStatPestGateWay extends RootGateWay {


    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TRStatPest>() {
            @Override
            public Predicate toPredicate(Root<TRStatPest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("areaCode") != null) {
                    predicate.add(cb.equal(root.get("areaCode"), getParam("areaCode")));
                }
                if (getParam("pestCode") != null) {
                    predicate.add(cb.equal(root.get("pestCode"), getParam("pestCode")));
                }
                if (getParam("province") != null) {
                    predicate.add(cb.equal(root.get("province"), getParam("province")));
                }
                if (getParam("city") != null) {
                    predicate.add(cb.equal(root.get("city"), getParam("city")));
                }
                if (getParam("startDt") != null) {
                    predicate.add(cb.greaterThanOrEqualTo(root.get("dt").as(String.class), getParam("startDt")));
                }
                if (getParam("endDt") != null) {
                    predicate.add(cb.lessThanOrEqualTo(root.get("dt").as(String.class), getParam("endDt")));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("dt", "areaCode", "pestCode", "pestValue", "longitude", "latitude", "temperature", "humidity"));
        TRStatPest trStatPest = new TRStatPest();
        trStatPest.setDt(DateTime.parse(getParam("dt")).toDate());
        trStatPest.setAreaCode(getParam("areaCode"));
        trStatPest.setPestCode(getParam("pestCode"));
        trStatPest.setPestValue(Double.parseDouble(getParam("pestValue")));
        trStatPest.setLongitude(Double.parseDouble(getParam("longitude")));
        trStatPest.setLatitude(Double.parseDouble(getParam("latitude")));
        trStatPest.setTemperature(Double.parseDouble(getParam("temperature")));
        trStatPest.setHumidity(Double.parseDouble(getParam("humidity")));
        if (!StringUtils.isEmpty(getParam("memo"))) {
            trStatPest.setMemo("memo");
        }
        if (!StringUtils.isEmpty(getParam("province"))) {
            trStatPest.setProvince("province");
        }
        if (!StringUtils.isEmpty(getParam("city"))) {
            trStatPest.setCity("city");
        }
        trStatPestDao.save(trStatPest);
        return CommonReturnType.create(trStatPest);
    }


    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code"));
        TRStatPest trStatPest = trStatPestDao.findByCode(getParam("code"));
        if (trStatPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        if (!StringUtils.isEmpty(getParam("dt"))) {
            trStatPest.setDt(DateTime.parse(getParam("dt")).toDate());
        }
        if (!StringUtils.isEmpty(getParam("areaCode"))) {
            trStatPest.setAreaCode(getParam("areaCode"));
        }
        if (!StringUtils.isEmpty(getParam("pestCode"))) {
            trStatPest.setPestCode(getParam("pestCode"));
        }
        if (!StringUtils.isEmpty(getParam("pestValue"))) {
            trStatPest.setPestValue(Double.parseDouble(getParam("pestValue")));
        }
        if (!StringUtils.isEmpty(getParam("longitude"))) {
            trStatPest.setLongitude(Double.parseDouble(getParam("longitude")));
        }
        if (!StringUtils.isEmpty(getParam("latitude"))) {
            trStatPest.setLatitude(Double.parseDouble(getParam("latitude")));
        }
        if (!StringUtils.isEmpty(getParam("temperature"))) {
            trStatPest.setTemperature(Double.parseDouble(getParam("temperature")));
        }
        if (!StringUtils.isEmpty(getParam("humidity"))) {
            trStatPest.setHumidity(Double.parseDouble(getParam("humidity")));
        }
        if (!StringUtils.isEmpty(getParam("memo"))) {
            trStatPest.setMemo("memo");
        }
        if (!StringUtils.isEmpty(getParam("province"))) {
            trStatPest.setProvince("province");
        }
        if (!StringUtils.isEmpty(getParam("city"))) {
            trStatPest.setCity("city");
        }
        trStatPestDao.save(trStatPest);
        return CommonReturnType.create(trStatPest);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        TRStatPest trStatPest = trStatPestDao.findByCode(getParam("code"));
        if (trStatPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trStatPestDao.delete(trStatPest);
        return CommonReturnType.create(trStatPest);
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trStatPestList"));
        List<TRStatPest> trStatPestList = JSON.parseObject(getParam("trStatPestList"), new TypeReference<ArrayList<TRStatPest>>() {
        });
        for (TRStatPest trStatPest : trStatPestList) {
            trStatPest.setId(null);
            if (StringUtils.isEmpty(trStatPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trStatPestList-code");
            }
            TRStatPest trStatPest1 = trStatPestDao.findByCode(trStatPest.getCode());
            if (trStatPest1 != null) {
                trStatPestDao.delete(trStatPest1);
            }
        }
        return CommonReturnType.create(trStatPestList);
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TRGrainArea> trGrainAreaList = trStatPestDao.findAll(getWhereClause());
        return CommonReturnType.create(trGrainAreaList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TRGrainArea> trGrainAreaPage = trStatPestDao.findAll(getWhereClause(), getPageRequest());
        return CommonReturnType.create(trGrainAreaPage);
    }

}
