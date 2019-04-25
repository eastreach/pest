package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDOperatorLimit;
import com.eastreach.pest.model.TZDUrl;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
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
 * 权限管理
 **/
@RestController
@RequestMapping("/operatorLimit")
public class TZDOperatorLimitGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimit"));
        TZDOperatorLimit tzdOperatorLimit = JSON.parseObject(requestJson.optString("tzdOperatorLimit"), new TypeReference<TZDOperatorLimit>() {
        });
        tzdOperatorLimit.setId(null);
        TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
        if (tzdOperatorLimit1 != null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "数据已经存在");
        }
        tzdOperatorLimitDao.save(tzdOperatorLimit);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimit);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimitList"));
        List<TZDOperatorLimit> tzdOperatorLimitList = JSON.parseObject(requestJson.optString("tzdOperatorLimitList"), new TypeReference<ArrayList<TZDOperatorLimit>>() {
        });
        for (TZDOperatorLimit tzdOperatorLimit : tzdOperatorLimitList) {
            tzdOperatorLimit.setId(null);
            if (StringUtils.isEmpty(tzdOperatorLimit.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-account");
            }
            if (StringUtils.isEmpty(tzdOperatorLimit.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-url");
            }
            TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
            if (tzdOperatorLimit1 != null) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号已经存在");
            }
            tzdOperatorLimitDao.save(tzdOperatorLimit);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimitList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimit"));
        TZDOperatorLimit tzdOperatorLimit = JSON.parseObject(requestJson.optString("tzdOperatorLimit"), new TypeReference<TZDOperatorLimit>() {
        });
        tzdOperatorLimit.setId(null);
        TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
        if (tzdOperatorLimit1 == null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "数据不存在");
        }
        tzdOperatorLimitDao.delete(tzdOperatorLimit1);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimit1);
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
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimitList"));
        List<TZDOperatorLimit> tzdOperatorLimitList = JSON.parseObject(requestJson.optString("tzdOperatorLimitList"), new TypeReference<ArrayList<TZDOperatorLimit>>() {
        });
        for (TZDOperatorLimit tzdOperatorLimit : tzdOperatorLimitList) {
            tzdOperatorLimit.setId(null);
            if (StringUtils.isEmpty(tzdOperatorLimit.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-account");
            }
            if (StringUtils.isEmpty(tzdOperatorLimit.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-url");
            }
            TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
            if (tzdOperatorLimit1 != null) {
                tzdOperatorLimitDao.delete(tzdOperatorLimit1);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimitList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 账号信息修改
     */
    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimit"));
        TZDOperatorLimit tzdOperatorLimit = JSON.parseObject(requestJson.optString("tzdOperatorLimit"), new TypeReference<TZDOperatorLimit>() {
        });
        tzdOperatorLimit.setId(null);
        TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
        if (tzdOperatorLimit1 == null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "数据不存在");
        }
        Utils.copy(tzdOperatorLimit, tzdOperatorLimit1);
        tzdOperatorLimitDao.save(tzdOperatorLimit1);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimit1);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 批量修改账号信息
     */
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimitList"));
        List<TZDOperatorLimit> tzdOperatorLimitList = JSON.parseObject(requestJson.optString("tzdOperatorLimitList"), new TypeReference<ArrayList<TZDOperatorLimit>>() {
        });
        for (TZDOperatorLimit tzdOperatorLimit : tzdOperatorLimitList) {
            tzdOperatorLimit.setId(null);
            if (StringUtils.isEmpty(tzdOperatorLimit.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-account");
            }
            if (StringUtils.isEmpty(tzdOperatorLimit.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-url");
            }
            TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
            if (tzdOperatorLimit1 != null) {
                Utils.copy(tzdOperatorLimit, tzdOperatorLimit1);
                tzdOperatorLimitDao.save(tzdOperatorLimit1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimitList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDOperatorLimit.class, Sets.<String>newHashSet("id", "account", "password"));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<TZDOperatorLimit> tzdOperatorLimitList = tzdOperatorLimitDao.findAll(mapFilter.getWhereClause(),sort);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimitList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(requestJson, TZDOperatorLimit.class, Sets.<String>newHashSet("id", "account", "password"));
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<TZDOperatorLimit> tzdOperatorLimitPage = tzdOperatorLimitDao.findAll(mapFilter.getWhereClause(), getPageRequest(requestJson,sort));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimitPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/init")
    public CommonReturnType init() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        List<TZDUrl> tzdUrlList = tzdUrlDao.findAll();
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimitList"));
        List<TZDOperator> tzdOperatorList = JSON.parseObject(requestJson.optString("tzdOperatorList"), new TypeReference<ArrayList<TZDOperator>>() {
        });
        for (TZDOperator tzdOperator1 : tzdOperatorList) {
            tzdOperator1.setId(null);
            for (TZDUrl tzdUrl : tzdUrlList) {
                TZDOperatorLimit tzdOperatorLimit = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperator1.getAccount(), tzdUrl.getUrl());
                if (tzdOperatorLimit == null) {
                    tzdOperatorLimit = new TZDOperatorLimit();
                    tzdOperatorLimit.setAccount(tzdOperator1.getAccount());
                    tzdOperatorLimit.setUrl(tzdUrl.getUrl());
                    tzdOperatorLimit.setMemo(tzdUrl.getMemo());
                    tzdOperatorLimit.setState(0);
                    tzdOperatorLimitDao.save(tzdOperatorLimit);
                }
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(null);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @Transactional
    @RequestMapping("/open")
    public CommonReturnType open() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimitList"));
        List<TZDOperatorLimit> tzdOperatorLimitList = JSON.parseObject(requestJson.optString("tzdOperatorLimitList"), new TypeReference<ArrayList<TZDOperatorLimit>>() {
        });
        for (TZDOperatorLimit tzdOperatorLimit : tzdOperatorLimitList) {
            tzdOperatorLimit.setId(null);
            if (StringUtils.isEmpty(tzdOperatorLimit.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-account");
            }
            if (StringUtils.isEmpty(tzdOperatorLimit.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-url");
            }
            TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
            if (tzdOperatorLimit1 != null) {
                tzdOperatorLimit1.setIfLimit(1);
                tzdOperatorLimitDao.save(tzdOperatorLimit1);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimitList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/close")
    public CommonReturnType close() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);

        //业务处理
        checkParam(requestJson, Lists.newArrayList("tzdOperatorLimitList"));
        List<TZDOperatorLimit> tzdOperatorLimitList = JSON.parseObject(requestJson.optString("tzdOperatorLimitList"), new TypeReference<ArrayList<TZDOperatorLimit>>() {
        });
        for (TZDOperatorLimit tzdOperatorLimit : tzdOperatorLimitList) {
            tzdOperatorLimit.setId(null);
            if (StringUtils.isEmpty(tzdOperatorLimit.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-account");
            }
            if (StringUtils.isEmpty(tzdOperatorLimit.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorLimitList-url");
            }
            TZDOperatorLimit tzdOperatorLimit1 = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperatorLimit.getAccount(), tzdOperatorLimit.getUrl());
            if (tzdOperatorLimit1 != null) {
                tzdOperatorLimit1.setIfLimit(-1);
                tzdOperatorLimitDao.save(tzdOperatorLimit1);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorLimitList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

}
