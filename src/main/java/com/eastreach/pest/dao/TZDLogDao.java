package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TZDLogDao extends JpaRepository<TZDLog, Integer>, JpaSpecificationExecutor<TZDLog> {

    TZDLog findByCode(String code);
}
