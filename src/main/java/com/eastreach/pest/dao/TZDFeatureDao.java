package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TZDFeatureDao extends JpaRepository<TZDFeature, Integer> {

    @Query("select u from TZDFeature u where u.code=:code")
    TZDFeature find(@Param("code") String code);
}
