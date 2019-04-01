package com.eastreach.pest.dao;

import com.eastreach.pest.model.TRGrainPest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TRGrainPestDao extends JpaRepository<TRGrainPest, Integer>, JpaSpecificationExecutor<TRGrainPest> {

    @Query("select u from TRGrainPest u where u.grainCode=:grainCode and u.pestCode=:pestCode")
    TRGrainPest find(@Param("grainCode") String grainCode, @Param("pestCode") String pestCode);

    TRGrainPest findByGrainCodeAndPestCode(String grainCode, String pestCode);
}
