package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
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
    public CommonReturnType register(String account, String password, String name) throws BusinessException {
        TZDOperator tzdOperator = tzdOperatorDao.find(account);
        if (tzdOperator != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "账号已经存在");
        }
        tzdOperator = new TZDOperator();
        tzdOperator.setAccount(account);
        tzdOperator.setPassword(password);
        tzdOperator.setName(name);
        tzdOperatorDao.save(tzdOperator);
        return CommonReturnType.create(tzdOperator);
    }


    @RequestMapping("/login")
    public CommonReturnType login(String account) throws BusinessException {
        auth();
        TZDOperator tzdOperator = tzdOperatorDao.find(account);
        return CommonReturnType.create(tzdOperator);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String account, String accountTarget) throws BusinessException {
        auth();
        TZDOperator tzdOperator = tzdOperatorDao.find(account);
        TZDOperator tzdOperatorTarget = tzdOperatorDao.find(accountTarget);
        if (tzdOperatorTarget == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "目标账号不存在");
        }
        return CommonReturnType.create(tzdOperator);
    }


    @RequestMapping("/findAll")
    public CommonReturnType findAll() throws BusinessException {
        auth();
        List<TZDOperator> tzdOperatorList = tzdOperatorDao.findAll();
        return CommonReturnType.create(tzdOperatorList);
    }


}
