package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.*;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.Utils;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/grainPest")
public class TRGrainPestGateWay extends RootGateWay {

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TRGrainPest>() {
            @Override
            public Predicate toPredicate(Root<TRGrainPest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("grainCode") != null) {
                    predicate.add(cb.equal(root.get("grainCode"), getParam("grainCode")));
                }
                if (getParam("pestCode") != null) {
                    predicate.add(cb.equal(root.get("pestCode"), getParam("pestCode")));
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
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = getParam("grainCode");
        String pestCode = getParam("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        trGrainPest = new TRGrainPest();
        trGrainPest.setGrainCode(grainCode);
        trGrainPest.setPestCode(pestCode);
        String name = getParam("name");
        if (name != null) {
            trGrainPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo(memo);
        }
        trGrainPestDao.save(trGrainPest);
        return CommonReturnType.create(trGrainPest);
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(getParam("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
        });
        for (TRGrainPest trGrainPest : trGrainPestList) {
            trGrainPest.setId(null);
            if (StringUtils.isEmpty(trGrainPest.getPestCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-pestCode");
            }
            if (StringUtils.isEmpty(trGrainPest.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-grainCode");
            }
            TRGrainPest trGrainPest1 = trGrainPestDao.findByGrainCodeAndPestCode(trGrainPest.getGrainCode(), trGrainPest.getPestCode());
            if (trGrainPest1 != null) {
                continue;
            }
            trGrainPestDao.delete(trGrainPest);
        }
        return CommonReturnType.create(trGrainPestList);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        TZDOperator tzdOperator = auth();

        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = getParam("grainCode");
        String pestCode = getParam("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trGrainPestDao.delete(trGrainPest);
        return CommonReturnType.create(trGrainPest);
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(getParam("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
        });
        for (TRGrainPest trGrainPest : trGrainPestList) {
            trGrainPest.setId(null);
            if (StringUtils.isEmpty(trGrainPest.getPestCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-pestCode");
            }
            if (StringUtils.isEmpty(trGrainPest.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-grainCode");
            }
            TRGrainPest trGrainPest1 = trGrainPestDao.findByGrainCodeAndPestCode(trGrainPest.getGrainCode(), trGrainPest.getPestCode());
            if (trGrainPest1 != null) {
                trGrainPestDao.delete(trGrainPest1);
            }
        }
        return CommonReturnType.create(trGrainPestList);
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "pestCode"));
        String grainCode = getParam("grainCode");
        String pestCode = getParam("pestCode");
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            trGrainPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo(memo);
        }

        trGrainPestDao.save(trGrainPest);
        return CommonReturnType.create(trGrainPest);
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("trGrainPestList"));
        List<TRGrainPest> trGrainPestList = JSON.parseObject(getParam("trGrainPestList"), new TypeReference<ArrayList<TRGrainPest>>() {
        });
        for (TRGrainPest trGrainPest : trGrainPestList) {
            trGrainPest.setId(null);
            if (StringUtils.isEmpty(trGrainPest.getPestCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-pestCode");
            }
            if (StringUtils.isEmpty(trGrainPest.getGrainCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "trGrainPestList-grainCode");
            }
            TRGrainPest trGrainPest1 = trGrainPestDao.findByGrainCodeAndPestCode(trGrainPest.getGrainCode(), trGrainPest.getPestCode());
            if (trGrainPest1 != null) {
                Utils.copy(trGrainPest, trGrainPest1);
                trGrainPestDao.save(trGrainPest1);
            }
        }
        return CommonReturnType.create(trGrainPestList);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TRGrainPest> trGrainPestList = trGrainPestDao.findAll(getWhereClause());
        return CommonReturnType.create(trGrainPestList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TRGrainPest> trGrainPestPage = trGrainPestDao.findAll(getWhereClause(), getPageRequest());
        return CommonReturnType.create(trGrainPestPage);
    }
}
