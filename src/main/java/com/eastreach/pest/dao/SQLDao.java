package com.eastreach.pest.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 原生SQL,
 * 存储过程.
 **/
@Repository
public class SQLDao {

    @PersistenceContext
    private EntityManager entityManger;


    public Session currentSession() {
        Session session = ((HibernateEntityManager) entityManger).getSession();
        return session;
    }

    public List sp_rep_stat_pest_by_year(String startDt, String endDt, String areaCode, String grainCode, String pestCode, String province, String city, String role, String depotCode) {
        SQLQuery sqlQuery = currentSession().createSQLQuery("{CALL sp_rep_stat_pest_by_year(?,?,?,?,?,?,?,?,?)}");
        sqlQuery.setString(0, startDt);
        sqlQuery.setString(1, endDt);
        sqlQuery.setString(2, areaCode);
        sqlQuery.setString(3, grainCode);
        sqlQuery.setString(4, pestCode);
        sqlQuery.setString(5, province);
        sqlQuery.setString(6, city);
        sqlQuery.setString(7, city);
        sqlQuery.setString(8, city);
        sqlQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        List list = sqlQuery.list();
        return list;
    }

}
