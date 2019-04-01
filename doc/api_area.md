## 区域API

### <span id="area">区域API</span>
1. /area/add: 区域增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | N |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |
    | areaDesc | 主要区域 | string | Y |  |  |
    | featureDesc | 生态特点 | string | Y |  |  |
    | grainDesc | 主要作物 | string | Y |  |  |
1. /area/addBatch: 区域增加-批量.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | tzdAreaList | 区域对象列表 | array | N |  |  |
1. /area/update: 区域修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
    | name | 名称 | string | Y |  |  |
    | memo | 备注 | string | Y |  |  |
    | pic | 图片地址 | string | Y |  |  |
    | pics | 多图片地址 | string | Y |  |  |
    | areaDesc | 主要区域 | string | Y |  |  |
    | featureDesc | 生态特点 | string | Y |  |  |
    | grainDesc | 主要作物 | string | Y |  |  |
1. /area/updateBatch: 区域修改-批量.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | tzdAreaList | 区域对象列表 | array | N |  |  |
1. /area/delete: 区域删除.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
1. /area/deleteBatch: 区域删除-批量.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | tzdAreaList | 区域对象列表 | array | N |  |  |
1. /area/selectPage: 区域查询分页.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | pageSize | 分页大小 | int | N |  |  |
    | currentPage | 当前页面 | int | N |  |索引从0开始  |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
    | memo | 备注 | string | Y |  |模糊匹配  |
    | areaDescLike | 主要区域 | string | Y |  |模糊匹配  |
    | featureDesc | 生态特点 | string | Y |  |模糊匹配  |
    | grainDesc | 主要作物 | string | Y |  |模糊匹配  |
1. /area/select: 区域查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | Y |  |精确匹配  |
    | nameLike | 名称 | string | Y |  |模糊匹配  |
    | memo | 备注 | string | Y |  |模糊匹配  |
    | areaDescLike | 主要区域 | string | Y |  |模糊匹配  |
    | featureDesc | 生态特点 | string | Y |  |模糊匹配  |
    | grainDesc | 主要作物 | string | Y |  |模糊匹配  |