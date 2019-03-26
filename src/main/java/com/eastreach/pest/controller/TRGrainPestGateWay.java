package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TRGrainPest;
import com.eastreach.pest.model.TZDArea;
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
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/grainPest")
public class TRGrainPestGateWay extends RootGateWay {


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

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TRGrainPest>() {
            @Override
            public Predicate toPredicate(Root<TRGrainPest> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (httpServletRequest.getParameter("grainCode") != null) {
                    predicate.add(cb.equal(root.get("grainCode"), httpServletRequest.getParameter("grainCode")));
                }
                if (httpServletRequest.getParameter("pestCode") != null) {
                    predicate.add(cb.equal(root.get("pestCode"), httpServletRequest.getParameter("pestCode")));
                }
                if (httpServletRequest.getParameter("memo") != null) {
                    predicate.add(cb.like(root.get("memo").as(String.class), "%" + httpServletRequest.getParameter("memo") + "%"));
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
