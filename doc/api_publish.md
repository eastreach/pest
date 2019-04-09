## 专家信息发布API

### <span id="publishInfo">专家信息发布API</span>
1. /publishInfo/add: 专家发布信息增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | name | 名称 | string | N |  |  |
    | content | 内容 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic |照片 | string | Y |  |  |
    | pics |多张照片 | string | Y |  |  |
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