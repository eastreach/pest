package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TPublishInfo;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
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
@RequestMapping("/publishInfo")
public class TPublishInfoGateWay extends RootGateWay {

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TPublishInfo>() {
            @Override
            public Predicate toPredicate(Root<TPublishInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("code") != null) {
                    predicate.add(cb.equal(root.get("code"), getParam("code")));
                }
                if (getParam("name") != null) {
                    predicate.add(cb.like(root.get("name").as(String.class), "%" + getParam("name") + "%"));
                }
                if (getParam("startDt") != null) {
                    predicate.add(cb.greaterThanOrEqualTo(root.get("createDt").as(String.class), getParam("startDt")));
                }
                if (getParam("endDt") != null) {
                    predicate.add(cb.lessThanOrEqualTo(root.get("createDt").as(String.class), getParam("endDt")));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("name", "content"));
        String name = getParam("name");
        TPublishInfo tPublishInfo = new TPublishInfo();
        tPublishInfo.setName(name);
        tPublishInfo.setCreateOper(tzdOperator.getAccount());
        String memo = getParam("memo");
        if (memo != null) {
            tPublishInfo.setMemo(memo);
        }
        String content = getParam("content");
        if (content != null) {
            tPublishInfo.setContent(content);
        }
        if (!StringUtils.isEmpty(getParam("pic"))) {
            tPublishInfo.setPic(getParam("pic"));
        }
        if (!StringUtils.isEmpty(getParam("pics"))) {
            tPublishInfo.setPics(getParam("pics"));
        }
        tPublishInfoDao.save(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code"));
        String code = getParam("code");
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        tPublishInfoDao.delete(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("code"));
        String code = getParam("code");
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo == null) {
            throw new BusinessException(EnumBusinessError.DATA_NOT_EXIST_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tPublishInfo.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tPublishInfo.setMemo(memo);
        }
        String content = getParam("content");
        if (content != null) {
            tPublishInfo.setContent(content);
        }
        String createOper = getParam("createOper");
        if (!createOper.equals("")) {
            tPublishInfo.setCreateOper(createOper);
        }
        if (!StringUtils.isEmpty(getParam("pic"))) {
            tPublishInfo.setPic(getParam("pic"));
        }
        if (!StringUtils.isEmpty(getParam("pics"))) {
            tPublishInfo.setPics(getParam("pics"));
        }
        tPublishInfoDao.save(tPublishInfo);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfo);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TPublishInfo> tPublishInfoList = tPublishInfoDao.findAll(getWhereClause());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TPublishInfo> tPublishInfoPage = tPublishInfoDao.findAll(getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tPublishInfoPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }
}
