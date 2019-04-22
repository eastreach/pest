package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TPublishInfo;
import com.eastreach.pest.model.TRGrainPest;
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
@RequestMapping("/grainPest")
public class TRGrainPestGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = requestJson.optString("grainCode");
        String pestCode = requestJson.optString("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        trGrainPest = new TRGrainPest();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TRGrainPest>(){}.getType()),
                trGrainPest,Lists.<String>newArrayList("id"));
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
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(requestJson.optString("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
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
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = requestJson.optString("grainCode");
        String pestCode = requestJson.optString("pestCode");
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
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(requestJson.optString("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
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
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = requestJson.optString("grainCode");
        String pestCode = requestJson.optString("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TRGrainPest>(){}.getType()),
                trGrainPest,Lists.<String>newArrayList("id"));
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
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(requestJson.optString("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
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
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TRGrainPest.class, Sets.<String>newHashSet("id"));
        List<TRGrainPest> trGrainPestList = trGrainPestDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TRGrainPest.class, Sets.<String>newHashSet("id"));
        Page<TRGrainPest> trGrainPestPage = trGrainPestDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainPestPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
