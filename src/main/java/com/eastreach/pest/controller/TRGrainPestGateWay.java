package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.*;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
@RequestMapping("/grainPest")
public class TRGrainPestGateWay extends RootGateWay {

//    /**
//     * 动态生成where语句
//     */
//    @Override
//    Specification getWhereClause() {
//        return new Specification<TRGrainPest>() {
//            @Override
//            public Predicate toPredicate(Root<TRGrainPest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicate = Lists.newArrayList();
//                if (getParam("grainCode") != null) {
//                    predicate.add(cb.equal(root.get("grainCode"), getParam("grainCode")));
//                }
//                if (getParam("pestCode") != null) {
//                    predicate.add(cb.equal(root.get("pestCode"), getParam("pestCode")));
//                }
//                if (getParam("memo") != null) {
//                    predicate.add(cb.like(root.get("memo").as(String.class), "%" + getParam("memo") + "%"));
//                }
//                Predicate[] pre = new Predicate[predicate.size()];
//                return query.where(predicate.toArray(pre)).getRestriction();
//            }
//        };
//    }

    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = getParam("grainCode");
        String pestCode = getParam("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        trGrainPest = new TRGrainPest();
        trGrainPest.setGrainCode(grainCode);
        trGrainPest.setPestCode(pestCode);
        String name = getParam("name");
        if (name != null) {
            trGrainPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo(memo);
        }
        trGrainPestDao.save(trGrainPest);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(getParam("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
        });
        for (TRGrainPest trGrainPest : trGrainPestList) {
            trGrainPest.setId(null);
            if (StringUtils.isEmpty(trGrainPest.getPestCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-pestCode");
            }
            if (StringUtils.isEmpty(trGrainPest.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-grainCode");
            }
            TRGrainPest trGrainPest1 = trGrainPestDao.findByGrainCodeAndPestCode(trGrainPest.getGrainCode(), trGrainPest.getPestCode());
            if (trGrainPest1 != null) {
                continue;
            }
            trGrainPestDao.delete(trGrainPest);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = getParam("grainCode");
        String pestCode = getParam("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trGrainPestDao.delete(trGrainPest);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(getParam("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
        });
        for (TRGrainPest trGrainPest : trGrainPestList) {
            trGrainPest.setId(null);
            if (StringUtils.isEmpty(trGrainPest.getPestCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-pestCode");
            }
            if (StringUtils.isEmpty(trGrainPest.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-grainCode");
            }
            TRGrainPest trGrainPest1 = trGrainPestDao.findByGrainCodeAndPestCode(trGrainPest.getGrainCode(), trGrainPest.getPestCode());
            if (trGrainPest1 != null) {
                trGrainPestDao.delete(trGrainPest1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = getParam("grainCode");
        String pestCode = getParam("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            trGrainPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo(memo);
        }

        trGrainPestDao.save(trGrainPest);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(getParam("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
        });
        for (TRGrainPest trGrainPest : trGrainPestList) {
            trGrainPest.setId(null);
            if (StringUtils.isEmpty(trGrainPest.getPestCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-pestCode");
            }
            if (StringUtils.isEmpty(trGrainPest.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-grainCode");
            }
            TRGrainPest trGrainPest1 = trGrainPestDao.findByGrainCodeAndPestCode(trGrainPest.getGrainCode(), trGrainPest.getPestCode());
            if (trGrainPest1 != null) {
                Utils.copy(trGrainPest, trGrainPest1);
                trGrainPestDao.save(trGrainPest1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TRGrainPest.class, Sets.<String>newHashSet("id"));
        List<TRGrainPest> trGrainPestList = trGrainPestDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TRGrainPest.class, Sets.<String>newHashSet("id"));
        Page<TRGrainPest> trGrainPestPage = trGrainPestDao.findAll(mapFilter.getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPestPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
