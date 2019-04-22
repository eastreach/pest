package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.model.TZDFeature;
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
@RequestMapping("/feature")
public class TZDFeatureGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("code", "name"));
        String code = requestJson.optString("code");
        String name = requestJson.optString("name");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdFeature = new TZDFeature();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TZDFeature>(){}.getType()),
                tzdFeature,Lists.<String>newArrayList("id"));
        tzdFeature.setCode(code);
        tzdFeature.setName(name);
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
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdFeatureList"));
        List<TZDFeature> tzdFeatureList = JSON.parseObject(requestJson.optString("tzdFeatureList"), new TypeReference<ArrayList<TZDFeature>>() {
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
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("code"));
        String code = requestJson.optString("code");
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
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdFeatureList"));
        List<TZDFeature> tzdFeatureList = JSON.parseObject(requestJson.optString("tzdFeatureList"), new TypeReference<ArrayList<TZDFeature>>() {
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
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("code"));
        String code = requestJson.optString("code");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TZDFeature>(){}.getType()),
                tzdFeature,Lists.<String>newArrayList("id"));
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
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdFeatureList"));
        List<TZDFeature> tzdFeatureList = JSON.parseObject(requestJson.optString("tzdFeatureList"), new TypeReference<ArrayList<TZDFeature>>() {
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
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDFeature.class, Sets.<String>newHashSet("id"));
        List<TZDFeature> tzdFeatureList = tzdFeatureDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeatureList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDFeature.class, Sets.<String>newHashSet("id"));
        Page<TZDFeature> tzdFeaturePage = tzdFeatureDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdFeaturePage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
