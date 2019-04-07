package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TZDUrlDao extends JpaRepository<TZDUrl, Integer>, JpaSpecificationExecutor<TZDUrl> {

    TZDUrl findByUrl(String url);
}
