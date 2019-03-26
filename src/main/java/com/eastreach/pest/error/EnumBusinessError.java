package com.eastreach.pest.error;

/**
 *
 **/
public enum EnumBusinessError implements CommonError {

    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),

    //20000开头为鉴权类错误
    AUTH_ERROR(20001, "授权错误"),
    AUTH_NOT_EXIST_ERROR(20002, "授权信息不存在"),
    AUTH_SIGN_ERROR(20003, "签名错误"),

    //30000开头为数据相关错误
    DATA_EXIST_ERROR(30001, "数据已经存在"),
    DATA_NOT_EXIST_ERROR(30002, "数据不存在"),
    ;

    private int errCode;
    private String errMsg;

    EnumBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
