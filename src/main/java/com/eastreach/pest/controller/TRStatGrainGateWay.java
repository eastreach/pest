package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatGrain;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.model.TZDGrain;
import com.eastreach.pest.model.TZDOperator;
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
@RequestMapping("/statGrain")
public class TRStatGrainGateWay extends RootGateWay {

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TRStatGrain>() {
            @Override
            public Predicate toPredicate(Root<TRStatGrain> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("areaCode") != null) {
                    predicate.add(cb.equal(root.get("areaCode"), getParam("areaCode")));
                }
                if (getParam("grainCode") != null) {
                    predicate.add(cb.equal(root.get("grainCode"), getParam("grainCode")));
                }
                if (getParam("year") != null) {
                    predicate.add(cb.equal(root.get("year"), getParam("year")));
                }
                if (getParam("month") != null) {
                    predicate.add(cb.equal(root.get("month"), getParam("month")));
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
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("year", "month", "areaCode", "grainCode", "grainValue"));
        TRStatGrain trStatGrain = new TRStatGrain();
        trStatGrain.setYear(Integer.parseInt(getParam("year")));
        trStatGrain.setMonth(Integer.parseInt(getParam("month")));
        trStatGrain.setDt(DateTime.now().withYear(trStatGrain.getYear()).withMonthOfYear(trStatGrain.getMonth()).withDayOfMonth(1).withTimeAtStartOfDay().toDate());
        TZDArea tzdArea = tzdAreaDao.findByCode(getParam("areaCode"));
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "区域不存在-" + getParam("areaCode"));
        } else {
            trStatGrain.setAreaCode(tzdArea.getCode());
        }
        TZDGrain tzdGrain = tzdGrainDao.find(getParam("grainCode"));
        if (tzdGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "作物不存在-" + getParam("grainCode"));
        } else {
            trStatGrain.setGrainCode(tzdGrain.getCode());
        }
        trStatGrain.setGrainValue(Double.parseDouble(getParam("grainValue")));
        trStatGrainDao.save(trStatGrain);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrain);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code"));
        TRStatGrain trStatGrain = trStatGrainDao.findByCode(getParam("code"));
        if (trStatGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        if (!StringUtils.isEmpty(getParam("areaCode"))) {
            trStatGrain.setAreaCode(getParam("areaCode"));
        }
        if (!StringUtils.isEmpty(getParam("grainCode"))) {
            trStatGrain.setGrainCode(getParam("grainCode"));
        }
        if (!StringUtils.isEmpty(getParam("grainValue"))) {
            trStatGrain.setGrainValue(Double.parseDouble(getParam("grainValue")));
        }
        trStatGrainDao.save(trStatGrain);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrain);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        TRStatGrain trStatGrain = trStatGrainDao.findByCode(getParam("code"));
        if (trStatGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trStatGrainDao.delete(trStatGrain);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrain);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trStatGrainList"));
        List<TRStatGrain> trStatGrainList = JSON.parseObject(getParam("trStatGrainList"), new TypeReference<ArrayList<TRStatGrain>>() {
        });
        for (TRStatGrain trStatGrain : trStatGrainList) {
            trStatGrain.setId(null);
            if (StringUtils.isEmpty(trStatGrain.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trStatGrainList-code");
            }
            TRStatGrain trStatGrain1 = trStatGrainDao.findByCode(trStatGrain.getCode());
            if (trStatGrain1 != null) {
                trStatGrainDao.delete(trStatGrain1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TRStatGrain> trStatGrainList = trStatGrainDao.findAll(getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TRStatGrain> trStatGrainPage = trStatGrainDao.findAll(getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
