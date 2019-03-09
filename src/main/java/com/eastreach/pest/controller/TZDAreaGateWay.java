package com.eastreach.pest.controller;

import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 **/
@RestController
@RequestMapping("/area")
public class TZDAreaGateWay extends RootGateWay {


    @RequestMapping("/add")
    public CommonReturnType add() throws BusinessException {
        TZDOperator tzdOperator = auth();
        return CommonReturnType.create(tzdOperator);
    }

    @RequestMapping("/delete")
    public CommonReturnType delete() throws BusinessException {
        TZDOperator tzdOperator = auth();
        return CommonReturnType.create(tzdOperator);
    }

    @RequestMapping("/update")
    public CommonReturnType update() throws BusinessException {
        TZDOperator tzdOperator = auth();
        return CommonReturnType.create(tzdOperator);
    }

    @RequestMapping("/select")
    public CommonReturnType select() throws BusinessException {
        TZDOperator tzdOperator = auth();
        return CommonReturnType.create(tzdOperator);
    }

    @RequestMapping("/selectPage")
    public CommonReturnType selectPage() throws BusinessException {
        TZDOperator tzdOperator = auth();
        return CommonReturnType.create(tzdOperator);
    }
}
