package com.eastreach.pest.controller;

import com.alibaba.fastjson.JSON;
import com.eastreach.pest.dao.*;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.model.*;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static final String accountKey = "account";
    public static final String passwordKey = "password";
    public static final String pageSizeKey = "pageSize";
    public static final String currentPageKey = "currentPage";

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    TZDUrlDao tzdUrlDao;
    @Autowired
    TZDLogDao tzdLogDao;
    @Autowired
    TZDParamDao tzdParamDao;
    @Autowired
    TZDOperatorDao tzdOperatorDao;
    @Autowired
    TZDOperatorLimitDao tzdOperatorLimitDao;
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
    TZDPestDao tzdPestDao;
    @Autowired
    TRStatGrainDao trStatGrainDao;
    @Autowired
    TRStatPestDao trStatPestDao;


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
            responseData.put(errMsgKey, ex.getMessage());
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
     * 非空参数校验
     */
    public void checkParam(List<String> paramList) throws BusinessException {
        for (String param : paramList) {
            if (httpServletRequest.getParameter(param) == null) {
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "参数不能为空-" + param);
            }
        }
    }

    /**
     * 获取请求参数值
     */
    public String getParam(String param) {
        String value = httpServletRequest.getParameter(param);
        return value;
    }

    /**
     * 资源权限初始化
     */
    public void initLimit(Integer ifRoot, Integer limitType) {
        String url = httpServletRequest.getRequestURI();
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(url);
        if (tzdUrl == null) {
            tzdUrl = new TZDUrl();
            tzdUrl.setUrl(url);
            tzdUrl.setIfRoot(ifRoot);
            tzdUrl.setLimitType(limitType);
            tzdUrlDao.save(tzdUrl);
        }
    }

    /**
     * 系统参数初始化
     */
    public TZDParam initParam(TZDParam tzdParam) {
        tzdParam.setId(null);
        TZDParam tzdParam1 = tzdParamDao.findByCode(tzdParam.getCode());
        if (tzdParam1 != null) {
            return tzdParam1;
        }
        tzdParamDao.save(tzdParam);
        return tzdParam;
    }

    /**
     * 日志记录
     */
    public void log(TZDOperator tzdOperator, CommonReturnType commonReturnType) {
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(httpServletRequest.getRequestURI());
        if (tzdUrl == null || tzdUrl.getState() != 1 || tzdUrl.getLogLevel() != 1) {
            return;
        }
        TZDLog tzdLog = new TZDLog();
        try {
            tzdLog.setAccount(tzdOperator.getAccount());
            tzdLog.setUrl(httpServletRequest.getRequestURI());
            tzdLog.setState(commonReturnType.getState());
            tzdLog.setResponse(commonReturnType.toString());
            tzdLogDao.save(tzdLog);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error(JSON.toJSONString(tzdLog));
        }
    }

    /**
     * 公共权限验证
     */
    public TZDOperator auth() throws BusinessException {
        checkParam(Lists.newArrayList(accountKey, passwordKey));
        String account = httpServletRequest.getParameter("account");
        String password = httpServletRequest.getParameter("password");
        TZDOperator tzdOperator = tzdOperatorDao.find(account);
        if (tzdOperator == null) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "操作员不存在");
        }
        if (tzdOperator.getState() != 1) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "操作员状态错误");
        }
        if (!tzdOperator.getPassword().equalsIgnoreCase(password)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "操作员密码错误");
        }
        //系统管理员不校验权限
        if (tzdOperator.getIfRoot() == 1) {
            return tzdOperator;
        }
        //资源不设置权限
        TZDUrl tzdUrl = tzdUrlDao.findByUrl(httpServletRequest.getRequestURI());
        if (tzdUrl == null || tzdUrl.getState() != 1) {
            return tzdOperator;
        }
        if (tzdUrl.getIfRoot() == 1) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "需要系统管理员权限");
        }
        //操作员权限判断
        TZDOperatorLimit tzdOperatorLimit = tzdOperatorLimitDao.findFirstByAccountAndUrl(tzdOperator.getAccount(), tzdUrl.getUrl());
        //黑名单限权
        if (tzdUrl.getLimitType() == 0) {
            if (tzdOperatorLimit != null && tzdOperatorLimit.getState() == 1 && tzdOperatorLimit.getIfLimit() == -1) {
                throw new BusinessException(EnumBusinessError.AUTH_ERROR, "黑名单已限权-" + tzdUrl.getUrl());
            }
        }
        //白名单授权
        if (tzdUrl.getLimitType() == 1) {
            if (tzdOperatorLimit == null || tzdOperatorLimit.getState() != 1 || tzdOperatorLimit.getIfLimit() != 1) {
                throw new BusinessException(EnumBusinessError.AUTH_ERROR, "白名单未授权-" + tzdUrl.getUrl());
            }
        }
        return tzdOperator;
    }

    /**
     * 获取分页查询信息
     */
    PageRequest getPageRequest() throws BusinessException {
        checkParam(Lists.newArrayList(pageSizeKey, currentPageKey));
        return new PageRequest(Integer.parseInt(getParam(currentPageKey)), Integer.parseInt(getParam(pageSizeKey)));
    }

    /**
     * 自动更新领域对象的属性
     * @param destination 领域对象
     * @param excludes    排除字段
     */
    public void setDomainProperty(Object destination, Set<String> excludes) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo destinationBean = Introspector.getBeanInfo(destination.getClass(), Object.class);
        PropertyDescriptor[] destinationProperties = destinationBean.getPropertyDescriptors();
        for (int j = 0; j < destinationProperties.length; j++) {
            String fieldName = destinationProperties[j].getName();
            if (!StringUtils.isEmpty(getParam(fieldName)) && !excludes.contains(fieldName)) {
                destinationProperties[j].getWriteMethod().invoke(destination, getParam(fieldName));
            }
        }
    }


}
