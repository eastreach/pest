package com.eastreach.pest.dao;

import com.eastreach.pest.model.TRStatPest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TRStatPestDao extends RootDao<TRStatPest, Integer>, JpaSpecificationExecutor<TRStatPest> {

    TRStatPest findByCode(String code);
}
