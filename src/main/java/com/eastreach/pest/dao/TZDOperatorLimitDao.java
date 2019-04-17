package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDOperatorLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TZDOperatorLimitDao extends RootDao<TZDOperatorLimit, Integer>, JpaSpecificationExecutor<TZDOperatorLimit> {

    TZDOperatorLimit findFirstByAccountAndUrl(String account, String url);
}
