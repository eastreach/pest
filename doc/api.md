## API接口.
1. 调用方式: HTTP.
1. 序列化方式: JSON.
1. 字符编码: UTF-8.
1. 授权方式: account + password.

## 错误代码
1. 10001:参数不合法.
1. 10002:系统未知错误.
1. 20001:授权错误.
1. 20003:签名错误.
1. 30001:数据错误,重复数据.
1. 30002:数据错误,不存在数据.

## API服务
1. [操作员API](#operator)
1. [区域API](#area)

### [操作员API]{#operator}

1. /operator/register: 账号注册申请,不需要权限.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| account | 账号 | string | N |  |  |
| password | 密码 | string | N |  |  |
| name | 姓名 | string | N |  |  |

1. /operator/login: 账号登录.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| account | 账号 | string | N |  |  |
| password | 密码 | string | N |  |  |
1. /operator/update: 账号信息修改.
1. /operator/findAll: 查找所有账号,管理员权限.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| account | 账号 | string | N |  |  |
| password | 密码 | string | N |  |  |

### <span id="area">区域API</span>

1. /area/add: 区域增加.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| code | 代码 | string | N |  |  |
| name | 名称 | string | N |  |  |
| memo | 备注 | string | Y |  |  |
| pic | 图片地址 | string | Y |  |  |
| pics | 多图片地址 | string | Y |  |  |
1. /area/update: 区域修改.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| code | 代码 | string | N |  |  |
| name | 名称 | string | Y |  |  |
| memo | 备注 | string | Y |  |  |
| pic | 图片地址 | string | Y |  |  |
| pics | 多图片地址 | string | Y |  |  |
1. /area/delete: 区域删除.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| code | 代码 | string | N |  |  |
1. /area/selectPage: 区域查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /area/select: 区域查询.
### 气候API.
1. /feature/add: 气候新增.
1. /feature/update: 气候修改.
1. /feature/delete: 气候删除.
1. /feature/selectPage: 气候查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /feature/select: 气候查询.
### 农作物API.
1. /grain/add: 农作物增加.
1. /grain/update: 农作物修改.
1. /grain/delete: 农作物删除.
1. /grain/selectPage: 农作物查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /grain/select: 农作物查询.
### 害虫API.
1. /pest/add: 害虫增加.
1. /pest/update: 害虫修改.
1. /pest/delete: 害虫删除.
1. /pest/selectPage: 害虫查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /pest/select: 害虫查询.
### 农作物与区域对应关系API.
1. /grainArea/add: 农作物区域关系增加.
1. /grainArea/update: 农作物区域关系修改.
1. /grainArea/delete: 农作物区域关系删除.
1. /grainArea/selectPage: 农作物区域关系查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /grainArea/select: 农作物区域关系查询.
### 农作物与害虫对应关系API.
1. /grainPest/add: 农作物害虫关系增加.
1. /grainPest/update: 农作物害虫关系修改.
1. /grainPest/delete: 农作物害虫关系删除.
1. /grainPest/selectPage: 农作物害虫关系查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /grainPest/select: 农作物害虫关系查询.
### 专家信息发布API.
1. /publishInfo/add: 专家发布信息增加.
1. /publishInfo/update: 专家发布信息修改.
1. /publishInfo/delete: 专家发布信息删除.
1. /publishInfo/selectPage: 专家发布信息查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /publishInfo/select: 专家发布信息查询.
### 农作物产量统计API.
1. /statGrain/add: 农作物产量统计增加.
1. /statGrain/update: 农作物产量统计修改.
1. /statGrain/delete: 农作物产量统计删除.
1. /statGrain/selectPage: 农作物产量统计查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /statGrain/select: 农作物产量统计查询.
### 害虫数量统计API.
1. /statPest/add: 害虫数量统计增加.
1. /statPest/update: 害虫数量统计修改.
1. /statPest/delete: 害虫数量统计删除.
1. /statPest/selectPage: 害虫数量统计查询分页.

| 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| pageSize | 分页大小 | int | N |  |  |
| currentPage | 当前页面 | int | N |  |索引从0开始  |
1. /statPest/select: 害虫数量统计查询.

### 文件上传API.
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
