USE pest;

--操作员数据初始化
IF NOT exists(SELECT *
              FROM TZDOperator
              WHERE account = 'root')
  INSERT INTO TZDOperator (account, password, state, ifRoot, role, name)
  VALUES ('root', 'root', 1, 1, '管理员', '管理员')
GO

--区域数据初始化
IF NOT exists(SELECT *
              FROM TZDArea
              WHERE code = 'qzgy')
  INSERT INTO TZDArea (code, name, state, areaDesc, featureDesc, grainDesc)
  VALUES ('qzgy', '青藏高原储粮区', 1, '西藏,青海,四川部分地区', '高寒干燥', '青稞,春小麦,冬小麦')
GO


--作物数据初始化
IF NOT exists(SELECT *
              FROM TZDGrain
              WHERE code = 'qk')
  INSERT INTO TZDGrain (code, name, state)
  VALUES ('qk', '青稞', 1)
GO


--作物数据初始化
IF NOT exists(SELECT *
              FROM TZDGrain
              WHERE code = 'qk')
  INSERT INTO TZDGrain (code, name, state)
  VALUES ('qk', '青稞', 1)
GO
