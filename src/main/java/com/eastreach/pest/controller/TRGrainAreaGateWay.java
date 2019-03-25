package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TRGrainArea;
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
@RequestMapping("/grainArea")
public class TRGrainAreaGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add(String grainCode, String areaCode) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码已经存在");
        }
        trGrainArea = new TRGrainArea();
        trGrainArea.setGrainCode(grainCode);
        trGrainArea.setAreaCode(areaCode);
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo(memo);
        }
        trGrainAreaDao.save(trGrainArea);
        return CommonReturnType.create(trGrainArea);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete(String grainCode, String areaCode) throws BusinessException {
        TZDOperator tzdOperator = auth();
        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        trGrainAreaDao.delete(trGrainArea);
        return CommonReturnType.create(trGrainArea);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String grainCode, String areaCode) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TRGrainArea trGrainArea = trGrainAreaDao.find(grainCode, areaCode);
        if (trGrainArea == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        String state = getParam("state");
        if (state != null) {
            trGrainArea.setState(Integer.parseInt(state));
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo(memo);
        }
        trGrainAreaDao.save(trGrainArea);
        return CommonReturnType.create(trGrainArea);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TRGrainArea trGrainArea = new TRGrainArea();
        String grainCode = getParam("grainCode");
        if (grainCode != null) {
            trGrainArea.setGrainCode(grainCode);
        }
        String areaCode = getParam("areaCode");
        if (areaCode != null) {
            trGrainArea.setAreaCode(areaCode);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo("memo");
        }
        List<TRGrainArea> trGrainAreaList = trGrainAreaDao.findAll(Example.of(trGrainArea));
        return CommonReturnType.create(trGrainAreaList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage(Integer pageSize, Integer currentPage) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        //业务处理
        TRGrainArea trGrainArea = new TRGrainArea();
        String grainCode = getParam("grainCode");
        if (grainCode != null) {
            trGrainArea.setGrainCode(grainCode);
        }
        String areaCode = getParam("areaCode");
        if (areaCode != null) {
            trGrainArea.setAreaCode(areaCode);
        }
        String memo = getParam("memo");
        if (memo != null) {
            trGrainArea.setMemo("memo");
        }
        Page<TRGrainArea> trGrainAreaPage = trGrainAreaDao.findAll(Example.of(trGrainArea), new PageRequest(currentPage, pageSize));
        return CommonReturnType.create(trGrainAreaPage);
    }
}
