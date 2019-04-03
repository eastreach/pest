## 害虫数量统计API

### <span id="statPest">害虫数量统计API</span>
1. /statPest/add: 害虫数量统计增加.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | dt | 生产日期 | datetime | N |  | 可按年,月统计 |
    | fromDt | 生产开始时间 | datetime | N |  |  |
    | toDt | 生产结束时间 | datetime | N |  |  |
    | areaCode | 区域代码 | string | N |  |  |
    | pestCode |害虫代码 | string | N |  |  |
    | pestValue |害虫数量 | double | N |  |  |
    | longitude |经度 | double | N |  |  |
    | latitude |维度 | double | N |  |  |
    | temperature |维度 | double | N |  |  |
    | humidity |湿度 | double | N |  |  |
    | memo |备注 | double | N |  |  |
    | province |省份 | double | N |  |  |
    | city |城市 | double | N |  |  |
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