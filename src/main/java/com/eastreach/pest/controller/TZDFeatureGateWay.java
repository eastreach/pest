package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatPest;
import com.eastreach.pest.model.TZDFeature;
import com.eastreach.pest.model.TZDOperator;
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
@RequestMapping("/feature")
public class TZDFeatureGateWay extends RootGateWay {

//    /**
//     * 动态生成where语句
//     */
//    @Override
//    Specification getWhereClause() {
//        return new Specification<TZDFeature>() {
//            @Override
//            public Predicate toPredicate(Root<TZDFeature> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicate = Lists.newArrayList();
//                if (getParam("code") != null) {
//                    predicate.add(cb.equal(root.get("code"), getParam("code")));
//                }
//                if (getParam("nameLike") != null) {
//                    predicate.add(cb.like(root.get("name").as(String.class), "%" + getParam("nameLike") + "%"));
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
        checkParam(Lists.<String>newArrayList("code", "name"));
        String code = getParam("code");
        String name = getParam("name");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdFeature = new TZDFeature();
        tzdFeature.setCode(code);
        tzdFeature.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tzdFeature.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdFeature.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdFeature.setPics(pics);
        }
        tzdFeatureDao.save(tzdFeature);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeature);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdFeatureList"));
        List<TZDFeature> tzdFeatureList = JSON.parseObject(getParam("tzdFeatureList"), new TypeReference<ArrayList<TZDFeature>>() {
        });
        for (TZDFeature tzdFeature : tzdFeatureList) {
            tzdFeature.setId(null);
            if (StringUtils.isEmpty(tzdFeature.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdFeatureList-code");
            }
            if (StringUtils.isEmpty(tzdFeature.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdFeatureList-name");
            }
            TZDFeature tzdFeature1 = tzdFeatureDao.find(tzdFeature.getCode());
            if (tzdFeature1 != null) {
                continue;
            }
            tzdFeatureDao.save(tzdFeature);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeatureList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdFeatureDao.delete(tzdFeature);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeature);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdFeatureList"));
        List<TZDFeature> tzdFeatureList = JSON.parseObject(getParam("tzdFeatureList"), new TypeReference<ArrayList<TZDFeature>>() {
        });
        for (TZDFeature tzdFeature : tzdFeatureList) {
            tzdFeature.setId(null);
            if (StringUtils.isEmpty(tzdFeature.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdFeatureList-code");
            }
            if (StringUtils.isEmpty(tzdFeature.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdFeatureList-name");
            }
            TZDFeature tzdFeature1 = tzdFeatureDao.find(tzdFeature.getCode());
            if (tzdFeature1 != null) {
                tzdFeatureDao.delete(tzdFeature1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeatureList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tzdFeature.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdFeature.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdFeature.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdFeature.setPics(pics);
        }
        tzdFeatureDao.save(tzdFeature);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeature);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdFeatureList"));
        List<TZDFeature> tzdFeatureList = JSON.parseObject(getParam("tzdFeatureList"), new TypeReference<ArrayList<TZDFeature>>() {
        });
        for (TZDFeature tzdFeature : tzdFeatureList) {
            tzdFeature.setId(null);
            if (StringUtils.isEmpty(tzdFeature.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdFeatureList-code");
            }
            if (StringUtils.isEmpty(tzdFeature.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdFeatureList-name");
            }
            TZDFeature tzdFeature1 = tzdFeatureDao.find(tzdFeature.getCode());
            if (tzdFeature1 != null) {
                Utils.copy(tzdFeature, tzdFeature1);
                tzdFeatureDao.save(tzdFeature1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeatureList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDFeature.class, Sets.<String>newHashSet("id"));
        List<TZDFeature> tzdFeatureList = tzdFeatureDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeatureList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDFeature.class, Sets.<String>newHashSet("id"));
        Page<TZDFeature> tzdFeaturePage = tzdFeatureDao.findAll(mapFilter.getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeaturePage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
