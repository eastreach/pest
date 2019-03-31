package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.model.TZDArea;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 **/
@RestController
@RequestMapping("/statGrain")
public class TRStatGrainGateWay extends RootGateWay {

    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        TZDOperator tzdOperator = auth();

        //业务处理
        checkParam(Lists.<String>newArrayList("code", "name"));
        String code = getParam("code");
        String name = getParam("name");
        TZDArea tzdArea = tzdAreaDao.find(code);
        if (tzdArea != null) {
            throw new BusinessException(EnumBusinessError.DATA_EXIST_ERROR, "代码已经存在");
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
}
