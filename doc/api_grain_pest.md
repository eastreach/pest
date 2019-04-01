## 农作物与害虫对应关系API

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