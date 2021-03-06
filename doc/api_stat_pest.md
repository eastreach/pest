## 害虫数量统计API

### <span id="statPest">害虫数量统计API</span>
1. /statPest/add: 害虫数量统计增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | year | 年份  | int | N |  |  |
    | month | 月份 | int | N |  |  |
    | areaCode | 区域代码 | string | N |  |  |
    | grainCode | 作物代码 | string | N |  |  |
    | pestCode |害虫代码 | string | N |  |  |
    | pestValue |害虫数量 | double | N |  |  |
    | longitude |经度 | double | Y |  |  |
    | latitude |维度 | double | Y |  |  |
    | temperature |维度 | double | Y |  |  |
    | humidity |湿度 | double | Y |  |  |
    | memo |备注 | double | Y |  |  |
    | province |省份 | double | Y |  |  |
    | city |城市 | double | Y |  |  |
    | pic |照片 | string | Y |  |  |
    | pics |多张照片 | string | Y |  |  |
1. /statPest/update: 害虫数量统计修改.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | code | 代码 | string | N |  |  |
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
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | grainCode | 作物代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | province | 省份 | string | Y |  |精确匹配  |
    | city | 城市 | string | Y |  |精确匹配  |
    | role | 角色 | string | Y |  |精确匹配  |
    | depotCode | 仓库代码 | string | Y |  |精确匹配  |
    | dt | 生产日期 | datetime | Y |  |精确匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
1. /statPest/select: 害虫数量统计查询.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | areaCode | 区域代码 | string | Y |  |精确匹配  |
    | grainCode | 作物代码 | string | Y |  |精确匹配  |
    | pestCode | 害虫代码 | string | Y |  |精确匹配  |
    | province | 省份 | string | Y |  |精确匹配  |
    | city | 城市 | string | Y |  |精确匹配  |
    | role | 角色 | string | Y |  |精确匹配  |
    | depotCode | 仓库代码 | string | Y |  |精确匹配  |
    | dt | 生产日期 | datetime | Y |  |精确匹配  |
    | startDt | 开始时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |
    | endDt | 结束时间 | string | Y |  |yyyy-MM-dd HH:mm:ss  |