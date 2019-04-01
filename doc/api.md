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
1. [气候API](#feature)
1. [农作物API](#grain)
1. [害虫API](#pest)
1. [农作物与区域对应关系API](#grainArea)
1. [农作物与害虫对应关系API](#grainPest)
1. [专家信息发布API](#publishInfo)
1. [农作物产量统计API](#statGrain)
1. [害虫数量统计API](#statPest)
1. [文件上传API](#upload)

### <span id="operator">操作员API</span>
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
1. /operator/update: 账号信息修改,管理员权限.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | account | 账号 | string | N |  |  |
    | password | 密码 | string | N |  |  |
    | oldOperator | 原操作员对象 | object | N |  |  |
    | newOperator | 新操作员对象 | object | N |  |  |
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
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
1. /area/select: 区域查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
### <span id="feature">气候API</span>
1. /feature/add: 气候新增.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |

1. /feature/update: 气候修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | Y |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |
1. /feature/delete: 气候删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
1. /feature/selectPage: 气候查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
1. /feature/select: 气候查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
### <span id="grain">农作物API</span>
1. /grain/add: 农作物增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |
1. /grain/update: 农作物修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | Y |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |
1. /grain/delete: 农作物删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
1. /grain/selectPage: 农作物查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
1. /grain/select: 农作物查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
### <span id="pest">害虫API</span>
1. /pest/add: 害虫增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |
1. /pest/update: 害虫修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | Y |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |
1. /pest/delete: 害虫删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
1. /pest/selectPage: 害虫查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
1. /pest/select: 害虫查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
### <span id="grainArea">农作物与区域对应关系API</span>
1. /grainArea/add: 农作物区域关系增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | N |  |  |
    | areaCode | 区域代码 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
1. /grainArea/update: 农作物区域关系修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | N |  |  |
    | areaCode | 区域代码 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
1. /grainArea/delete: 农作物区域关系删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | N |  |  |
    | areaCode | 区域代码 | string | N |  |  |
1. /grainArea/selectPage: 农作物区域关系查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | grainCode | 农作物代码 | string | Y |  |精确匹配  |
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | memo | 备注 | string | Y |  |模糊匹配  |
1. /grainArea/select: 农作物区域关系查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | Y |  |精确匹配  |
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | memo | 备注 | string | Y |  |模糊匹配  |
### <span id="grainPest">农作物与害虫对应关系API</span>
1. /grainPest/add: 农作物害虫关系增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | memo | 备注 | string | Y |  |  |
1. /grainPest/update: 农作物害虫关系修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | memo | 备注 | string | Y |  |  |
1. /grainPest/delete: 农作物害虫关系删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
1. /grainPest/selectPage: 农作物害虫关系查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | grainCode | 农作物代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | memo | 备注 | string | Y |  | 模糊匹配 |
1. /grainPest/select: 农作物害虫关系查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | grainCode | 农作物代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | memo | 备注 | string | Y |  | 模糊匹配 |
### <span id="publishInfo">专家信息发布API</span>
1. /publishInfo/add: 专家发布信息增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | name | 名称 | string | N |  |  |
    | content | 内容 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
1. /publishInfo/update: 专家发布信息修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | Y |  |  |
    | memo | 备注 | string | Y |  |  |
1. /publishInfo/delete: 专家发布信息删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
1. /publishInfo/selectPage: 专家发布信息查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | code | 代码 | string | Y |  |精确匹配  |
    | createOper | 创建操作员 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
    | contentLike | 内容 | string | Y |  |模糊匹配  |
    | memo | 备注 | string | Y |  |模糊匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
1. /publishInfo/select: 专家发布信息查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | createOper | 创建操作员 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
    | contentLike | 内容 | string | Y |  |模糊匹配  |
    | memo | 备注 | string | Y |  |模糊匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
### <span id="statGrain">农作物产量统计API</span>
1. /statGrain/add: 农作物产量统计增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | dt | 生产日期 | datetime | N |  | 可按年,月统计 |
    | fromDt | 生产开始时间 | datetime | N |  |  |
    | toDt | 生产结束时间 | datetime | N |  |  |
    | areaCode | 区域代码 | string | N |  |  |
    | grainCode |农作物代码 | string | N |  |  |
    | grainValue |农作物产量 | double | N |  |  |
1. /statGrain/update: 农作物产量统计修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | dt | 生产日期 | datetime | Y |  | 可按年,月统计 |
    | fromDt | 生产开始时间 | datetime | Y |  |  |
    | toDt | 生产结束时间 | datetime | Y |  |  |
    | areaCode | 区域代码 | string | Y |  |  |
    | grainCode |农作物代码 | string | Y |  |  |
    | grainValue |农作物产量 | double | Y |  |  |

1. /statGrain/delete: 农作物产量统计删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
1. /statGrain/selectPage: 农作物产量统计查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | code | 代码 | string | Y |  |精确匹配  |
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | grainCode | 作物代码 | string | Y |  |精确匹配  |
    | dt | 生产日期 | datetime | Y |  |精确匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
1. /statGrain/select: 农作物产量统计查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | grainCode | 作物代码 | string | Y |  |精确匹配  |
    | dt | 生产日期 | datetime | Y |  |精确匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |

### <span id="statPest">害虫数量统计API</span>
1. /statPest/add: 害虫数量统计增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | dt | 生产日期 | datetime | N |  | 可按年,月统计 |
    | fromDt | 生产开始时间 | datetime | N |  |  |
    | toDt | 生产结束时间 | datetime | N |  |  |
    | areaCode | 区域代码 | string | N |  |  |
    | pestCode |害虫代码 | string | N |  |  |
    | grainValue |农作物产量 | double | N |  |  |
1. /statPest/update: 害虫数量统计修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | dt | 生产日期 | datetime | Y |  | 可按年,月统计 |
    | fromDt | 生产开始时间 | datetime | Y |  |  |
    | toDt | 生产结束时间 | datetime | Y |  |  |
    | areaCode | 区域代码 | string | Y |  |  |
    | pestCode |害虫代码 | string | Y |  |  |
    | grainValue |农作物产量 | double | Y |  |  |
1. /statPest/delete: 害虫数量统计删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
1. /statPest/selectPage: 害虫数量统计查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | code | 代码 | string | Y |  |精确匹配  |
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | dt | 生产日期 | datetime | Y |  |精确匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
1. /statPest/select: 害虫数量统计查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | dt | 生产日期 | datetime | Y |  |精确匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |

### <span id="upload">文件上传API</span>
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
