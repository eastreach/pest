package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.dao.TZDDepotDao;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDDepot;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/depot")
public class TZDDepotGateWay extends RootGateWay {

    @Autowired
    TZDDepotDao tzdDepotDao;

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("code", "name"));
        String code = requestJson.optString("code");
        String name = requestJson.optString("name");
        TZDDepot tzdDepot = tzdDepotDao.findByCode(code);
        if (tzdDepot != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdDepot = new TZDDepot();
        setDomainProperty(requestJson, tzdDepot, Sets.<String>newHashSet("id", "state"));
        tzdDepotDao.save(tzdDepot);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdDepot);
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
        checkParam(requestJson, Lists.newArrayList("tzdDepotList"));
        List<TZDDepot> tzdDepotList = JSON.parseObject(requestJson.optString("tzdDepotList"), new TypeReference<ArrayList<TZDDepot>>() {
        });
        for (TZDDepot tzdDepot : tzdDepotList) {
            tzdDepot.setId(null);
            if (StringUtils.isEmpty(tzdDepot.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdDepotList-code");
            }
            if (StringUtils.isEmpty(tzdDepot.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdDepotList-name");
            }
            TZDDepot tzdDepot1 = tzdDepotDao.findByCode(tzdDepot.getCode());
            if (tzdDepot1 != null) {
                continue;
            }
            tzdDepotDao.save(tzdDepot);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdDepotList);
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
        TZDDepot tzdDepot = tzdDepotDao.findByCode(code);
        if (tzdDepot == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdDepotDao.delete(tzdDepot);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdDepot);
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
        checkParam(requestJson, Lists.newArrayList("tzdDepotList"));
        List<TZDDepot> tzdDepotList = JSON.parseObject(requestJson.optString("tzdDepotList"), new TypeReference<ArrayList<TZDDepot>>() {
        });
        for (TZDDepot tzdDepot : tzdDepotList) {
            tzdDepot.setId(null);
            if (StringUtils.isEmpty(tzdDepot.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdDepotList-code");
            }
            if (StringUtils.isEmpty(tzdDepot.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdDepotList-name");
            }
            TZDDepot tzdDepot1 = tzdDepotDao.findByCode(tzdDepot.getCode());
            if (tzdDepot1 != null) {
                tzdDepotDao.delete(tzdDepot1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdDepotList);
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
        TZDDepot tzdDepot = tzdDepotDao.findByCode(code);
        if (tzdDepot == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        setDomainProperty(requestJson, tzdDepot, Sets.<String>newHashSet("id", "state"));
        tzdDepotDao.save(tzdDepot);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdDepot);
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
        checkParam(requestJson, Lists.newArrayList("tzdDepotList"));
        List<TZDDepot> tzdDepotList = JSON.parseObject(requestJson.optString("tzdDepotList"), new TypeReference<ArrayList<TZDDepot>>() {
        });
        for (TZDDepot tzdDepot : tzdDepotList) {
            tzdDepot.setId(null);
            if (StringUtils.isEmpty(tzdDepot.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdDepotList-code");
            }
            if (StringUtils.isEmpty(tzdDepot.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdDepotList-name");
            }
            TZDDepot tzdDepot1 = tzdDepotDao.findByCode(tzdDepot.getCode());
            if (tzdDepot1 != null) {
                Utils.copy(tzdDepot, tzdDepot1);
                tzdDepotDao.save(tzdDepot1);
            } else {
                tzdDepotDao.save(tzdDepot);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdDepotList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDDepot.class, Sets.<String>newHashSet("id"));
        List<TZDDepot> tzdDepotList = tzdDepotDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdDepotList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDDepot.class, Sets.<String>newHashSet("id"));
        Page<TZDDepot> page = tzdDepotDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(page);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
