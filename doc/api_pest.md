## 害虫API

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