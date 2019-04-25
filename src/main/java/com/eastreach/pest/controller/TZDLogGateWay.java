package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDLog;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.sf.json.JSONObject;
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
@RequestMapping("/log")
public class TZDLogGateWay extends RootGateWay {

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdLogList"));
        List<TZDLog> tzdLogList = JSON.parseObject(requestJson.optString("tzdLogList"), new TypeReference<ArrayList<TZDLog>>() {
        });
        for (TZDLog tzdLog : tzdLogList) {
            tzdLog.setId(null);
            if (StringUtils.isEmpty(tzdLog.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdLogList-code");
            }
            TZDLog tzdLog1 = tzdLogDao.findByCode(tzdLog.getCode());
            if (tzdLog1 != null) {
                tzdLogDao.delete(tzdLog1);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdLogList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDLog.class, Sets.<String>newHashSet("id"));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<TZDLog> tzdLogList = tzdLogDao.findAll(mapFilter.getWhereClause(),sort);
        return CommonReturnType.create(tzdLogList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDLog.class, Sets.<String>newHashSet("id"));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<TZDLog> tzdLogPage = tzdLogDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson,sort));
        return CommonReturnType.create(tzdLogPage);
    }

}
