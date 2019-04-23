package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TPublishInfo;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.JSONUtil;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        checkParam(requestJson, Lists.newArrayList("name", "content"));
        TPublishInfo tPublishInfo = new TPublishInfo();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(), new TypeToken<TPublishInfo>() {
                }.getType()),
                tPublishInfo, Lists.<String>newArrayList("id"));
        tPublishInfoDao.save(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tPublishInfoList"));
        List<TPublishInfo> tPublishInfoList = JSON.parseObject(requestJson.optString("tPublishInfoList"), new TypeReference<ArrayList<TPublishInfo>>() {
        });
        for (TPublishInfo tPublishInfo : tPublishInfoList) {
            tPublishInfo.setId(null);
            if (StringUtils.isEmpty(tPublishInfo.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tPublishInfoList-code");
            }
            TPublishInfo tPublishInfo1 = tPublishInfoDao.findByCode(tPublishInfo.getCode());
            if (tPublishInfo1 != null) {
                continue;
            }
            tPublishInfoDao.save(tPublishInfo1);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("code"));
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

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tPublishInfoList"));
        List<TPublishInfo> tPublishInfoList = JSON.parseObject(requestJson.optString("tPublishInfoList"), new TypeReference<ArrayList<TPublishInfo>>() {
        });
        for (TPublishInfo tPublishInfo : tPublishInfoList) {
            tPublishInfo.setId(null);
            if (StringUtils.isEmpty(tPublishInfo.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tPublishInfoList-code");
            }
            TPublishInfo tPublishInfo1 = tPublishInfoDao.findByCode(tPublishInfo.getCode());
            if (tPublishInfo1 != null) {
                tPublishInfoDao.delete(tPublishInfo1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("code"));
        String code = requestJson.optString("code");
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(), new TypeToken<TPublishInfo>() {
                }.getType()),
                tPublishInfo, Lists.<String>newArrayList("id"));
        tPublishInfoDao.save(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
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
        checkParam(requestJson, Lists.newArrayList("tPublishInfoList"));
        List<TPublishInfo> tPublishInfoList = JSON.parseObject(requestJson.optString("tPublishInfoList"), new TypeReference<ArrayList<TPublishInfo>>() {
        });
        for (TPublishInfo tPublishInfo : tPublishInfoList) {
            tPublishInfo.setId(null);
            if (StringUtils.isEmpty(tPublishInfo.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tPublishInfoList-code");
            }
            TPublishInfo tPublishInfo1 = tPublishInfoDao.findByCode(tPublishInfo.getCode());
            if (tPublishInfo1 != null) {
                Utils.copy(tPublishInfo, tPublishInfo1);
                tPublishInfoDao.save(tPublishInfo1);
            }
        }
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TPublishInfo.class, Sets.<String>newHashSet("id"));
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
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TPublishInfo.class, Sets.<String>newHashSet("id"));
        Page<TPublishInfo> tPublishInfoPage = tPublishInfoDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
