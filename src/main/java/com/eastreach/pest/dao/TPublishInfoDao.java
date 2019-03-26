package com.eastreach.pest.dao;

import com.eastreach.pest.model.TPublishInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 **/
public interface TPublishInfoDao extends JpaRepository<TPublishInfo, Integer>,JpaSpecificationExecutor<TPublishInfo> {

    @Query("select u from TPublishInfo u where u.code=:code")
    TPublishInfo find(@Param("code") String code);

    TPublishInfo findByCode(String code);
}
