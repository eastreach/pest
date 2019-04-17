package com.eastreach.pest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Repository接口是spring data的核心接口,不提供任何方法.
 * CrudRepository: 继承Repository,实现CRUR相关方法.
 * PagingAndSortingRepository: 实现了分页排序相关方法.
 * JpaRepository: 继承PagingAndSortingRepository, 实现JPA相关方法.
 * @Query注解.
 *
 **/
@NoRepositoryBean
public interface RootDao<T,ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
