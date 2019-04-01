package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TRGrainArea;
import com.eastreach.pest.model.TRGrainPest;
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
@RequestMapping("/grainArea")
public class TRGrainAreaGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = getParam("grainCode");
        String areaCode = getParam("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        trGrainArea = new TRGrainArea();
        trGrainArea.setGrainCode(grainCode);
        trGrainArea.setAreaCode(areaCode);
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo(memo);
        }
        trGrainAreaDao.save(trGrainArea);
        return CommonReturnType.create(trGrainArea);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = getParam("grainCode");
        String areaCode = getParam("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        trGrainAreaDao.delete(trGrainArea);
        return CommonReturnType.create(trGrainArea);
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("grainCode", "areaCode"));
        String grainCode = getParam("grainCode");
        String areaCode = getParam("areaCode");
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String state = getParam("state");
        if (state != null) {
            trGrainArea.setState(Integer.parseInt(state));
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo(memo);
        }
        trGrainAreaDao.save(trGrainArea);
        return CommonReturnType.create(trGrainArea);
    }

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TRGrainArea>() {
            @Override
            public Predicate toPredicate(Root<TRGrainArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("grainCode") != null) {
                    predicate.add(cb.equal(root.get("grainCode"), getParam("grainCode")));
                }
                if (getParam("areaCode") != null) {
                    predicate.add(cb.equal(root.get("areaCode"), getParam("areaCode")));
                }
                if (getParam("memo") != null) {
                    predicate.add(cb.like(root.get("memo").as(String.class), "%" + getParam("memo") + "%"));
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
        List<TRGrainArea> trGrainAreaList = trGrainAreaDao.findAll(getWhereClause());
        return CommonReturnType.create(trGrainAreaList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TRGrainArea> trGrainAreaPage = trGrainAreaDao.findAll(getWhereClause(), getPageRequest());
        return CommonReturnType.create(trGrainAreaPage);
    }
}
