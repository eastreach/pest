package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.model.TZDFeature;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDPest;
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
@RequestMapping("/area")
public class TZDAreaGateWay extends RootGateWay {

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TZDArea>() {
            @Override
            public Predicate toPredicate(Root<TZDArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("code") != null) {
                    predicate.add(cb.equal(root.get("code"), getParam("code")));
                }
                if (getParam("nameLike") != null) {
                    predicate.add(cb.like(root.get("name").as(String.class), "%" + getParam("nameLike") + "%"));
                }
                if (getParam("memo") != null) {
                    predicate.add(cb.like(root.get("memo").as(String.class), "%" + getParam("memo") + "%"));
                }
                if (getParam("areaDescLike") != null) {
                    predicate.add(cb.like(root.get("areaDesc").as(String.class), "%" + getParam("areaDescLike") + "%"));
                }
                if (getParam("featureDescLike") != null) {
                    predicate.add(cb.like(root.get("featureDesc").as(String.class), "%" + getParam("featureDescLike") + "%"));
                }
                if (getParam("grainDescLike") != null) {
                    predicate.add(cb.like(root.get("grainDesc").as(String.class), "%" + getParam("grainDescLike") + "%"));
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
        checkParam(Lists.newArrayList("code", "name"));
        String code = getParam("code");
        String name = getParam("name");
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
        }
        tzdArea = new TZDArea();
        tzdArea.setCode(code);
        tzdArea.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tzdArea.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdArea.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdArea.setPics(pics);
        }
        String areaDesc = getParam("areaDesc");
        if (areaDesc != null) {
            tzdArea.setAreaDesc(areaDesc);
        }
        String featureDesc = getParam("featureDesc");
        if (featureDesc != null) {
            tzdArea.setFeatureDesc(featureDesc);
        }
        String grainDesc = getParam("grainDesc");
        if (grainDesc != null) {
            tzdArea.setGrainDesc(grainDesc);
        }
        tzdAreaDao.save(tzdArea);
        return CommonReturnType.create(tzdArea);
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdAreaList"));
        List<TZDArea> tzdAreaList = JSON.parseObject(getParam("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                continue;
            }
            tzdAreaDao.save(tzdArea);
        }
        return CommonReturnType.create(tzdAreaList);
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
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tzdAreaDao.delete(tzdArea);
        return CommonReturnType.create(tzdArea);
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        checkParam(Lists.newArrayList("tzdAreaList"));
        List<TZDArea> tzdAreaList = JSON.parseObject(getParam("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                tzdAreaDao.delete(tzdArea1);
            }
        }
        return CommonReturnType.create(tzdAreaList);
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code"));
        String code = getParam("code");
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tzdArea.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdArea.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdArea.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdArea.setPics(pics);
        }
        String areaDesc = getParam("areaDesc");
        if (areaDesc != null) {
            tzdArea.setAreaDesc(areaDesc);
        }
        String featureDesc = getParam("featureDesc");
        if (featureDesc != null) {
            tzdArea.setFeatureDesc(featureDesc);
        }
        String grainDesc = getParam("grainDesc");
        if (grainDesc != null) {
            tzdArea.setGrainDesc(grainDesc);
        }
        tzdAreaDao.save(tzdArea);
        return CommonReturnType.create(tzdArea);
    }

    @Transactional
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        checkParam(Lists.newArrayList("tzdAreaList"));
        List<TZDArea> tzdAreaList = JSON.parseObject(getParam("tzdAreaList"), new TypeReference<ArrayList<TZDArea>>() {
        });
        for (TZDArea tzdArea : tzdAreaList) {
            tzdArea.setId(null);
            if (StringUtils.isEmpty(tzdArea.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-code");
            }
            if (StringUtils.isEmpty(tzdArea.getName())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdAreaList-name");
            }
            TZDArea tzdArea1 = tzdAreaDao.findByCode(tzdArea.getCode());
            if (tzdArea1 != null) {
                Utils.copy(tzdArea, tzdArea1);
                tzdAreaDao.save(tzdArea1);
            }
        }
        return CommonReturnType.create(tzdAreaList);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TZDArea> tzdAreaList = tzdAreaDao.findAll(getWhereClause());
        return CommonReturnType.create(tzdAreaList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TZDArea> tzdAreaPage = tzdAreaDao.findAll(getWhereClause(), getPageRequest());
        return CommonReturnType.create(tzdAreaPage);
    }
}
