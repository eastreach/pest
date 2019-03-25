package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TRGrainPest;
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
@RequestMapping("/grainPest")
public class TRGrainPestGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add(String grainCode, String pestCode) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码已经存在");
        }
        trGrainPest = new TRGrainPest();
        trGrainPest.setGrainCode(grainCode);
        trGrainPest.setPestCode(pestCode);
        String name = getParam("name");
        if (name != null) {
            trGrainPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo(memo);
        }
        trGrainPestDao.save(trGrainPest);
        return CommonReturnType.create(trGrainPest);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete(String grainCode, String pestCode) throws BusinessException {
        TZDOperator tzdOperator = auth();

        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        trGrainPestDao.delete(trGrainPest);
        return CommonReturnType.create(trGrainPest);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String grainCode, String pestCode) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TRGrainPest trGrainPest = trGrainPestDao.find(grainCode, pestCode);
        if (trGrainPest == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            trGrainPest.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo(memo);
        }

        trGrainPestDao.save(trGrainPest);
        return CommonReturnType.create(trGrainPest);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TRGrainPest trGrainPest = new TRGrainPest();
        String grainCode = getParam("grainCode");
        if (grainCode != null) {
            trGrainPest.setGrainCode(grainCode);
        }
        String pestCode = getParam("pestCode");
        if (pestCode != null) {
            trGrainPest.setGrainCode(pestCode);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo("memo");
        }
        List<TRGrainPest> trGrainPestList = trGrainPestDao.findAll(Example.of(trGrainPest));
        return CommonReturnType.create(trGrainPestList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage(Integer pageSize, Integer currentPage) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TRGrainPest trGrainPest = new TRGrainPest();
        String grainCode = getParam("grainCode");
        if (grainCode != null) {
            trGrainPest.setGrainCode(grainCode);
        }
        String pestCode = getParam("pestCode");
        if (pestCode != null) {
            trGrainPest.setGrainCode(pestCode);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainPest.setMemo("memo");
        }
        Page<TRGrainPest> trGrainPestPage = trGrainPestDao.findAll(Example.of(trGrainPest), new PageRequest(currentPage, pageSize));
        return CommonReturnType.create(trGrainPestPage);
    }
}
