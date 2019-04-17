package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDPest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TZDPestDao extends RootDao<TZDPest, Integer> , JpaSpecificationExecutor<TZDPest> {

    TZDPest findFirstByCode(String code);
}
