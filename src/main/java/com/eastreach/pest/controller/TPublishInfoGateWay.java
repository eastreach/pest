package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TPublishInfo;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDPest;
import com.eastreach.pest.response.CommonReturnType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/publishInfo")
public class TPublishInfoGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add(String code, String name) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码已经存在");
        }
        tPublishInfo = new TPublishInfo();
        tPublishInfo.setCode(code);
        tPublishInfo.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tPublishInfo.setMemo(memo);
        }
        String content = getParam("content");
        if (content != null) {
            tPublishInfo.setContent(content);
        }
        tPublishInfoDao.save(tPublishInfo);
        return CommonReturnType.create(tPublishInfo);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        tPublishInfoDao.delete(tPublishInfo);
        return CommonReturnType.create(tPublishInfo);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TPublishInfo tPublishInfo = tPublishInfoDao.find(code);
        if (tPublishInfo == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
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
        tPublishInfoDao.save(tPublishInfo);
        return CommonReturnType.create(tPublishInfo);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TPublishInfo tPublishInfo = new TPublishInfo();
        String code = getParam("code");
        if (code != null) {
            tPublishInfo.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tPublishInfo.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tPublishInfo.setMemo("memo");
        }
        List<TPublishInfo> tPublishInfoList = tPublishInfoDao.findAll(Example.of(tPublishInfo));
        return CommonReturnType.create(tPublishInfoList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage(Integer pageSize, Integer currentPage) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TPublishInfo tPublishInfo = new TPublishInfo();
        String code = getParam("code");
        if (code != null) {
            tPublishInfo.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tPublishInfo.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tPublishInfo.setMemo("memo");
        }
        Page<TPublishInfo> tPublishInfoPage = tPublishInfoDao.findAll(Example.of(tPublishInfo), new PageRequest(currentPage, pageSize));
        return CommonReturnType.create(tPublishInfoPage);
    }
}
