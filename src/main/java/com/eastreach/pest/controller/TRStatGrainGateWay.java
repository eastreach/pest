package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatGrain;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.model.TZDGrain;
import com.eastreach.pest.model.TZDOperator;
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
import org.springframework.data.domain.Sort;
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
@RequestMapping("/statGrain")
public class TRStatGrainGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("year", "month", "areaCode", "grainCode", "grainValue"));
        TRStatGrain trStatGrain = new TRStatGrain();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(), new TypeToken<TRStatGrain>() {
                }.getType()),
                trStatGrain, Lists.<String>newArrayList("id"));
        trStatGrain.setDt(DateTime.now().withYear(trStatGrain.getYear()).withMonthOfYear(trStatGrain.getMonth()).withDayOfMonth(1).withTimeAtStartOfDay().toDate());
        TZDArea tzdArea = tzdAreaDao.findByCode(requestJson.optString("areaCode"));
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "区域不存在-" + requestJson.optString("areaCode"));
        } else {
            trStatGrain.setAreaCode(tzdArea.getCode());
        }
        TZDGrain tzdGrain = tzdGrainDao.find(requestJson.optString("grainCode"));
        if (tzdGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "作物不存在-" + requestJson.optString("grainCode"));
        } else {
            trStatGrain.setGrainCode(tzdGrain.getCode());
        }
        trStatGrain.setGrainValue(Double.parseDouble(requestJson.optString("grainValue")));
        trStatGrainDao.save(trStatGrain);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrain);
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
        checkParam(requestJson, Lists.newArrayList("tzdAreaList"));
        List<TRStatGrain> trStatGrainList = JSON.parseObject(requestJson.optString("trStatGrainList"), new TypeReference<ArrayList<TRStatGrain>>() {
        });
        for (TRStatGrain trStatGrain : trStatGrainList) {
            trStatGrain.setId(null);
            if (StringUtils.isEmpty(trStatGrain.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trStatGrainList-code");
            }
            TRStatGrain trStatGrain1 = trStatGrainDao.findByCode(trStatGrain.getCode());
            if (trStatGrain1 != null) {
                continue;
            }
            trStatGrainDao.save(trStatGrain);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainList);
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
        TRStatGrain trStatGrain = trStatGrainDao.findByCode(requestJson.optString("code"));
        if (trStatGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(), new TypeToken<TRStatGrain>() {
                }.getType()),
                trStatGrain, Lists.<String>newArrayList("id"));
        trStatGrainDao.save(trStatGrain);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrain);
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
        checkParam(requestJson, Lists.newArrayList("tzdAreaList"));
        List<TRStatGrain> trStatGrainList = JSON.parseObject(requestJson.optString("trStatGrainList"), new TypeReference<ArrayList<TRStatGrain>>() {
        });
        for (TRStatGrain trStatGrain : trStatGrainList) {
            trStatGrain.setId(null);
            if (StringUtils.isEmpty(trStatGrain.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trStatGrainList-code");
            }
            TRStatGrain trStatGrain1 = trStatGrainDao.findByCode(trStatGrain.getCode());
            if (trStatGrain1 != null) {
                Utils.copy(trStatGrain, trStatGrain1);
                trStatGrainDao.save(trStatGrain1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainList);
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
        TRStatGrain trStatGrain = trStatGrainDao.findByCode(requestJson.optString("code"));
        if (trStatGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trStatGrainDao.delete(trStatGrain);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrain);
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
        checkParam(requestJson, Lists.newArrayList("trStatGrainList"));
        List<TRStatGrain> trStatGrainList = JSON.parseObject(requestJson.optString("trStatGrainList"), new TypeReference<ArrayList<TRStatGrain>>() {
        });
        for (TRStatGrain trStatGrain : trStatGrainList) {
            trStatGrain.setId(null);
            if (StringUtils.isEmpty(trStatGrain.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trStatGrainList-code");
            }
            TRStatGrain trStatGrain1 = trStatGrainDao.findByCode(trStatGrain.getCode());
            if (trStatGrain1 != null) {
                trStatGrainDao.delete(trStatGrain1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TRStatGrain.class, Sets.<String>newHashSet("id"));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<TRStatGrain> trStatGrainList = trStatGrainDao.findAll(mapFilter.getWhereClause(),sort);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TRStatGrain.class, Sets.<String>newHashSet("id"));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<TRStatGrain> trStatGrainPage = trStatGrainDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson,sort));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(trStatGrainPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
