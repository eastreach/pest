package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TZDFeature;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDPest;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/feature")
public class TZDFeatureGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code", "name"));
        String code = getParam("code");
        String name = getParam("name");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdFeature = new TZDFeature();
        tzdFeature.setCode(code);
        tzdFeature.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tzdFeature.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdFeature.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdFeature.setPics(pics);
        }
        tzdFeatureDao.save(tzdFeature);
        return CommonReturnType.create(tzdFeature);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdFeatureDao.delete(tzdFeature);
        return CommonReturnType.create(tzdFeature);
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tzdFeature.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdFeature.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdFeature.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdFeature.setPics(pics);
        }
        tzdFeatureDao.save(tzdFeature);
        return CommonReturnType.create(tzdFeature);
    }

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TZDFeature>() {
            @Override
            public Predicate toPredicate(Root<TZDFeature> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("code") != null) {
                    predicate.add(cb.equal(root.get("code"), getParam("code")));
                }
                if (getParam("nameLike") != null) {
                    predicate.add(cb.like(root.get("name").as(String.class), "%" + getParam("nameLike") + "%"));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TZDFeature> tzdFeatureList = tzdFeatureDao.findAll(getWhereClause());
        return CommonReturnType.create(tzdFeatureList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TZDFeature> tzdFeaturePage = tzdFeatureDao.findAll(getWhereClause(), getPageRequest());
        return CommonReturnType.create(tzdFeaturePage);
    }
}
