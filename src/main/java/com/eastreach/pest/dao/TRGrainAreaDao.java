package com.eastreach.pest.dao;

import com.eastreach.pest.model.TRGrainArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TRGrainAreaDao extends RootDao<TRGrainArea, Integer>, JpaSpecificationExecutor<TRGrainArea> {

    @Query("select u from TRGrainArea u where u.grainCode=:grainCode and u.areaCode=:areaCode")
    TRGrainArea find(@Param("grainCode") String grainCode, @Param("areaCode") String areaCode);

    TRGrainArea findByGrainCodeAndAreaCode(String grainCode, String areaCode);
}
