## 对象模型.
1. [TZDParam](#TZDParam)  系统参数.
1. [TZDLog](#TZDLog)  系统日志.
1. [TZDUrl](#TZDUrl)  系统资源.
1. [TZDOperator](#TZDOperator)  操作员.
1. [TZDOperatorLimit](#TZDOperatorLimit)  操作员权限设置.
----
1. [TZDArea](#TZDArea)  区域.
1. [TZDDepot](#TZDDepot)  粮仓.
1. [TZDFeature](#TZDFeature)  气候.
1. [TZDGrain](#TZDGrain)  农作物.
1. [TZDPest](#TZDPest)  害虫.


### TZDParam
```
private Integer id;
private Integer state = 1;      //状态,-1禁用,0未审核,1可用,非1不可用.
private String code = "";       //参数代码
private String value = "";       //参数值
private String memo = "";       //备注说明
```
### TZDLog
```
private Integer id;
private String code = UUID.randomUUID().toString();     //流水号
private Date dt = new Date();
private String account = "";    //操作员账号
private String url = "";    //资源访问地址
private String state = "";      //调用结果
private Integer errCode = 0;    //状态码
private String request = "";    //请求内容
private String response = "";   //返回内容
```
### TZDUrl
```
private Integer id;
private Integer state = 1;      //状态,-1禁用,0未审核,1可用,非1不可用.
private String url = "";    //资源地址
private String memo = "";       //备注
private Integer ifRoot = 0;     //是否需要系统管理员权限, 0不需要, 1需要只有管理员可以操作
private Integer limitType = 0;    //权限模式, 0黑名单限权, 1白名单授权
private Integer logLevel = 0;   //日志模式, 0不记录日志, 1记录日志
```
### TZDOperator
```
private Integer id;
private Integer state = 0;      //状态,-1禁用,0未审核,1可用,非1不可用.
private Integer ifRoot = 0;     //是否系统管理员,系统管理员有所有权限.
private String account = "";    //登录账号
private String password = "";   //登录密码
private String role = "";       //操作员角色,权限模型采用简化单个操作员设置,

private String name = "";           //姓名.
private String telephone = "";      //电话
private String place="";            //住址
private String province="";         //省份
private String city="";             //城市
```
### TZDOperatorLimit
```
private Integer id;
private Integer state = 1;      //状态,-1禁用,0未审核,1可用,非1不可用.
private String account = "";        //操作员账号
private String url = "";            //权限代码
private Integer ifLimit = 1;        //是否有权限, 1授权, -1限权
private Integer limitLevel = 0;    //权限等级, 0系统, 1用户
private String memo = "";          //备注
```
### TZDArea
```
private Integer id;
private Integer state = 1;  //状态码, 1可用,非1禁用.
private String code = "";   //代码, 唯一标识
private String name = "";   //区域名称, 青藏高原储量区

private String memo = "";   //备注
private String pic = "";    //图片
private String pics = "";   //多图.
private String areaDesc = "";       //主要地区, 西藏...
private String featureDesc = "";    //生态特点.
private String grainDesc = "";      //主要粮食作物
```
### TZDDepot
```
private Integer id;
private Integer state = 1;  //状态码, 1可用,非1禁用.
private String code = "";   //代码, 唯一标识
private String name = "";   //区域名称, 青藏高原储量区
private String memo = "";   //备注
private String pic = "";    //图片
private String pics = "";   //多图.
private String kind = "";   //类型
private String volumnName = "";     //容量名字
private String grainName = "";      //作物名称
private String storeKind = "";      //存储形式
private Double mountValue = 0.0;     //当前储量
private String grainArea = "";      //粮食产地
private Date inDt;              //入库时间
private Date outDt;             //出口时间
```
### TZDFeature
```
private Integer id;
private Integer state = 1;
private String code = "";
private String name = "";
private String memo = "";
private String pic = "";
private String pics = "";
```
### TZDGrain
```
private Integer id;
private Integer state=1;
private String code="";
private String name="";

private String pic="";
private String pics="";
private String memo="";
```
### TZDPest
```
private Integer id;
private Integer state;
private String code;
private String name;
private String memo;
private String pic;
private String pics;
```
### TRGrainArea: 农作物与区域对应关系.
```
private Integer id;
private Integer state = 1;          //状态码1可用,非1禁用

private String grainCode = "";      //农作物代码
private String grainName = "";      //农作物名称
private String areaCode = "";       //区域代码
private String areaName = "";       //区域名称
private String memo = "";           //备注
```
### TRGrainPest: 农作物与害虫对应关系.
```
private Integer id;
private Integer state = 1;
private String grainCode = "";  //农作物代码
private String grainName = "";  //农作物名称
private String pestCode = "";   //害虫代码
private String pestName = "";   //害虫名称

private String name = "";
private String memo = "";       //备注
```
### TPublishInfo: 专家信息发布.
```
private Integer id;
private Integer state = 1;          //状态

private String code = "";           //发布信息唯一流水号,后台系统生成.
private String name = "";           //发布信息名称title.
private Date createDt = new Date();  //发布时间
private String createOper = "";      //发布操作员工号
private String content = "";         //发布内容
private String memo = "";            //备注
private String pic;
private String pics;
```
### TRStatGrain: 农作物产量统计.
```
private Integer id;
private Integer state = 1;
private String code = UUID.randomUUID().toString();           //发布信息唯一流水号,后台系统生成.

private Date dt;                //统计时间
private Date fromDt;            //统计开始时间
private Date toDt;              //统计结束时间

private String areaCode = "";     //区域代码
private String grainCode = "";    //农作物代码
private Double grainValue;      //农作物产量
private String pic;
private String pics;
```
### TRStatPest: 害虫数量统计.
```
private Integer id;
private Integer state = 1;
private String code = UUID.randomUUID().toString();           //发布信息唯一流水号,后台系统生成.

private Date dt;                //统计时间
private Date fromDt;            //统计开始时间
private Date toDt;              //统计结束时间

private Integer year;             //年份
private Integer month;            //月份
private String areaCode = "";     //区域代码
private String grainCode = "";     //作物代码
private String pestCode = "";     //害虫代码
private Double pestValue;       //害虫数量
private Double longitude;            //经度
private Double latitude;             //纬度
private Double temperature;         //温度
private Double humidity;            //湿度
private String memo;
private String province;
private String city;
private String pic;
private String pics;
```