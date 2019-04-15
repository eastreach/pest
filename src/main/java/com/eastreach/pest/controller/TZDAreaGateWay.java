package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatPest;
import com.eastreach.pest.model.TZDArea;
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
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/area")
public class TZDAreaGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code", "name"));
        String code = getParam("code");
        String name = getParam("name");
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdArea = new TZDArea();
        setDomainProperty(tzdArea,Sets.<String>newHashSet("id","state"));
        tzdAreaDao.save(tzdArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdArea);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdAreaList"));
        List<TZDArea> tzdAreaList = JSON.parseObject(getParam("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                continue;
            }
            tzdAreaDao.save(tzdArea);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
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
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdAreaDao.delete(tzdArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdArea);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdAreaList"));
        List<TZDArea> tzdAreaList = JSON.parseObject(getParam("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                tzdAreaDao.delete(tzdArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
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
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        setDomainProperty(tzdArea,Sets.<String>newHashSet("id","state"));
        tzdAreaDao.save(tzdArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdArea);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdAreaList"));
        List<TZDArea> tzdAreaList = JSON.parseObject(getParam("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                Utils.copy(tzdArea, tzdArea1);
                tzdAreaDao.save(tzdArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDArea.class, Sets.<String>newHashSet("id"));
        List<TZDArea> tzdAreaList = tzdAreaDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDArea.class, Sets.<String>newHashSet("id"));
        Page<TZDArea> tzdAreaPage = tzdAreaDao.findAll(mapFilter.getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
