package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.*;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.JSONUtil;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/statPest")
public class TRStatPestGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.newArrayList("year", "month", "areaCode", "grainCode", "pestCode", "pestValue"));
        TRStatPest trStatPest = new TRStatPest();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TRStatPest>(){}.getType()),
                trStatPest,Lists.<String>newArrayList("id"));
        trStatPest.setYear(Integer.parseInt(requestJson.optString("year")));
        trStatPest.setMonth(Integer.parseInt(requestJson.optString("month")));
        trStatPest.setDt(DateTime.now().withYear(trStatPest.getYear()).withMonthOfYear(trStatPest.getMonth()).withDayOfMonth(1).withTimeAtStartOfDay().toDate());
        TZDArea tzdArea = tzdAreaDao.findByCode(requestJson.optString("areaCode"));
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "区域不存在-" + requestJson.optString("areaCode"));
        } else {
            trStatPest.setAreaCode(tzdArea.getCode());
        }
        TZDGrain tzdGrain = tzdGrainDao.find(requestJson.optString("grainCode"));
        if (tzdGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "作物不存在-" + requestJson.optString("grainCode"));
        } else {
            trStatPest.setGrainCode(tzdGrain.getCode());
        }
        TZDPest tzdPest = tzdPestDao.findFirstByCode(requestJson.optString("pestCode"));
        if (tzdPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "害虫不存在-" + requestJson.optString("pestCode"));
        } else {
            trStatPest.setPestCode(tzdPest.getCode());
        }
        trStatPestDao.save(trStatPest);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatPest);
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
        TRStatPest trStatPest = trStatPestDao.findByCode(requestJson.optString("code"));
        if (trStatPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TRStatPest>(){}.getType()),
                trStatPest,Lists.<String>newArrayList("id"));
        trStatPestDao.save(trStatPest);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson,Lists.<String>newArrayList("code"));
        TRStatPest trStatPest = trStatPestDao.findByCode(requestJson.optString("code"));
        if (trStatPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trStatPestDao.delete(trStatPest);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatPest);
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
        checkParam(requestJson,Lists.newArrayList("trStatPestList"));
        List<TRStatPest> trStatPestList = JSON.parseObject(requestJson.optString("trStatPestList"), new TypeReference<ArrayList<TRStatPest>>() {
        });
        for (TRStatPest trStatPest : trStatPestList) {
            trStatPest.setId(null);
            if (StringUtils.isEmpty(trStatPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trStatPestList-code");
            }
            TRStatPest trStatPest1 = trStatPestDao.findByCode(trStatPest.getCode());
            if (trStatPest1 != null) {
                trStatPestDao.delete(trStatPest1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson,TRStatPest.class, Sets.<String>newHashSet("id"));
        List<TRStatPest> trStatPestList = trStatPestDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson,TRStatPest.class, Sets.<String>newHashSet("id"));
        Page<TRStatPest> trGrainAreaPage = trStatPestDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trGrainAreaPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

}
