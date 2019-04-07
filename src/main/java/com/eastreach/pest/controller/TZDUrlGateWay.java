package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDUrl;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 微服务管理
 **/
@RestController
@RequestMapping("/url")
public class TZDUrlGateWay extends RootGateWay {

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TZDUrl>() {
            @Override
            public Predicate toPredicate(Root<TZDUrl> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("url") != null) {
                    predicate.add(cb.equal(root.get("url"), getParam("url")));
                }
                if (getParam("ifRoot") != null) {
                    predicate.add(cb.equal(root.get("ifRoot"), getParam("ifRoot")));
                }
                if (getParam("limitType") != null) {
                    predicate.add(cb.equal(root.get("limitType"), getParam("limitType")));
                }
                if (getParam("logLevel") != null) {
                    predicate.add(cb.equal(root.get("logLevel"), getParam("logLevel")));
                }
                if (getParam("urlLike") != null) {
                    predicate.add(cb.like(root.get("url").as(String.class), "%" + getParam("url") + "%"));
                }
                if (getParam("memo") != null) {
                    predicate.add(cb.like(root.get("memo").as(String.class), "%" + getParam("memo") + "%"));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("url"));
        String url = getParam("url");
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(url);
        if (tzdUrl != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdUrl = new TZDUrl();
        tzdUrl.setUrl(url);
        String memo = getParam("memo");
        if (memo != null) {
            tzdUrl.setMemo(memo);
        }
        String ifRoot = getParam("ifRoot");
        if (memo != null) {
            tzdUrl.setIfRoot(Integer.parseInt(ifRoot));
        }
        String limitType = getParam("limitType");
        if (limitType != null) {
            tzdUrl.setLimitType(Integer.parseInt(limitType));
        }
        String logLevel = getParam("logLevel");
        if (logLevel != null) {
            tzdUrl.setLogLevel(Integer.parseInt(logLevel));
        }
        tzdUrlDao.save(tzdUrl);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrl);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdUrlList"));
        List<TZDUrl> tzdUrlList = JSON.parseObject(getParam("tzdUrlList"), new TypeReference<ArrayList<TZDUrl>>() {
        });
        for (TZDUrl tzdUrl : tzdUrlList) {
            tzdUrl.setId(null);
            if (StringUtils.isEmpty(tzdUrl.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdUrlList-url");
            }
            TZDUrl tzdUrl1 = tzdUrlDao.findByUrl(tzdUrl.getUrl());
            if (tzdUrl1 != null) {
                continue;
            }
            tzdUrlDao.save(tzdUrl);
        }
        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("url"));
        String url = getParam("url");
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(url);
        if (tzdUrl == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        tzdUrlDao.delete(tzdUrl);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrl);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdUrlList"));
        List<TZDUrl> tzdUrlList = JSON.parseObject(getParam("tzdUrlList"), new TypeReference<ArrayList<TZDUrl>>() {
        });
        for (TZDUrl tzdUrl : tzdUrlList) {
            tzdUrl.setId(null);
            if (StringUtils.isEmpty(tzdUrl.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdUrlList-url");
            }
            TZDUrl tzdUrl1 = tzdUrlDao.findByUrl(tzdUrl.getUrl());
            if (tzdUrl1 != null) {
                tzdUrlDao.delete(tzdUrl1);
            }
        }

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("url"));
        String url = getParam("url");
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(url);
        if (tzdUrl == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdUrl.setMemo(memo);
        }
        String ifRoot = getParam("ifRoot");
        if (memo != null) {
            tzdUrl.setIfRoot(Integer.parseInt(ifRoot));
        }
        String limitType = getParam("limitType");
        if (limitType != null) {
            tzdUrl.setLimitType(Integer.parseInt(limitType));
        }
        String logLevel = getParam("logLevel");
        if (logLevel != null) {
            tzdUrl.setLogLevel(Integer.parseInt(logLevel));
        }
        tzdUrlDao.save(tzdUrl);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrl);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdUrlList"));
        List<TZDUrl> tzdUrlList = JSON.parseObject(getParam("tzdUrlList"), new TypeReference<ArrayList<TZDUrl>>() {
        });
        for (TZDUrl tzdUrl : tzdUrlList) {
            tzdUrl.setId(null);
            if (StringUtils.isEmpty(tzdUrl.getUrl())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdUrlList-url");
            }
            TZDUrl tzdUrl1 = tzdUrlDao.findByUrl(tzdUrl.getUrl());
            if (tzdUrl1 != null) {
                Utils.copy(tzdUrl, tzdUrl1);
                tzdUrlDao.save(tzdUrl1);
            }
        }
        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TZDUrl> tzdUrlList = tzdUrlDao.findAll(getWhereClause());

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TZDUrl> tzdUrlPage = tzdUrlDao.findAll(getWhereClause(), getPageRequest());

        CommonReturnType commonReturnType = CommonReturnType.create(tzdUrlPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
