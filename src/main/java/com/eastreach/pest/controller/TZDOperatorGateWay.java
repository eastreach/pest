package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.metadata.TZDParamType;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDParam;
import com.eastreach.pest.model.TZDPest;
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
 *
 **/
@RestController
@RequestMapping("/operator")
public class TZDOperatorGateWay extends RootGateWay {

    /**
     * 动态生成where语句
     */
    @Override
    Specification getWhereClause() {
        return new Specification<TZDOperator>() {
            @Override
            public Predicate toPredicate(Root<TZDOperator> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = Lists.newArrayList();
                if (getParam("state") != null) {
                    predicate.add(cb.equal(root.get("state"), getParam("state")));
                }
                if (getParam("ifRoot") != null) {
                    predicate.add(cb.equal(root.get("ifRoot"), getParam("ifRoot")));
                }
                if (getParam("account") != null) {
                    predicate.add(cb.equal(root.get("account"), getParam("account")));
                }
                if (getParam("telephone") != null) {
                    predicate.add(cb.equal(root.get("telephone"), getParam("telephone")));
                }
                if (getParam("name") != null) {
                    predicate.add(cb.equal(root.get("name"), getParam("name")));
                }
                if (getParam("nameLike") != null) {
                    predicate.add(cb.like(root.get("name").as(String.class), "%" + getParam("nameLike") + "%"));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    /**
     * 账号注册
     */
    @RequestMapping("/register")
    public CommonReturnType register() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDParam tzdParam = initParam(new TZDParam(TZDParamType.operator_register_state, "1", "新注册账号默认状态"));
        //业务处理
        checkParam(Lists.newArrayList("account", "password", "name"));
        String account = getParam("account");
        String password = getParam("password");
        String name = getParam("name");
        TZDOperator tzdOperator = tzdOperatorDao.find(account);
        if (tzdOperator != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "账号已经存在");
        }
        tzdOperator = new TZDOperator();
        tzdOperator.setAccount(account);
        tzdOperator.setPassword(password);
        tzdOperator.setName(name);
        tzdOperator.setState(Integer.parseInt(tzdParam.getValue()));
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    /**
     * 账号登录
     */
    @RequestMapping("/login")
    public CommonReturnType login() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();
        //业务处理

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 修改自身账号信息
     */
    @RequestMapping("/updateSelf")
    public CommonReturnType updateSelf() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        String newPassword = getParam("newPassword");
        if (newPassword != null) {
            tzdOperator.setPassword(newPassword);
        }
        String name = getParam("name");
        if (name != null) {
            tzdOperator.setName(name);
        }
        String telephone = getParam("telephone");
        if (telephone != null) {
            tzdOperator.setTelephone(telephone);
        }
        String place = getParam("place");
        if (place != null) {
            tzdOperator.setPlace(place);
        }
        String province = getParam("province");
        if (province != null) {
            tzdOperator.setProvince(province);
        }
        String city = getParam("city");
        if (city != null) {
            tzdOperator.setCity(city);
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("operator"));
        TZDOperator operator = JSON.parseObject(getParam("operator"), new TypeReference<TZDOperator>() {
        });
        operator.setId(null);
        TZDOperator tzdOperator1 = tzdOperatorDao.find(operator.getAccount());
        if (tzdOperator1 != null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号已经存在");
        }
        tzdOperatorDao.save(operator);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(operator);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/addBatch")
    public CommonReturnType addBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdOperatorList"));
        List<TZDOperator> tzdOperatorList = JSON.parseObject(getParam("tzdOperatorList"), new TypeReference<ArrayList<TZDOperator>>() {
        });
        for (TZDOperator tzdOperator1 : tzdOperatorList) {
            tzdOperator1.setId(null);
            if (StringUtils.isEmpty(tzdOperator1.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorList-account");
            }
            TZDOperator tzdOperator2 = tzdOperatorDao.find(tzdOperator1.getAccount());
            if (tzdOperator2 != null) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号已经存在");
            }
            tzdOperatorDao.save(tzdOperator1);
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("operator"));
        TZDOperator operator = JSON.parseObject(getParam("operator"), new TypeReference<TZDOperator>() {
        });
        operator.setId(null);
        TZDOperator tzdOperator1 = tzdOperatorDao.find(operator.getAccount());
        if (tzdOperator1 == null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号不存在");
        }
        tzdOperatorDao.delete(tzdOperator1);

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator1);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @Transactional
    @RequestMapping("/deleteBatch")
    public CommonReturnType deleteBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdOperatorList"));
        List<TZDOperator> tzdOperatorList = JSON.parseObject(getParam("tzdOperatorList"), new TypeReference<ArrayList<TZDOperator>>() {
        });
        for (TZDOperator tzdOperator1 : tzdOperatorList) {
            tzdOperator1.setId(null);
            if (StringUtils.isEmpty(tzdOperator1.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorList-account");
            }
            TZDOperator tzdOperator2 = tzdOperatorDao.find(tzdOperator1.getAccount());
            if (tzdOperator2 != null) {
                tzdOperatorDao.delete(tzdOperator2);
            }
        }

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 账号信息修改
     */
    @RequestMapping("/update")
    public CommonReturnType update() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("operator"));
        TZDOperator operator = JSON.parseObject(getParam("operator"), new TypeReference<TZDOperator>() {
        });
        operator.setId(null);
        TZDOperator tzdOperator1 = tzdOperatorDao.find(operator.getAccount());
        if (tzdOperator1 == null) {
            throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "账号不存在");
        }
        Utils.copy(operator, tzdOperator1);
        tzdOperatorDao.save(tzdOperator1);
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperator1);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    /**
     * 批量修改账号信息
     */
    @RequestMapping("/updateBatch")
    public CommonReturnType updateBatch() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.newArrayList("tzdOperatorList"));
        List<TZDOperator> tzdOperatorList = JSON.parseObject(getParam("tzdOperatorList"), new TypeReference<ArrayList<TZDOperator>>() {
        });
        for (TZDOperator tzdOperator1 : tzdOperatorList) {
            tzdOperator1.setId(null);
            if (StringUtils.isEmpty(tzdOperator1.getAccount())) {
                throw new BusinessException(EnumBusinessError.DATA_CONNENT_ERROR, "tzdOperatorList-account");
            }
            TZDOperator tzdOperator2 = tzdOperatorDao.find(tzdOperator1.getAccount());
            if (tzdOperator2 != null) {
                Utils.copy(tzdOperator1, tzdOperator2);
                tzdOperatorDao.save(tzdOperator2);
            }
        }
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }


    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        List<TZDOperator> tzdOperatorList = tzdOperatorDao.findAll(getWhereClause());

        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorList);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        initLimit(TZDLimitType.limit_ifRoot_yes, TZDLimitType.limit_type_0);
        TZDOperator tzdOperator = auth();

        //业务处理
        Page<TZDOperator> tzdOperatorPage = tzdOperatorDao.findAll(getWhereClause(), getPageRequest());
        //返回结果
        CommonReturnType commonReturnType = CommonReturnType.create(tzdOperatorPage);
        log(tzdOperator, commonReturnType);
        return commonReturnType;
    }

}
