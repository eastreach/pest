package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDParam;
import com.eastreach.pest.model.TZDPest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/pest")
public class TZDPestGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.<String>newArrayList("code", "name"));
        String code = requestJson.optString("code");
        String name = requestJson.optString("name");
        TZDPest tzdPest = tzdPestDao.findFirstByCode(code);
        if (tzdPest != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdPest = new TZDPest();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TZDPest>(){}.getType()),
                tzdPest,Lists.<String>newArrayList());
        tzdPestDao.save(tzdPest);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPest);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    public CommonReturnType addBatch(@RequestBody JSONObject requestJson) throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        List<TZDPest> tzdPestList = JSON.parseObject(requestJson.getString("tzdPestList"), new TypeReference<ArrayList<TZDPest>>() {
        });
        for (TZDPest tzdPest : tzdPestList) {
            tzdPest.setId(null);
            if (StringUtils.isEmpty(tzdPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-code");
            }
            if (StringUtils.isEmpty(tzdPest.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-name");
            }
            TZDPest tzdPest1 = tzdPestDao.findFirstByCode(tzdPest.getCode());
            if (tzdPest1 != null) {
                continue;
            }
            tzdPestDao.save(tzdPest);
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
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
        TZDPest tzdPest = tzdPestDao.findFirstByCode(code);
        if (tzdPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdPestDao.delete(tzdPest);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPest);
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
        checkParam(requestJson, Lists.newArrayList("tzdPestList"));
        List<TZDPest> tzdPestList = JSON.parseObject(requestJson.optString("tzdPestList"), new TypeReference<ArrayList<TZDPest>>() {
        });
        for (TZDPest tzdPest : tzdPestList) {
            tzdPest.setId(null);
            if (StringUtils.isEmpty(tzdPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-code");
            }
            if (StringUtils.isEmpty(tzdPest.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-name");
            }
            TZDPest tzdPest1 = tzdPestDao.findFirstByCode(tzdPest.getCode());
            if (tzdPest1 != null) {
                tzdPestDao.delete(tzdPest1);
                continue;
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
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
        TZDPest tzdPest = tzdPestDao.findFirstByCode(code);
        if (tzdPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(),new TypeToken<TZDPest>(){}.getType()),
                tzdPest,Lists.<String>newArrayList());
        tzdPestDao.save(tzdPest);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPest);
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
        checkParam(requestJson, Lists.newArrayList("tzdPestList"));
        List<TZDPest> tzdPestList = JSON.parseObject(requestJson.optString("tzdPestList"), new TypeReference<ArrayList<TZDPest>>() {
        });
        for (TZDPest tzdPest : tzdPestList) {
            tzdPest.setId(null);
            if (StringUtils.isEmpty(tzdPest.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-code");
            }
            if (StringUtils.isEmpty(tzdPest.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdPestList-name");
            }
            TZDPest tzdPest1 = tzdPestDao.findFirstByCode(tzdPest.getCode());
            if (tzdPest1 != null) {
                Utils.copy(tzdPest, tzdPest1);
                tzdPestDao.save(tzdPest1);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDPest.class, Sets.<String>newHashSet("id"));
        List<TZDPest> tzdPestList = tzdPestDao.findAll(mapFilter.getWhereClause());

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDPest.class, Sets.<String>newHashSet("id"));
        Page<TZDPest> tzdPestPage = tzdPestDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdPestPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


}
