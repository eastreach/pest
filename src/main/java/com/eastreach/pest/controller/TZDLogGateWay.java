package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TRStatPest;
import com.eastreach.pest.model.TZDLog;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.eastreach.pest.util.MapFilter;
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
@RequestMapping("/log")
public class TZDLogGateWay extends RootGateWay {

//    /**
//     * 动态生成where语句
//     */
//    @Override
//    Specification getWhereClause() {
//        return new Specification<TZDLog>() {
//            @Override
//            public Predicate toPredicate(Root<TZDLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicate = Lists.newArrayList();
//                if (getParam("url") != null) {
//                    predicate.add(cb.equal(root.get("url"), getParam("url")));
//                }
//                if (getParam("account") != null) {
//                    predicate.add(cb.equal(root.get("account"), getParam("account")));
//                }
//                if (getParam("state") != null) {
//                    predicate.add(cb.equal(root.get("state"), getParam("state")));
//                }
//                if (getParam("errCode") != null) {
//                    predicate.add(cb.equal(root.get("errCode"), getParam("errCode")));
//                }
//                if (getParam("urlLike") != null) {
//                    predicate.add(cb.like(root.get("url").as(String.class), "%" + getParam("url") + "%"));
//                }
//                if (getParam("startDt") != null) {
//                    predicate.add(cb.greaterThanOrEqualTo(root.get("dt").as(String.class), getParam("startDt")));
//                }
//                if (getParam("endDt") != null) {
//                    predicate.add(cb.lessThanOrEqualTo(root.get("dt").as(String.class), getParam("endDt")));
//                }
//                Predicate[] pre = new Predicate[predicate.size()];
//                return query.where(predicate.toArray(pre)).getRestriction();
//            }
//        };
//    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdLogList"));
        List<TZDLog> tzdLogList = JSON.parseObject(getParam("tzdLogList"), new TypeReference<ArrayList<TZDLog>>() {
        });
        for (TZDLog tzdLog : tzdLogList) {
            tzdLog.setId(null);
            if (StringUtils.isEmpty(tzdLog.getCode())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdLogList-code");
            }
            TZDLog tzdLog1 = tzdLogDao.findByCode(tzdLog.getCode());
            if (tzdLog1 != null) {
                tzdLogDao.delete(tzdLog1);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdLogList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDLog.class, Sets.<String>newHashSet("id"));
        List<TZDLog> tzdLogList = tzdLogDao.findAll(mapFilter.getWhereClause());
        return CommonReturnType.create(tzdLogList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        MapFilter mapFilter = MapFilter.newInstance(httpServletRequest,TZDLog.class, Sets.<String>newHashSet("id"));
        Page<TZDLog> tzdLogPage = tzdLogDao.findAll(mapFilter.getWhereClause(), getPageRequest());
        return CommonReturnType.create(tzdLogPage);
    }

}
