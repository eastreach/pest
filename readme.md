## pest项目(1.0)
1. 数据库: SqlServer.
1. 后台开发语言: java
1. 使用框架: springBoot.
1. 接口协议: REST.
1. 应用终端: ios.
1. 终端开发语言: OC.

#### 测试部署.
1. 接口地址: http://113.31.21.52
1. 管理员账号: root+root.
1. http://113.31.21.52/operator/login?account=root&password=root


#### 数据库表结构设计.
1. TPublishInfo: 专家发布信息.
 ```
 private Integer id;
 private Integer state = 1;
 private String code = "";
 private String name = "";
 private Date createDt = new Date();
 private String createOper = "";
 private String content = "";
 private String memo = "";
 ```
1. TRGrainArea: 农作物与储量区域关系.
```
private Integer id;
private Integer state = 1;
private String grainCode = "";
private String areaCode = "";
private String memo = "";
```
1. TRGrainPest: 农作物与害虫对应关系.
```
private Integer id;
private Integer state = 1;
private String grainCode = "";
private String pestCode = "";
private String name = "";
private String memo = "";
```
1. TZDArea: 储量区域.
```
private Integer id;
private Integer state = 1;
private String code = "";
private String name = "";
private String memo = "";
private String pic = "";
private String pics = "";
```
1. TZDFeature: 气候字典表.
```
private Integer id;
private Integer state = 1;
private String code = "";
private String name = "";
private String memo = "";
private String pic = "";
private String pics = "";
```
1. TZDGrain: 农作物.
```
private Integer id;
private Integer state=1;
private String code="";
private String name="";
private String pic="";
private String pics="";
private String memo="";
```
1. TZDOperator: 操作员.
```
private Integer id;
private Integer state = 0;
private Integer ifRoot = 0;     //是否管理员
private String account = "";
private String password = "";
private String role = "";
private String name = "";
private String telephone = "";
private String place="";
private String province="";
private String city="";
```
1. TZDPest: 害虫表.
```
private Integer id;
private Integer state = 1;
private String code = "";
private String name = "";
private String memo = "";
private String pic = "";
private String pics = "";
```


#### 接口地址.
##### 账号接口
1. /operator/register: 账号注册
    1. account: 账号.
    1. password: 密码.
    1. name: 名称.
1. /operator/login: 账号登录.
     1. account: 账号.
     1. password: 密码.
1. /operator/update: 账号信息修改.
1. /operator/findAll: 账号信息查询.
##### 专家信息发布接口.
1. /publishInfo/add:
    1. code: 编号
1. /publishInfo/update:
    1. code: 编号
1. /publishInfo/delete:
    1. code: 编号
1. /publishInfo/selectPage:
    1. pageSize: 分页大小.
    1. currentPage: 当前页.
    1. startDt: 过滤开始时间-包含.
    1. endDt: 过滤结束时间-包含.
1. /publishInfo/select:
    1. ...过滤查询字段.
##### 农作物区域关系接口.
1. /grainArea/add:
    1. code:
    1. name:
1. /grainArea/update:
    1. code.
1. /grainArea/delete:
    1. code.
1. /grainArea/selectPage:
    1. pageSize: 分页大小.
    1. currentPage: 当前页.
    1. ...可选过滤字段
1. /grainArea/select:
    1. ...可选过滤字段.
##### 农作物害虫关系接口.
1. /grainPest/add:
     1. code:
     1. name:
1. /grainPest/update:
    1. code:
1. /grainPest/delete:
    1. code:
1. /grainPest/selectPage:
    1. pageSize: 分页大小.
    1. currentPage: 当前页.
    1. ...可选过滤字段
1. /grainPest/select:
    1. ...可选过滤字段
##### 区域字典接口.
1. /area/add:
    1. code.
    1. name.
1. /area/update:
    1. code.
1. /area/delete:
    1. code.
1. /area/selectPage:
    1. pageSize: 分页大小.
    1. currentPage: 当前页.
    1. ...可选过滤字段
1. /area/select:
    1. ...可选过滤字段
##### 气候字典接口.
1. /feature/add:
    1. code.
    1. name.
1. /feature/update:
    1. code.
1. /feature/delete:
    1. code
1. /feature/selectPage:
    1. pageSize: 分页大小.
    1. currentPage: 当前页.
    1. ...可选过滤字段
1. /feature/select:
    1. ...可选过滤字段
##### 农作物接口.
1. /grain/add:
1. /grain/update.
1. /grain/delete.
1. /grain/selectPage.
1. /grain/select.
#### 害虫接口.
1. /pest/add.
1. /pest/update.
1. /pest/delete.
1. /pest/selectPage.
1. /pest/select.
#### 文件上传接口.
1. /upload.
1. Headers.
    Content-Type: application/x-www-form-urlencoded
1. Body.
    file: binFile.
1. 响应结果
    {
        "state":"success",
        "data":"http://192.168.3.130/a.jpg"
    }




#### 错误代码
1. 10001:参数不合法.
1. 10002:系统未知错误.
1. 20001:授权错误.
1. 20003:签名错误.
1. 30001:数据错误,重复数据.
1. 30002:数据错误,不存在数据.

