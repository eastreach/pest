package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TZDGrain;
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
@RequestMapping("/grain")
public class TZDGrainGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add(String code, String name) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDGrain tzdGrain = tzdGrainDao.find(code);
        if (tzdGrain != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码已经存在");
        }
        tzdGrain = new TZDGrain();
        tzdGrain.setCode(code);
        tzdGrain.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tzdGrain.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdGrain.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdGrain.setPics(pics);
        }
        tzdGrainDao.save(tzdGrain);
        return CommonReturnType.create(tzdGrain);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        TZDGrain tzdGrain = tzdGrainDao.find(code);
        if (tzdGrain == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        tzdGrainDao.delete(tzdGrain);
        return CommonReturnType.create(tzdGrain);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDGrain tzdGrain = tzdGrainDao.find(code);
        if (tzdGrain == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tzdGrain.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdGrain.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdGrain.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdGrain.setPics(pics);
        }
        tzdGrainDao.save(tzdGrain);
        return CommonReturnType.create(tzdGrain);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDGrain tzdGrain = new TZDGrain();
        String code = getParam("code");
        if (code != null) {
            tzdGrain.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdGrain.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdGrain.setMemo("memo");
        }
        List<TZDGrain> tzdGrainList = tzdGrainDao.findAll(Example.of(tzdGrain));
        return CommonReturnType.create(tzdGrainList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage(Integer pageSize, Integer currentPage) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDGrain tzdGrain = new TZDGrain();
        String code = getParam("code");
        if (code != null) {
            tzdGrain.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdGrain.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdGrain.setMemo("memo");
        }
        Page<TZDGrain> tzdGrainPage = tzdGrainDao.findAll(Example.of(tzdGrain), new PageRequest(currentPage, pageSize));
        return CommonReturnType.create(tzdGrainPage);
    }

}
