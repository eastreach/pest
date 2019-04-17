package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TZDParamDao extends RootDao<TZDParam, Integer>, JpaSpecificationExecutor<TZDParam> {

    TZDParam findByCode(String code);
}
