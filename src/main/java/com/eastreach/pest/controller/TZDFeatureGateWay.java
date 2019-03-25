package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TZDFeature;
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
@RequestMapping("/feature")
public class TZDFeatureGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add(String code, String name) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码已经存在");
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
    public CommonReturnType delete(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        tzdFeatureDao.delete(tzdFeature);
        return CommonReturnType.create(tzdFeature);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDFeature tzdFeature = tzdFeatureDao.find(code);
        if (tzdFeature == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
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

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDFeature tzdFeature = new TZDFeature();
        String code = getParam("code");
        if (code != null) {
            tzdFeature.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdFeature.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdFeature.setMemo("memo");
        }
        List<TZDFeature> tzdFeatureList = tzdFeatureDao.findAll(Example.of(tzdFeature));
        return CommonReturnType.create(tzdFeatureList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage(Integer pageSize, Integer currentPage) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDFeature tzdFeature = new TZDFeature();
        String code = getParam("code");
        if (code != null) {
            tzdFeature.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdFeature.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdFeature.setMemo("memo");
        }
        Page<TZDFeature> tzdFeaturePage = tzdFeatureDao.findAll(Example.of(tzdFeature), new PageRequest(currentPage, pageSize));
        return CommonReturnType.create(tzdFeaturePage);
    }
}
