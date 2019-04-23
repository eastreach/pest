package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TQuestion;
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
@RequestMapping("/question")
public class TQuestionGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList(""));
        TQuestion tQuestion = new TQuestion();
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(), new TypeToken<TQuestion>() {
                }.getType()),
                tQuestion, Lists.<String>newArrayList());
        tQuestionDao.save(tQuestion);

        CommonReturnType commonReturnType = CommonReturnType.create(tQuestion);
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
        checkParam(requestJson, Lists.newArrayList("tQuestionList"));
        List<TQuestion> tQuestionList = JSON.parseObject(requestJson.optString("tQuestionList"), new TypeReference<ArrayList<TQuestion>>() {
        });
        for (TQuestion tQuestion : tQuestionList) {
            tQuestion.setId(null);
            if (StringUtils.isEmpty(tQuestion.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tQuestionList-code");
            }
            TQuestion tQuestion1 = tQuestionDao.findByCode(tQuestion.getCode());
            if (tQuestion1 != null) {
                continue;
            }
            tQuestionDao.save(tQuestion1);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tQuestionList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("code"));
        String code = requestJson.optString("code");
        TQuestion tQuestion = tQuestionDao.findByCode(code);
        if (tQuestion == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        tQuestionDao.delete(tQuestion);

        CommonReturnType commonReturnType = CommonReturnType.create(tQuestion);
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
        checkParam(requestJson, Lists.newArrayList("tQuestionList"));
        List<TQuestion> tQuestionList = JSON.parseObject(requestJson.optString("tQuestionList"), new TypeReference<ArrayList<TQuestion>>() {
        });
        for (TQuestion tQuestion : tQuestionList) {
            tQuestion.setId(null);
            if (StringUtils.isEmpty(tQuestion.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tQuestionList-code");
            }
            TQuestion tQuestion1 = tQuestionDao.findByCode(tQuestion.getCode());
            if (tQuestion1 != null) {
                tQuestionDao.delete(tQuestion1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tQuestionList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("code"));
        String code = requestJson.optString("code");
        TQuestion tQuestion = tQuestionDao.findByCode(code);
        if (tQuestion == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        Utils.copy(JSONUtil.gson.fromJson(requestJson.toString(), new TypeToken<TQuestion>() {
                }.getType()),
                tQuestion, Lists.<String>newArrayList());
        tQuestionDao.save(tQuestion);

        CommonReturnType commonReturnType = CommonReturnType.create(tQuestion);
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
        checkParam(requestJson, Lists.newArrayList("tQuestionList"));
        List<TQuestion> tQuestionList = JSON.parseObject(requestJson.optString("tQuestionList"), new TypeReference<ArrayList<TQuestion>>() {
        });
        for (TQuestion tQuestion : tQuestionList) {
            tQuestion.setId(null);
            if (StringUtils.isEmpty(tQuestion.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tQuestionList-code");
            }
            TQuestion tQuestion1 = tQuestionDao.findByCode(tQuestion.getCode());
            if (tQuestion1 != null) {
                Utils.copy(tQuestion, tQuestion1);
                tQuestionDao.save(tQuestion1);
            }

        }
        CommonReturnType commonReturnType = CommonReturnType.create(tQuestionList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TQuestion.class, Sets.<String>newHashSet("id"));
        List<TQuestion> tQuestionList = tQuestionDao.findAll(mapFilter.getWhereClause());

        CommonReturnType commonReturnType = CommonReturnType.create(tQuestionList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TQuestion.class, Sets.<String>newHashSet("id"));
        Page<TQuestion> page = tQuestionDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson));

        CommonReturnType commonReturnType = CommonReturnType.create(page);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


}
