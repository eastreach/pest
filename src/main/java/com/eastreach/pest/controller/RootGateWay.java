package com.eastreach.pest.controller;

import com.alibaba.druid.util.StringUtils;
import com.eastreach.pest.dao.*;
import com.eastreach.pest.error.BusinessException;
import com.eastreach.pest.error.EnumBusinessError;
import com.eastreach.pest.model.TZDOperator;
import com.eastreach.pest.model.TZDPest;
import com.eastreach.pest.response.CommonReturnType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.List;
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
    public static final String accountKey = "account";
    public static final String passwordKey = "password";
    public static final String pageSizeKey = "pageSize";
    public static final String currentPageKey = "currentPage";

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
        if (!tzdOperator.getPassword().equals(password)) {
            throw new BusinessException(EnumBusinessError.AUTH_ERROR, "操作员密码错误");
        }
        return tzdOperator;
    }

    /**
     * 特殊
     */
    public boolean auth(TZDOperator tzdOperator, String limitCode) {
        if (tzdOperator.getIfRoot() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取动态where语句
     */
    Specification getWhereClause() {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return null;
            }
        };
    }

    /**
     * 获取分页查询信息
     */
    PageRequest getPageRequest() throws BusinessException {
        checkParam(Lists.newArrayList(pageSizeKey, currentPageKey));
        return new PageRequest(Integer.parseInt(getParam(currentPageKey)), Integer.parseInt(getParam(pageSizeKey)));
    }


}
