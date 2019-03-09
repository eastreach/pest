package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDPest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TZDPestDao extends JpaRepository<TZDPest, Integer> {

    @Query("select u from TZDPest u where u.code=:code")
    TZDPest find(@Param("code") String code);
}
