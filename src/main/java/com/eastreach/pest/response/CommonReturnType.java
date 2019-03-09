package com.eastreach.pest.response;


import com.eastreach.pest.controller.RootGateWay;

/**
 * 统一返回结果
 **/
public class CommonReturnType {

    //表明对应请求的返回处理结果"success"或"fail"
    private String state;

    //若state=success, 则data内返回前端需要的json数据
    //若state<>success, 则data内使用通用的错误码
    private Object data;


    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, RootGateWay.successKey);
    }

    public static CommonReturnType create(Object result, String state) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setData(result);
        commonReturnType.setState(state);
        return commonReturnType;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
