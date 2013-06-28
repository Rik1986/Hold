package com.gs7.dao;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.gs7.domain.Subcategory;

public interface SubcategoryDao extends ILongDao<Subcategory, Long> {

	public Subcategory loadByTitle(String title) throws DataAccessException;

}
