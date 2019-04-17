package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDOperator;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TZDOperatorDao extends RootDao<TZDOperator, Integer>, JpaSpecificationExecutor<TZDOperator> {


//    @Query("select u from TZDOperator u where u.account=:account")
//    TZDOperator find(@Param("account") String account);

    TZDOperator findFirstByAccount(String account);
}
