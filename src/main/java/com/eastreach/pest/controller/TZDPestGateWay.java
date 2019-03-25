package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
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
@RequestMapping("/pest")
public class TZDPestGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add(String code, String name) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDPest tzdPest = tzdPestDao.find(code);
        if (tzdPest != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码已经存在");
        }
        tzdPest = new TZDPest();
        tzdPest.setCode(code);
        tzdPest.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tzdPest.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdPest.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdPest.setPics(pics);
        }
        tzdPestDao.save(tzdPest);
        return CommonReturnType.create(tzdPest);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        TZDPest tzdPest = tzdPestDao.find(code);
        if (tzdPest == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        tzdPestDao.delete(tzdPest);
        return CommonReturnType.create(tzdPest);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDPest tzdPest = tzdPestDao.find(code);
        if (tzdPest == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tzdPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdPest.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdPest.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdPest.setPics(pics);
        }
        tzdPestDao.save(tzdPest);
        return CommonReturnType.create(tzdPest);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDPest tzdPest = new TZDPest();
        String code = getParam("code");
        if (code != null) {
            tzdPest.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdPest.setMemo("memo");
        }
        List<TZDPest> tzdPestList = tzdPestDao.findAll(Example.of(tzdPest));
        return CommonReturnType.create(tzdPestList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage(Integer pageSize, Integer currentPage) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDPest tzdPest = new TZDPest();
        String code = getParam("code");
        if (code != null) {
            tzdPest.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdPest.setMemo("memo");
        }
        Page<TZDPest> tzdPestPage = tzdPestDao.findAll(Example.of(tzdPest), new PageRequest(currentPage, pageSize));
        return CommonReturnType.create(tzdPestPage);
    }

}
