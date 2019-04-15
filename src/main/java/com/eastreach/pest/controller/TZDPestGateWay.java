package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatPest;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDPest;
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
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/pest")
public class TZDPestGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code", "name"));
        String code = getParam("code");
        String name = getParam("name");
        TZDPest tzdPest = tzdPestDao.find(code);
        if (tzdPest != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdPest = new TZDPest();
        setDomainProperty(tzdPest,Sets.<String>newHashSet("id","state"));
        tzdPestDao.save(tzdPest);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdPestList"));
        List<TZDPest> tzdPestList = JSON.parseObject(getParam("tzdPestList"), new TypeReference<ArrayList<TZDPest>>() {
        });
        for (TZDPest tzdPest : tzdPestList) {
            tzdPest.setId(null);
            if (StringUtils.isEmpty(tzdPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-code");
            }
            if (StringUtils.isEmpty(tzdPest.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-name");
            }
            TZDPest tzdPest1 = tzdPestDao.find(tzdPest.getCode());
            if (tzdPest1 != null) {
                continue;
            }
            tzdPestDao.save(tzdPest);
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
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
        TZDPest tzdPest = tzdPestDao.find(code);
        if (tzdPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdPestDao.delete(tzdPest);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdPestList"));
        List<TZDPest> tzdPestList = JSON.parseObject(getParam("tzdPestList"), new TypeReference<ArrayList<TZDPest>>() {
        });
        for (TZDPest tzdPest : tzdPestList) {
            tzdPest.setId(null);
            if (StringUtils.isEmpty(tzdPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-code");
            }
            if (StringUtils.isEmpty(tzdPest.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-name");
            }
            TZDPest tzdPest1 = tzdPestDao.find(tzdPest.getCode());
            if (tzdPest1 != null) {
                tzdPestDao.delete(tzdPest1);
                continue;
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDPest tzdPest = tzdPestDao.find(code);
        if (tzdPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        setDomainProperty(tzdPest,Sets.<String>newHashSet("id","state"));
        tzdPestDao.save(tzdPest);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdPestList"));
        List<TZDPest> tzdPestList = JSON.parseObject(getParam("tzdPestList"), new TypeReference<ArrayList<TZDPest>>() {
        });
        for (TZDPest tzdPest : tzdPestList) {
            tzdPest.setId(null);
            if (StringUtils.isEmpty(tzdPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-code");
            }
            if (StringUtils.isEmpty(tzdPest.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-name");
            }
            TZDPest tzdPest1 = tzdPestDao.find(tzdPest.getCode());
            if (tzdPest1 != null) {
                Utils.copy(tzdPest, tzdPest1);
                tzdPestDao.save(tzdPest1);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDPest.class, Sets.<String>newHashSet("id"));
        List<TZDPest> tzdPestList = tzdPestDao.findAll(mapFilter.getWhereClause());

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDPest.class, Sets.<String>newHashSet("id"));
        Page<TZDPest> tzdPestPage = tzdPestDao.findAll(mapFilter.getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


}
