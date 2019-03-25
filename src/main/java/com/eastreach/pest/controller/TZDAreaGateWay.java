package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.metadata.TZDLimit;
import com.eastreach.pest.model.TZDArea;
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
@RequestMapping("/area")
public class TZDAreaGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add(String code, String name) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea != null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码已经存在");
        }
        tzdArea = new TZDArea();
        tzdArea.setCode(code);
        tzdArea.setName(name);
        String memo = getParam("memo");
        if (memo != null) {
            tzdArea.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdArea.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdArea.setPics(pics);
        }
        tzdAreaDao.save(tzdArea);
        return CommonReturnType.create(tzdArea);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();

        if (!auth(tzdOperator, TZDLimit.limit_code_root)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要管理员权限");
        }

        //业务处理
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        tzdAreaDao.delete(tzdArea);
        return CommonReturnType.create(tzdArea);
    }

    @RequestMapping("/update")
    public CommonReturnType update(String code) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "代码不存在");
        }
        String name = getParam("name");
        if (name != null) {
            tzdArea.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdArea.setMemo(memo);
        }
        String pic = getParam("pic");
        if (pic != null) {
            tzdArea.setPic(pic);
        }
        String pics = getParam("pics");
        if (pics != null) {
            tzdArea.setPics(pics);
        }
        tzdAreaDao.save(tzdArea);
        return CommonReturnType.create(tzdArea);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDArea tzdArea = new TZDArea();
        String code = getParam("code");
        if (code != null) {
            tzdArea.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdArea.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdArea.setMemo("memo");
        }
        List<TZDArea> tzdAreaList = tzdAreaDao.findAll(Example.of(tzdArea));
        return CommonReturnType.create(tzdAreaList);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage(Integer pageSize, Integer currentPage) throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        TZDArea tzdArea = new TZDArea();
        String code = getParam("code");
        if (code != null) {
            tzdArea.setCode(code);
        }
        String name = getParam("name");
        if (name != null) {
            tzdArea.setName(name);
        }
        String memo = getParam("memo");
        if (memo != null) {
            tzdArea.setMemo("memo");
        }
        Page<TZDArea> tzdAreaPage = tzdAreaDao.findAll(Example.of(tzdArea), new PageRequest(currentPage, pageSize));
        return CommonReturnType.create(tzdAreaPage);
    }
}
