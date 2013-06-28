package com.gs7.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.chinaren.framework.model.pk.LongPair;
import com.gs7.domain.CategorySubcategory;

public interface CategorySubcategoryDao extends
		ILongDao<CategorySubcategory, LongPair> {

	public CategorySubcategory load(long categoryId, long subcategoryId)
			throws DataAccessException;

	public List<CategorySubcategory> getCategorySubcategoriesByCategoryId(
			long categoryId, int status) throws DataAccessException;

	public List<CategorySubcategory> getCategorySubcategoriesByCategoryId(
			long categoryId, int status, long cursor, int size)
			throws DataAccessException;

}
