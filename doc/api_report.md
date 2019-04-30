## 报表相关API.
1. /report/sp_rep_stat_pest_by_year
1. /report/sp_rep_stat_pest_temperature_by_year
1. /report/sp_rep_stat_pest_humidity_by_year

### 害虫统计报表.
1. 害虫数量统计,每个害虫一条记录,可选字段可以进行过滤.
1. /report/sp_rep_stat_pest_by_year

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | startDt | 开始时间 | datetime | N |  |  |
    | endDt | 结束时间 | datetime | N |  |  |
    | areaCode | 区域代码 | string | Y |  |  可选过滤|
    | grainCode | 作物代码 | string | Y |  |  |
    | pestCode | 害虫代码 | string | Y |  |  |
    | province | 省份 | string | Y |  |  |
    | city | 城市 | string | Y |  |  |
    | role | 角色 | string | Y |  |  |
    | depotCode | 仓库代码 | string | Y |  |  |

```
{
  "state": "success",
  "data": [
    {
      "m1": 0.0,
      "m2": 0.0,
      "m3": 0.0,
      "m4": 122966.0,
      "m5": 0.0,
      "m6": 0.0,
      "m7": 0.0,
      "m8": 0.0,
      "m9": 0.0,
      "m11": 0.0,
      "m10": 0.0,
      "m12": 0.0,
      "total": 122966.0,
      "pestCode": "麦蛾"
    },
    {
      "m1": 0.0,
      "m2": 0.0,
      "m3": 0.0,
      "m4": 1000.0,
      "m5": 0.0,
      "m6": 0.0,
      "m7": 0.0,
      "m8": 0.0,
      "m9": 0.0,
      "m11": 0.0,
      "m10": 0.0,
      "m12": 0.0,
      "total": 1000.0,
      "pestCode": "大谷盗"
    },
    {
      "m1": 0.0,
      "m2": 0.0,
      "m3": 0.0,
      "m4": 96.0,
      "m5": 0.0,
      "m6": 0.0,
      "m7": 0.0,
      "m8": 0.0,
      "m9": 0.0,
      "m11": 0.0,
      "m10": 0.0,
      "m12": 0.0,
      "total": 96.0,
      "pestCode": "赤拟谷盗"
    }
  ]
}
```