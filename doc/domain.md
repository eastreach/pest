## 对象模型.
### TZDOperator: 操作员.
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
### TZDArea: 区域.
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
### TZDFeature: 气候.
```
private Integer id;
private Integer state = 1;
private String code = "";
private String name = "";
private String memo = "";
private String pic = "";
private String pics = "";
```
### TZDGrain: 农作物.
```
private Integer id;
private Integer state=1;
private String code="";
private String name="";

private String pic="";
private String pics="";
private String memo="";
```
### TZDPest: 害虫.
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
private String pestCode = "";     //害虫代码
private Double pestValue;       //害虫数量
private Double longitude;            //经度
private Double latitude;             //纬度
private Double temperature;         //温度
private Double humidity;            //湿度
private String memo;
private String province;
private String city;
```