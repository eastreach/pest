package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDUrl;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 微服务管理
 **/
@RestController
@RequestMapping("/url")
public class TZDUrlGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("url"));
        String url = requestJson.optString("url");
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(url);
        if (tzdUrl != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdUrl = new TZDUrl();
        setDomainProperty(requestJson, tzdUrl, Sets.<String>newHashSet());
        tzdUrl.setUrl(url);
        tzdUrlDao.save(tzdUrl);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrl);
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
        checkParam(requestJson, Lists.newArrayList("tzdUrlList"));
        List<TZDUrl> tzdUrlList = JSON.parseObject(requestJson.optString("tzdUrlList"), new TypeReference<ArrayList<TZDUrl>>() {
        });
        for (TZDUrl tzdUrl : tzdUrlList) {
            tzdUrl.setId(null);
            if (StringUtils.isEmpty(tzdUrl.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdUrlList-url");
            }
            TZDUrl tzdUrl1 = tzdUrlDao.findByUrl(tzdUrl.getUrl());
            if (tzdUrl1 != null) {
                continue;
            }
            tzdUrlDao.save(tzdUrl);
        }
        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("url"));
        String url = requestJson.optString("url");
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(url);
        if (tzdUrl == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        tzdUrlDao.delete(tzdUrl);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrl);
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
        checkParam(requestJson, Lists.newArrayList("tzdUrlList"));
        List<TZDUrl> tzdUrlList = JSON.parseObject(requestJson.optString("tzdUrlList"), new TypeReference<ArrayList<TZDUrl>>() {
        });
        for (TZDUrl tzdUrl : tzdUrlList) {
            tzdUrl.setId(null);
            if (StringUtils.isEmpty(tzdUrl.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdUrlList-url");
            }
            TZDUrl tzdUrl1 = tzdUrlDao.findByUrl(tzdUrl.getUrl());
            if (tzdUrl1 != null) {
                tzdUrlDao.delete(tzdUrl1);
            }
        }

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("url"));
        String url = requestJson.optString("url");
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(url);
        if (tzdUrl == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        setDomainProperty(requestJson, tzdUrl, Sets.<String>newHashSet());
        tzdUrlDao.save(tzdUrl);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrl);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdUrlList"));
        List<TZDUrl> tzdUrlList = JSON.parseObject(requestJson.optString("tzdUrlList"), new TypeReference<ArrayList<TZDUrl>>() {
        });
        for (TZDUrl tzdUrl : tzdUrlList) {
            tzdUrl.setId(null);
            if (StringUtils.isEmpty(tzdUrl.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdUrlList-url");
            }
            TZDUrl tzdUrl1 = tzdUrlDao.findByUrl(tzdUrl.getUrl());
            if (tzdUrl1 != null) {
                Utils.copy(tzdUrl, tzdUrl1);
                tzdUrlDao.save(tzdUrl1);
            }
        }
        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDUrl.class, Sets.<String>newHashSet("id"));
        List<TZDUrl> tzdUrlList = tzdUrlDao.findAll(mapFilter.getWhereClause());

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDUrl.class, Sets.<String>newHashSet("id"));
        Page<TZDUrl> tzdUrlPage = tzdUrlDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
