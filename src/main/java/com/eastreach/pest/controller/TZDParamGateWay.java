package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatPest;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDParam;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
 *
 **/
@RestController
@RequestMapping("/param")
public class TZDParamGateWay extends RootGateWay {

//    /**
//     * 动态生成where语句
//     */
//    @Override
//    Specification getWhereClause() {
//        return new Specification<TZDParam>() {
//            @Override
//            public Predicate toPredicate(Root<TZDParam> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicate = Lists.newArrayList();
//                if (getParam("code") != null) {
//                    predicate.add(cb.equal(root.get("code"), getParam("code")));
//                }
//                if (getParam("value") != null) {
//                    predicate.add(cb.equal(root.get("value"), getParam("value")));
//                }
//                if (getParam("memo") != null) {
//                    predicate.add(cb.like(root.get("memo").as(String.class), "%" + getParam("memo") + "%"));
//                }
//                Predicate[] pre = new Predicate[predicate.size()];
//                return query.where(predicate.toArray(pre)).getRestriction();
//            }
//        };
//    }


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code"));
        String code = getParam("code");
        TZDParam tzdParam = tzdParamDao.findByCode(code);
        if (tzdParam != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdParam = new TZDParam();
        tzdParam.setCode(code);
        String value = getParam("value");
        if (value != null) {
            tzdParam.setMemo(value);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdParam.setMemo(memo);
        }
        tzdParamDao.save(tzdParam);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdParam);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdParamList"));
        List<TZDParam> tzdParamList = JSON.parseObject(getParam("tzdParamList"), new TypeReference<ArrayList<TZDParam>>() {
        });
        for (TZDParam tzdParam : tzdParamList) {
            tzdParam.setId(null);
            if (StringUtils.isEmpty(tzdParam.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdParamList-code");
            }
            TZDParam tzdParam1 = tzdParamDao.findByCode(tzdParam.getCode());
            if (tzdParam1 != null) {
                continue;
            }
            tzdParamDao.save(tzdParam);
        }
        CommonReturnType commonReturnType = CommonReturnType.create(tzdParamList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code"));
        String code = getParam("code");
        TZDParam tzdParam = tzdParamDao.findByCode(code);
        if (tzdParam == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        tzdParamDao.delete(tzdParam);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdParam);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdParamList"));
        List<TZDParam> tzdParamList = JSON.parseObject(getParam("tzdParamList"), new TypeReference<ArrayList<TZDParam>>() {
        });
        for (TZDParam tzdParam : tzdParamList) {
            tzdParam.setId(null);
            if (StringUtils.isEmpty(tzdParam.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdParamList-code");
            }
            TZDParam tzdParam1 = tzdParamDao.findByCode(tzdParam.getCode());
            if (tzdParam1 != null) {
                tzdParamDao.delete(tzdParam1);
            }

        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdParamList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code"));
        String code = getParam("code");
        TZDParam tzdParam = tzdParamDao.findByCode(code);
        if (tzdParam == null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码不存在");
        }
        String value = getParam("value");
        if (value != null) {
            tzdParam.setMemo(value);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdParam.setMemo(memo);
        }
        tzdParamDao.save(tzdParam);

        CommonReturnType commonReturnType = CommonReturnType.create(tzdParam);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdParamList"));
        List<TZDParam> tzdParamList = JSON.parseObject(getParam("tzdParamList"), new TypeReference<ArrayList<TZDParam>>() {
        });
        for (TZDParam tzdParam : tzdParamList) {
            tzdParam.setId(null);
            if (StringUtils.isEmpty(tzdParam.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdParamList-code");
            }
            TZDParam tzdParam1 = tzdParamDao.findByCode(tzdParam.getCode());
            if (tzdParam1 != null) {
                Utils.copy(tzdParam, tzdParam1);
                tzdParamDao.save(tzdParam1);
            }

        }
        CommonReturnType commonReturnType = CommonReturnType.create(tzdParamList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDParam.class, Sets.<String>newHashSet("id"));
        List<TZDParam> tzdParamList = tzdParamDao.findAll(mapFilter.getWhereClause());

        CommonReturnType commonReturnType = CommonReturnType.create(tzdParamList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDParam.class, Sets.<String>newHashSet("id"));
        Page<TZDParam> tzdParamPage = tzdParamDao.findAll(mapFilter.getWhereClause(), getPageRequest());

        CommonReturnType commonReturnType = CommonReturnType.create(tzdParamPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
