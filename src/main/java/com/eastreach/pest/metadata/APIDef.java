package com.eastreach.pest.metadata;

/**
 * 接口调用常用元数据
 **/
public class APIDef {

    //系统常用编码
    public static final String rootKey = "root";                    //系统管理员账号
    public static final Integer state_open = 1;                     //int开关-开
    public static final Integer state_close = -1;                   //int开关-关


    //请求元数据
    public static final String tzdOperatorKey = "tzdOperator";      //操作员关键字
    public static final String serviceKey = "service";              //微服务名称
    public static final String actionKey = "action";                //微服务方法
    public static final String nodeKey = "node";                    //微服务节点
    public static final String apiMethodKey = "apiMethod";          //兼容旧版APP.
    public static final String pageSizeKey = "pageSize";            //分页查询页大小
    public static final String currentPageKey = "currentPage";      //分页查询当前页,从0开始
    public static final String requestIdKey = "requestId";          //同步请求ID
    public static final String syncKey = "sync";                    //同步请求ID
    public static final String hotelIdKey = "hotelId";              //酒店ID
    public static final String hotelTokenKey = "hotelToken";        //酒店令牌
    public static final String groupIdKey = "groupId";              //集团ID
    public static final String clientKey = "client";                //站点代码
    public static final String hotelDtKey = "hotelDt";              //酒店日期
    public static final String startDtKey = "startDt";              //开始时间
    public static final String endDtKey = "endDt";                  //结束时间
    public static final String orderIdKey = "orderId";              //订单号


    //开放网关元数据
    public static final String appIdKey = "appId";                  //appId
    public static final String appSecretKey = "appSecret";          //appSecret
    public static final String dataKey = "data";                    //data

    //响应元数据
    public static final String state_key = "state";         //业务逻辑状态字段
    public static final String code_key = "code";           //业务逻辑状态返回状态码
    public static final String msg_key = "msg";             //业务逻辑说明字段
    public static final String state_success = "success";   //业务逻辑成功
    public static final String state_fail = "fail";         //业务逻辑失败
    public static final String total_key = "total";         //总数量
    public static final String rows_key = "rows";           //当前页数据内容


    //其他
    public static final String dateTimeFormatKey = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormatKey = "yyyy-MM-dd";

}
