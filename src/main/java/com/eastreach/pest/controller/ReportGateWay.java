package com.eastreach.pest.controller;

import com.eastreach.pest.dao.SQLDao;
import com.eastreach.pest.metadata.APIDef;
import com.eastreach.pest.metadata.TZDLimitType;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 **/
@RestController
@RequestMapping("/report")
public class ReportGateWay extends RootGateWay {

    @Resource
    private SQLDao sqlDao;


    @RequestMapping("/sp_rep_stat_pest_by_year")
    public CommonReturnType sp_rep_stat_pest_by_year() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);
        checkParam(requestJson, Lists.newArrayList(APIDef.startDtKey, APIDef.endDtKey));
        String startDt = requestJson.optString(APIDef.startDtKey);
        String endDt = requestJson.optString(APIDef.endDtKey);
        String areaCode = requestJson.optString("areaCode");
        String grainCode = requestJson.optString("grainCode");
        String pestCode = requestJson.optString("pestCode");
        String province = requestJson.optString("province");
        String city = requestJson.optString("city");
        String role = requestJson.optString("role");
        String depotCode = requestJson.optString("depotCode");
        List list = sqlDao.sp_rep_stat_pest_by_year(startDt, endDt, areaCode, grainCode, pestCode, province, city, role, depotCode);
        CommonReturnType commonReturnType = CommonReturnType.create(list);
        return commonReturnType;
    }

    @RequestMapping("/sp_rep_stat_pest_temperature_by_year")
    public CommonReturnType sp_rep_stat_pest_temperature_by_year() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);
        checkParam(requestJson, Lists.newArrayList(APIDef.startDtKey, APIDef.endDtKey));
        String startDt = requestJson.optString(APIDef.startDtKey);
        String endDt = requestJson.optString(APIDef.endDtKey);
        String areaCode = requestJson.optString("areaCode");
        String grainCode = requestJson.optString("grainCode");
        String pestCode = requestJson.optString("pestCode");
        String province = requestJson.optString("province");
        String city = requestJson.optString("city");
        String role = requestJson.optString("role");
        String depotCode = requestJson.optString("depotCode");
        List list = sqlDao.sp_rep_stat_pest_temperature_by_year(startDt, endDt, areaCode, grainCode, pestCode, province, city, role, depotCode);
        CommonReturnType commonReturnType = CommonReturnType.create(list);
        return commonReturnType;
    }


    @RequestMapping("/sp_rep_stat_pest_humidity_by_year")
    public CommonReturnType sp_rep_stat_pest_humidity_by_year() throws Exception {
        initLimit(TZDLimitType.limit_ifRoot_no, TZDLimitType.limit_type_0);
        JSONObject requestJson = getRequestJson();
        TZDOperator tzdOperator = auth(requestJson);
        checkParam(requestJson, Lists.newArrayList(APIDef.startDtKey, APIDef.endDtKey));
        String startDt = requestJson.optString(APIDef.startDtKey);
        String endDt = requestJson.optString(APIDef.endDtKey);
        String areaCode = requestJson.optString("areaCode");
        String grainCode = requestJson.optString("grainCode");
        String pestCode = requestJson.optString("pestCode");
        String province = requestJson.optString("province");
        String city = requestJson.optString("city");
        String role = requestJson.optString("role");
        String depotCode = requestJson.optString("depotCode");
        List list = sqlDao.sp_rep_stat_pest_humidity_by_year(startDt, endDt, areaCode, grainCode, pestCode, province, city, role, depotCode);
        CommonReturnType commonReturnType = CommonReturnType.create(list);
        return commonReturnType;
    }
}
