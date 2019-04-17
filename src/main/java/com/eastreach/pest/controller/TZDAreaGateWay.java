package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.model.TZDOperator;
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
 *
 **/
@RestController
@RequestMapping("/area")
public class TZDAreaGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("code", "name"));
        String code = requestJson.optString("code");
        String name = requestJson.optString("name");
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdArea = new TZDArea();
        setDomainProperty(requestJson, tzdArea, Sets.<String>newHashSet("id", "state"));
        tzdAreaDao.save(tzdArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdArea);
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
        List<TZDArea> tzdAreaList = JSON.parseObject(requestJson.optString("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                continue;
            }
            tzdAreaDao.save(tzdArea);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
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
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdAreaDao.delete(tzdArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdArea);
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
        checkParam(requestJson, Lists.newArrayList("tzdAreaList"));
        List<TZDArea> tzdAreaList = JSON.parseObject(requestJson.optString("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                tzdAreaDao.delete(tzdArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
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
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        setDomainProperty(requestJson, tzdArea, Sets.<String>newHashSet("id", "state"));
        tzdAreaDao.save(tzdArea);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdArea);
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
        List<TZDArea> tzdAreaList = JSON.parseObject(requestJson.optString("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                Utils.copy(tzdArea, tzdArea1);
                tzdAreaDao.save(tzdArea1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDArea.class, Sets.<String>newHashSet("id"));
        List<TZDArea> tzdAreaList = tzdAreaDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDArea.class, Sets.<String>newHashSet("id"));
        Page<TZDArea> tzdAreaPage = tzdAreaDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdAreaPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
