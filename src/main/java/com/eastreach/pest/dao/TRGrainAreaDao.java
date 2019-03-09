package com.eastreach.pest.dao;

import com.eastreach.pest.model.TRGrainArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TRGrainAreaDao extends JpaRepository<TRGrainArea, Integer> {

    @Query("select u from TRGrainArea u where u.grainCode=:grainCode and u.areaCode=:areaCode")
    TRGrainArea find(@Param("grainCode") String grainCode, @Param("areaCode") String areaCode);
}
