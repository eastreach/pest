package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TPublishInfo;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/publishInfo")
public class TPublishInfoGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("name", "content"));
        TPublishInfo tPublishInfo = new TPublishInfo();
        setDomainProperty(requestJson,tPublishInfo,Sets.<String>newHashSet("id"));
        tPublishInfoDao.save(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("code"));
        String code = requestJson.optString("code");
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tPublishInfoDao.delete(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("code"));
        String code = requestJson.optString("code");
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        setDomainProperty(requestJson,tPublishInfo,Sets.<String>newHashSet("id"));
        tPublishInfoDao.save(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson,TPublishInfo.class, Sets.<String>newHashSet("id"));
        List<TPublishInfo> tPublishInfoList = tPublishInfoDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson,TPublishInfo.class, Sets.<String>newHashSet("id"));
        Page<TPublishInfo> tPublishInfoPage = tPublishInfoDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
