package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/operator")
public class TZDOperatorGateWay extends RootGateWay {


    @RequestMapping("/register")
    public CommonReturnType register() throws BusinessException {
        //业务处理
        checkParam(Lists.<String>newArrayList("account", "password", "name"));
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
        tzdOperatorDao.save(tzdOperator);
        return CommonReturnType.create(tzdOperator);
    }


    @RequestMapping("/login")
    public CommonReturnType login() throws BusinessException {
        TZDOperator tzdOperator = auth();
        //业务处理
        return CommonReturnType.create(tzdOperator);
    }

    /**
     * todo json->object
     */
    @RequestMapping("/update")
    public CommonReturnType update(String account, String accountTarget) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("account", "password", "oldOperator","newOperator"));
        return CommonReturnType.create(tzdOperator);
    }


    @RequestMapping("/findAll")
    public CommonReturnType findAll() throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        List<TZDOperator> tzdOperatorList = tzdOperatorDao.findAll();
        return CommonReturnType.create(tzdOperatorList);
    }


}
