package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDUrl;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TZDUrlDao extends RootDao<TZDUrl, Integer>, JpaSpecificationExecutor<TZDUrl> {

    TZDUrl findByUrl(String url);
}
