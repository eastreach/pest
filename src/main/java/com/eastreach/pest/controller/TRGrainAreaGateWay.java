package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.*;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
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
@RequestMapping("/grainArea")
public class TRGrainAreaGateWay extends RootGateWay {

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TRGrainArea>() {
            @Override
            public Predicate toPredicate(Root<TRGrainArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("grainCode") != null) {
                    predicate.add(cb.equal(root.get("grainCode"), getParam("grainCode")));
                }
                if (getParam("areaCode") != null) {
                    predicate.add(cb.equal(root.get("areaCode"), getParam("areaCode")));
                }
                if (getParam("memo") != null) {
                    predicate.add(cb.like(root.get("memo").as(String.class), "%" + getParam("memo") + "%"));
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
        checkParam(Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = getParam("grainCode");
        String areaCode = getParam("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        trGrainArea = new TRGrainArea();
        trGrainArea.setGrainCode(grainCode);
        trGrainArea.setAreaCode(areaCode);
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo(memo);
        }
        trGrainAreaDao.save(trGrainArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainArea);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainAreaList"));
        List<TRGrainArea> trGrainAreaList = JSON.parseObject(getParam("trGrainAreaList"), new TypeReference<ArrayList<TRGrainArea>>() {
        });
        for (TRGrainArea trGrainArea : trGrainAreaList) {
            trGrainArea.setId(null);
            if (StringUtils.isEmpty(trGrainArea.getAreaCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-areaCode");
            }
            if (StringUtils.isEmpty(trGrainArea.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-grainCode");
            }
            TRGrainArea trGrainArea1 = trGrainAreaDao.findByGrainCodeAndAreaCode(trGrainArea.getGrainCode(), trGrainArea.getAreaCode());
            if (trGrainArea1 != null) {
                continue;
            }
            trGrainAreaDao.save(trGrainArea);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = getParam("grainCode");
        String areaCode = getParam("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trGrainAreaDao.delete(trGrainArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainArea);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainAreaList"));
        List<TRGrainArea> trGrainAreaList = JSON.parseObject(getParam("trGrainAreaList"), new TypeReference<ArrayList<TRGrainArea>>() {
        });
        for (TRGrainArea trGrainArea : trGrainAreaList) {
            trGrainArea.setId(null);
            if (StringUtils.isEmpty(trGrainArea.getAreaCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-areaCode");
            }
            if (StringUtils.isEmpty(trGrainArea.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-grainCode");
            }
            TRGrainArea trGrainArea1 = trGrainAreaDao.findByGrainCodeAndAreaCode(trGrainArea.getGrainCode(), trGrainArea.getAreaCode());
            if (trGrainArea1 != null) {
                trGrainAreaDao.delete(trGrainArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = getParam("grainCode");
        String areaCode = getParam("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String state = getParam("state");
        if (state != null) {
            trGrainArea.setState(Integer.parseInt(state));
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo(memo);
        }
        trGrainAreaDao.save(trGrainArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainArea);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainAreaList"));
        List<TRGrainArea> trGrainAreaList = JSON.parseObject(getParam("trGrainAreaList"), new TypeReference<ArrayList<TRGrainArea>>() {
        });
        for (TRGrainArea trGrainArea : trGrainAreaList) {
            trGrainArea.setId(null);
            if (StringUtils.isEmpty(trGrainArea.getAreaCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-areaCode");
            }
            if (StringUtils.isEmpty(trGrainArea.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-grainCode");
            }
            TRGrainArea trGrainArea1 = trGrainAreaDao.findByGrainCodeAndAreaCode(trGrainArea.getGrainCode(), trGrainArea.getAreaCode());
            if (trGrainArea1 != null) {
                Utils.copy(trGrainArea, trGrainArea1);
                trGrainAreaDao.save(trGrainArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TRGrainArea> trGrainAreaList = trGrainAreaDao.findAll(getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TRGrainArea> trGrainAreaPage = trGrainAreaDao.findAll(getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
