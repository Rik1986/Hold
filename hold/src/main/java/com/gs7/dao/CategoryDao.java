package com.gs7.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.gs7.domain.Category;

public interface CategoryDao extends ILongDao<Category, Long> {

	public List<Category> getCategories(int status) throws DataAccessException;

}
