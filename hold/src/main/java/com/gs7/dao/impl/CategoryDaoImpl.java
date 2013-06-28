package com.gs7.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.CategoryDao;
import com.gs7.domain.Category;

public class CategoryDaoImpl extends AbstractBackendDao<Category> implements
		CategoryDao {

	@Override
	protected void init() {
		this.COLUMNS = " id,title,descn,sort,status,ext,ctime,utime ";
		this.INSERT_BEAN_COLUMNS = " :id,:title,:descn,:sort,:status,:ext,:ctime,:utime ";
		this.UPDATE_BEAN_COLUMNS = " title=:title,descn=:descn,sort=:sort,status=:status,ext=:ext,utime=:utime ";
		this.enableObjectCache = false;
	}

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category c = new Category();
		c.setId(rs.getLong("id"));
		c.setTitle(rs.getString("title"));
		c.setDescn(rs.getString("descn"));
		c.setSort(rs.getLong("sort"));
		c.setStatus(rs.getInt("status"));
		c.setExt(rs.getString("ext"));
		c.setCtime(rs.getLong("ctime"));
		c.setUtime(rs.getLong("utime"));
		return c;
	}

	@Override
	public List<Category> getCategories(int status) throws DataAccessException {
		List<Category> result = null;
		String sql = String.format(
				"select %s from %s where status = ? order by ctime desc",
				this.COLUMNS, this.getTable(0));
		try {
			result = this.getJdbcTemplate(0).query(sql, this, status);
			if (null == result)
				result = new ArrayList<Category>();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = new ArrayList<Category>();
		}
		return result;
	}
}
