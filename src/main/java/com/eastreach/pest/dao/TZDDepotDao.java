package com.eastreach.pest.dao;

import com.eastreach.pest.model.TZDDepot;

/**
 *
 **/
public interface TZDDepotDao extends RootDao<TZDDepot, Integer> {

    TZDDepot findByCode(String code);
}
