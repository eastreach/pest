package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TZDAreaDao extends JpaRepository<TZDArea, Integer> {

    @Query("select u from TZDArea u where u.code=:code")
    TZDArea find(@Param("code") String code);
}
