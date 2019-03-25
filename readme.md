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
1. TRGrainArea: 农作物与储量区域关系.
1. TRGrainPest: 农作物与害虫对应关系.
1. TZDArea: 储量区域.
1. TZDFeature: 气候字典表.
1. TZDGrain: 农作物.
1. TZDOperator: 操作员.
1. TZDPest: 害虫表.


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
1. /publishInfo/update:
1. /publishInfo/delete:
1. /publishInfo/selectPage:
1. /publishInfo/select:
##### 农作物区域关系接口.
1. /grainArea/add:
1. /grainArea/update:
1. /grainArea/delete:
1. /grainArea/selectPage:
1. /grainArea/select:
##### 农作物害虫关系接口.
1. /grainPest/add:
1. /grainPest/update:
1. /grainPest/delete:
1. /grainPest/selectPage:
1. /grainPest/select:
##### 区域字典接口.
1. /area/add:
1. /area/update:
1. /area/delete:
1. /area/selectPage:
1. /area/select:
##### 气候字典接口.
1. /feature/add:
1. /feature/update:
1. /feature/delete:
1. /feature/selectPage:
1. /feature/select:
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





