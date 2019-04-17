package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDGrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TZDGrainDao extends RootDao<TZDGrain, Integer>,JpaSpecificationExecutor<TZDGrain> {

    @Query("select u from TZDGrain u where u.code=:code")
    TZDGrain find(@Param("code") String code);
}
