package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.metadata.TZDParamType;
import com.eastreach.pest.model.*;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
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
@RequestMapping("/operator")
public class TZDOperatorGateWay extends RootGateWay {

    /**
     * 账号注册
     */
    @RequestMapping("/register")
    public CommonReturnType register() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDParam tzdParam = initParam(new TZDParam(TZDParamType.operator_register_state, "1", "新注册账号默认状态"));
        //业务处理
        checkParam(requestJson,Lists.newArrayList("account", "password", "name"));
        String account = requestJson.optString("account");
        String password = requestJson.optString("password");
        String name = requestJson.optString("name");
        TZDOperator tzdOperator = tzdOperatorDao.findFirstByAccount(account);
        if (tzdOperator != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "账号已经存在");
        }
        tzdOperator = new TZDOperator();
        setDomainProperty(requestJson,tzdOperator,Sets.<String>newHashSet("id","state","ifRoot"));
        tzdOperator.setState(Integer.parseInt(tzdParam.getValue()));
        tzdOperatorDao.save(tzdOperator);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    /**
     * 账号登录
     */
    @RequestMapping("/login")
    public CommonReturnType login() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);
        //业务处理

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 修改自身账号信息
     */
    @RequestMapping("/updateSelf")
    public CommonReturnType updateSelf() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);
        setDomainProperty(requestJson,tzdOperator,Sets.<String>newHashSet("id","state","ifRoot","password"));

        //业务处理
        String newPassword = requestJson.optString("newPassword");
        if (StringUtils.isEmpty(newPassword)) {
            tzdOperator.setPassword(newPassword);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("operator"));
        TZDOperator operator = JSON.parseObject(requestJson.optString("operator"), new TypeReference<TZDOperator>() {
        });
        operator.setId(null);
        TZDOperator tzdOperator1 = tzdOperatorDao.findFirstByAccount(operator.getAccount());
        if (tzdOperator1 != null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号已经存在");
        }
        tzdOperatorDao.save(operator);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(operator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("tzdOperatorList"));
        List<TZDOperator> tzdOperatorList = JSON.parseObject(requestJson.optString("tzdOperatorList"), new TypeReference<ArrayList<TZDOperator>>() {
        });
        for (TZDOperator tzdOperator1 : tzdOperatorList) {
            tzdOperator1.setId(null);
            if (StringUtils.isEmpty(tzdOperator1.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorList-account");
            }
            TZDOperator tzdOperator2 = tzdOperatorDao.findFirstByAccount(tzdOperator1.getAccount());
            if (tzdOperator2 != null) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号已经存在");
            }
            tzdOperatorDao.save(tzdOperator1);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("operator"));
        TZDOperator operator = JSON.parseObject(requestJson.optString("operator"), new TypeReference<TZDOperator>() {
        });
        operator.setId(null);
        TZDOperator tzdOperator1 = tzdOperatorDao.findFirstByAccount(operator.getAccount());
        if (tzdOperator1 == null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号不存在");
        }
        tzdOperatorDao.delete(tzdOperator1);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator1);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("tzdOperatorList"));
        List<TZDOperator> tzdOperatorList = JSON.parseObject(requestJson.optString("tzdOperatorList"), new TypeReference<ArrayList<TZDOperator>>() {
        });
        for (TZDOperator tzdOperator1 : tzdOperatorList) {
            tzdOperator1.setId(null);
            if (StringUtils.isEmpty(tzdOperator1.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorList-account");
            }
            TZDOperator tzdOperator2 = tzdOperatorDao.findFirstByAccount(tzdOperator1.getAccount());
            if (tzdOperator2 != null) {
                tzdOperatorDao.delete(tzdOperator2);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 账号信息修改
     */
    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("operator"));
        TZDOperator operator = JSON.parseObject(requestJson.optString("operator"), new TypeReference<TZDOperator>() {
        });
        operator.setId(null);
        TZDOperator tzdOperator1 = tzdOperatorDao.findFirstByAccount(operator.getAccount());
        if (tzdOperator1 == null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号不存在");
        }
        Utils.copy(operator, tzdOperator1);
        tzdOperatorDao.save(tzdOperator1);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator1);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 批量修改账号信息
     */
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("tzdOperatorList"));
        List<TZDOperator> tzdOperatorList = JSON.parseObject(requestJson.optString("tzdOperatorList"), new TypeReference<ArrayList<TZDOperator>>() {
        });
        for (TZDOperator tzdOperator1 : tzdOperatorList) {
            tzdOperator1.setId(null);
            if (StringUtils.isEmpty(tzdOperator1.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorList-account");
            }
            TZDOperator tzdOperator2 = tzdOperatorDao.findFirstByAccount(tzdOperator1.getAccount());
            if (tzdOperator2 != null) {
                Utils.copy(tzdOperator1, tzdOperator2);
                tzdOperatorDao.save(tzdOperator2);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDOperator.class, Sets.<String>newHashSet("id", "account", "password"));
        List<TZDOperator> tzdOperatorList = tzdOperatorDao.findAll(mapFilter.getWhereClause());

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDOperator.class, Sets.<String>newHashSet("id", "account", "password"));
        Page<TZDOperator> tzdOperatorPage = tzdOperatorDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

}
