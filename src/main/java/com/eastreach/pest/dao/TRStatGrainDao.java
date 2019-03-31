package com.eastreach.pest.dao;

import com.eastreach.pest.model.TRStatGrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TRStatGrainDao extends JpaRepository<TRStatGrain, Integer>, JpaSpecificationExecutor<TRStatGrain> {

    TRStatGrain findByCode(String code);
}
