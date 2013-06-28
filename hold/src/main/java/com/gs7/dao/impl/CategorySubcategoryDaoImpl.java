package com.gs7.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.gs7.dao.CategorySubcategoryDao;
import com.gs7.dao.LongPairBackendBaseDao;
import com.gs7.domain.CategorySubcategory;

public class CategorySubcategoryDaoImpl extends
		LongPairBackendBaseDao<CategorySubcategory> implements
		CategorySubcategoryDao {

	@Override
	protected void init() {
		this.COLUMNS = " id,categoryId,subcategoryId,sort,status,ctime,utime ";
		this.INSERT_BEAN_COLUMNS = " :id,:categoryId,:subcategoryId,:sort,:status,:ctime,:utime ";
		this.UPDATE_BEAN_COLUMNS = " categoryId=:categoryId,subcategoryId=:subcategoryId,sort=:sort,status=:status,utime=:utime ";
		this.enableObjectCache = false;
	}

	@Override
	public CategorySubcategory mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CategorySubcategory cs = new CategorySubcategory();
		cs.setId(rs.getLong("id"));
		cs.setCategoryId(rs.getLong("categoryId"));
		cs.setSubcategoryId(rs.getLong("subcategoryId"));
		cs.setSort(rs.getLong("sort"));
		cs.setStatus(rs.getInt("status"));
		cs.setCtime(rs.getLong("ctime"));
		cs.setUtime(rs.getLong("utime"));
		return cs;
	}

	@Override
	public List<CategorySubcategory> getCategorySubcategoriesByCategoryId(
			long categoryId, int status) throws DataAccessException {
		List<CategorySubcategory> result = null;
		String sql = String
				.format("select %s from %s where categoryId = ? and status=? order by ctime desc",
						this.COLUMNS, this.getTable(categoryId));
		try {
			result = this.getJdbcTemplate(categoryId).query(sql, this,
					categoryId, status);
			if (null == result)
				result = new ArrayList<CategorySubcategory>();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = new ArrayList<CategorySubcategory>();
		}
		return result;
	}

	@Override
	public List<CategorySubcategory> getCategorySubcategoriesByCategoryId(
			long categoryId, int status, long cursor, int size)
			throws DataAccessException {
		List<CategorySubcategory> result = null;
		String sql = String
				.format("select %s from %s where categoryId = ? and status= ? and ctime < ?  order by ctime desc limit ?",
						this.COLUMNS, this.getTable(categoryId));
		try {
			result = this.getJdbcTemplate(categoryId).query(sql, this,
					categoryId, status, cursor, size);
			if (null == result)
				result = new ArrayList<CategorySubcategory>();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = new ArrayList<CategorySubcategory>();
		}
		return result;
	}

	@Override
	public CategorySubcategory load(long categoryId, long subcategoryId)
			throws DataAccessException {
		List<CategorySubcategory> list = null;
		String sql = String.format(
				"SELECT %s FROM %s WHERE categoryId = ? and subcategoryId = ?",
				this.COLUMNS, super.getTable(categoryId));
		try {
			list = getJdbcTemplate(categoryId).query(sql, this, categoryId,
					subcategoryId);
			if (list != null && list.size() > 0) {
				CategorySubcategory ret = list.get(0);
				return ret;
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
