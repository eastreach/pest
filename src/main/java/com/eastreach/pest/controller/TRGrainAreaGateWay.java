package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TPublishInfo;
import com.eastreach.pest.model.TRGrainArea;
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
@RequestMapping("/grainArea")
public class TRGrainAreaGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = requestJson.optString("grainCode");
        String areaCode = requestJson.optString("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        trGrainArea = new TRGrainArea();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TRGrainArea>(){}.getType()),
                trGrainArea,Lists.<String>newArrayList("id"));
        trGrainAreaDao.save(trGrainArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainArea);
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
        checkParam(requestJson, Lists.newArrayList("trGrainAreaList"));
        List<TRGrainArea> trGrainAreaList = JSON.parseObject(requestJson.optString("trGrainAreaList"), new TypeReference<ArrayList<TRGrainArea>>() {
        });
        for (TRGrainArea trGrainArea : trGrainAreaList) {
            trGrainArea.setId(null);
            if (StringUtils.isEmpty(trGrainArea.getAreaCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-areaCode");
            }
            if (StringUtils.isEmpty(trGrainArea.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-grainCode");
            }
            TRGrainArea trGrainArea1 = trGrainAreaDao.findByGrainCodeAndAreaCode(trGrainArea.getGrainCode(), trGrainArea.getAreaCode());
            if (trGrainArea1 != null) {
                continue;
            }
            trGrainAreaDao.save(trGrainArea);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = requestJson.optString("grainCode");
        String areaCode = requestJson.optString("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trGrainAreaDao.delete(trGrainArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainArea);
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
        checkParam(requestJson, Lists.newArrayList("trGrainAreaList"));
        List<TRGrainArea> trGrainAreaList = JSON.parseObject(requestJson.optString("trGrainAreaList"), new TypeReference<ArrayList<TRGrainArea>>() {
        });
        for (TRGrainArea trGrainArea : trGrainAreaList) {
            trGrainArea.setId(null);
            if (StringUtils.isEmpty(trGrainArea.getAreaCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-areaCode");
            }
            if (StringUtils.isEmpty(trGrainArea.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-grainCode");
            }
            TRGrainArea trGrainArea1 = trGrainAreaDao.findByGrainCodeAndAreaCode(trGrainArea.getGrainCode(), trGrainArea.getAreaCode());
            if (trGrainArea1 != null) {
                trGrainAreaDao.delete(trGrainArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = requestJson.optString("grainCode");
        String areaCode = requestJson.optString("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TRGrainArea>(){}.getType()),
                trGrainArea,Lists.<String>newArrayList("id"));
        trGrainAreaDao.save(trGrainArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainArea);
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
        checkParam(requestJson, Lists.newArrayList("trGrainAreaList"));
        List<TRGrainArea> trGrainAreaList = JSON.parseObject(requestJson.optString("trGrainAreaList"), new TypeReference<ArrayList<TRGrainArea>>() {
        });
        for (TRGrainArea trGrainArea : trGrainAreaList) {
            trGrainArea.setId(null);
            if (StringUtils.isEmpty(trGrainArea.getAreaCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-areaCode");
            }
            if (StringUtils.isEmpty(trGrainArea.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainAreaList-grainCode");
            }
            TRGrainArea trGrainArea1 = trGrainAreaDao.findByGrainCodeAndAreaCode(trGrainArea.getGrainCode(), trGrainArea.getAreaCode());
            if (trGrainArea1 != null) {
                Utils.copy(trGrainArea, trGrainArea1);
                trGrainAreaDao.save(trGrainArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TRGrainArea.class, Sets.<String>newHashSet("id"));
        List<TRGrainArea> trGrainAreaList = trGrainAreaDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TRGrainArea.class, Sets.<String>newHashSet("id"));
        Page<TRGrainArea> trGrainAreaPage = trGrainAreaDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
