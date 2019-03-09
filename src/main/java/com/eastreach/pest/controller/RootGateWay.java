package com.eastreach.pest.controller;

import com.alibaba.druid.util.StringUtils;
import com.eastreach.pest.dao.*;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Map;

/**
 * 微服务网关
 **/
@RestController
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class RootGateWay {

    public final Logger logger = LoggerFactory.getLogger(RootGateWay.class);
    public static final String successKey = "success";
    public static final String failKey = "fail";
    public static final String stateKey = "state";
    public static final String errCodeKey = "errCode";
    public static final String errMsgKey = "errMsg";

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    TPublishInfoDao tPublishInfoDao;
    @Autowired
    TRGrainAreaDao trGrainAreaDao;
    @Autowired
    TRGrainPestDao trGrainPestDao;
    @Autowired
    TZDAreaDao tzdAreaDao;
    @Autowired
    TZDFeatureDao tzdFeatureDao;
    @Autowired
    TZDGrainDao tzdGrainDao;
    @Autowired
    TZDOperatorDao tzdOperatorDao;
    @Autowired
    TZDPestDao tzdPestDao;


    /**
     * 异常处理
     * 定义exceptionHandler解决未被controller层吸收的exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(Exception ex) {
        Map<String, Object> responseData = Maps.newConcurrentMap();
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseData.put(errCodeKey, businessException.getErrCode());
            responseData.put(errMsgKey, businessException.getErrMsg());
        } else {
            logger.error(ex.getMessage());
            responseData.put(errCodeKey, EnumBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put(errMsgKey, EnumBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData, failKey);
    }

    /**
     * 获取请求内容
     */
    public String requestBodyContent(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                stringBuffer.append(line);
        } catch (Exception e) { /*report an error*/ }
        return stringBuffer.toString();
    }


    /**
     * 权限验证
     */
    public TZDOperator auth() throws BusinessException {
        String account = httpServletRequest.getParameter("account");
        if (StringUtils.isEmpty(account)) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "参数错误-account");
        }
        String password = httpServletRequest.getParameter("password");
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "参数错误-password");
        }
        TZDOperator tzdOperator = tzdOperatorDao.find(account);
        if (tzdOperator == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "操作员不存在");
        }
        if (tzdOperator.getState() != 1) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "操作员状态错误");
        }
        if (!tzdOperator.getPassword().equals(password)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "操作员密码错误");
        }
        return tzdOperator;
    }


}
