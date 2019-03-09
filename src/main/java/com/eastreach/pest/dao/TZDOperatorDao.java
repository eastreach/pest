package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TZDOperatorDao extends JpaRepository<TZDOperator, Integer> {


    @Query("select u from TZDOperator u where u.account=:account")
    TZDOperator find(@Param("account") String account);
}
