package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatPest;
import com.eastreach.pest.model.TZDGrain;
import com.eastreach.pest.model.TZDOperator;
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
@RequestMapping("/grain")
public class TZDGrainGateWay extends RootGateWay {

//    /**
//     * 动态生成where语句
//     */
//    @Override
//    Specification getWhereClause() {
//        return new Specification<TZDGrain>() {
//            @Override
//            public Predicate toPredicate(Root<TZDGrain> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicate = Lists.newArrayList();
//                if (getParam("code") != null) {
//                    predicate.add(cb.equal(root.get("code"), getParam("code")));
//                }
//                if (getParam("nameLike") != null) {
//                    predicate.add(cb.like(root.get("name").as(String.class), "%" + getParam("nameLike") + "%"));
//                }
//                Predicate[] pre = new Predicate[predicate.size()];
//                return query.where(predicate.toArray(pre)).getRestriction();
//            }
//        };
//    }


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code", "name"));
        String code = getParam("code");
        String name = getParam("name");
        TZDGrain tzdGrain = tzdGrainDao.find(code);
        if (tzdGrain != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdGrain = new TZDGrain();
        tzdGrain.setCode(code);
        tzdGrain.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tzdGrain.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdGrain.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdGrain.setPics(pics);
        }
        tzdGrainDao.save(tzdGrain);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrain);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdGrainList"));
        List<TZDGrain> tzdGrainList = JSON.parseObject(getParam("tzdGrainList"), new TypeReference<ArrayList<TZDGrain>>() {
        });
        for (TZDGrain tzdGrain : tzdGrainList) {
            tzdGrain.setId(null);
            if (StringUtils.isEmpty(tzdGrain.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdGrainList-code");
            }
            if (StringUtils.isEmpty(tzdGrain.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdGrainList-name");
            }
            TZDGrain tzdGrain1 = tzdGrainDao.find(tzdGrain.getCode());
            if (tzdGrain1 != null) {
                continue;
            }
            tzdGrainDao.save(tzdGrain);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDGrain tzdGrain = tzdGrainDao.find(code);
        if (tzdGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdGrainDao.delete(tzdGrain);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrain);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdGrainList"));
        List<TZDGrain> tzdGrainList = JSON.parseObject(getParam("tzdGrainList"), new TypeReference<ArrayList<TZDGrain>>() {
        });
        for (TZDGrain tzdGrain : tzdGrainList) {
            tzdGrain.setId(null);
            if (StringUtils.isEmpty(tzdGrain.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdGrainList-code");
            }
            if (StringUtils.isEmpty(tzdGrain.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdGrainList-name");
            }
            TZDGrain tzdGrain1 = tzdGrainDao.find(tzdGrain.getCode());
            if (tzdGrain1 != null) {
                tzdGrainDao.delete(tzdGrain1);
                continue;
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDGrain tzdGrain = tzdGrainDao.find(code);
        if (tzdGrain == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tzdGrain.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdGrain.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdGrain.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdGrain.setPics(pics);
        }
        tzdGrainDao.save(tzdGrain);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrain);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdGrainList"));
        List<TZDGrain> tzdGrainList = JSON.parseObject(getParam("tzdGrainList"), new TypeReference<ArrayList<TZDGrain>>() {
        });
        for (TZDGrain tzdGrain : tzdGrainList) {
            tzdGrain.setId(null);
            if (StringUtils.isEmpty(tzdGrain.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdGrainList-code");
            }
            if (StringUtils.isEmpty(tzdGrain.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdGrainList-name");
            }
            TZDGrain tzdGrain1 = tzdGrainDao.find(tzdGrain.getCode());
            if (tzdGrain1 != null) {
                Utils.copy(tzdGrain, tzdGrain1);
                tzdGrainDao.save(tzdGrain1);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDGrain.class, Sets.<String>newHashSet("id"));
        List<TZDGrain> tzdGrainList = tzdGrainDao.findAll(mapFilter.getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrainList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDGrain.class, Sets.<String>newHashSet("id"));
        Page<TZDGrain> tzdGrainPage = tzdGrainDao.findAll(mapFilter.getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdGrainPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

}
