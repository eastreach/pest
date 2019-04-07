## 系统管理API.
### 系统运行配置对象模型.
1. TZDParam:/param: 系统运行参数配置.
1. TZDLog:/log: 系统日志.
1. TZDUrl:/url: 系统资源,权限模式,日志模式.
1. TZDOperator:/operator: 操作员.
1. TZDOperatorLimit:/operatorLimit: 操作员资源权限配置.

### 系统管理API接口方法划分.
1. add: 增加数据, 与授权字段不冲突时使用单个字段标识,否则使用对象字段.
1. addBatch: 批量添加数据, 参数为对象列表.
1. delete: 删除数据, 与授权字段不冲突时使用单个字段标识,否则使用对象字段.
1. deleteBatch; 批量删除数据, 参数为对象列表
1. update: 修改数据, 与授权字段不冲突时使用单个字段标识,否则使用对象字段.
1. updateBatch: 批量修改数据. 参数为对象列表.
1. select: 查询数据: 可选过滤参数.
1. selectPage: 分页查询数据. 可选过滤参数

### 权限管理模型.
1. 基于资源配置的动态权限管理模型.
1. TZDUrl配置系统资源, ifRoot是否管理员权限, limitType授权模型(白名单)还是限权模型(黑名单).
1. TZDOperatorLimit: 操作员权限设置, 是否有权限, 权限等级(数据隔离机制), 可动态扩展.
1. 系统管理相关默认需要系统管理员权限, 业务相关数据默认放开权限,可进行配置设置.

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
1. /operator/updateSelf: 账号信息修改,管理员权限.

    | 字段名称 | 字段描述 | 类型 | 允许为空 | 长度 | 说明 |
    | :--- | :--- | :--- | :--- | :--- | :--- |
    | account | 账号 | string | N |  |  |
    | password | 密码 | string | N |  |  |
    | newPassword | 新密码 | object | N |  |  |