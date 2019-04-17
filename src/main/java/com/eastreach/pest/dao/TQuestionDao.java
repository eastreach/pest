package com.eastreach.pest.dao;

import com.eastreach.pest.model.TQuestion;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 **/
public interface TQuestionDao extends RootDao<TQuestion, Integer>, JpaSpecificationExecutor<TQuestion> {

    TQuestion findByCode(String code);
}
