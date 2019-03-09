package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDGrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TZDGrainDao extends JpaRepository<TZDGrain, Integer> {

    @Query("select u from TZDGrain u where u.code=:code")
    TZDGrain find(@Param("code") String code);
}
