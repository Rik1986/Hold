package com.gs7.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.SubcategoryDao;
import com.gs7.domain.Subcategory;

public class SubcategoryDaoImpl extends AbstractBackendDao<Subcategory>
		implements SubcategoryDao {

	@Override
	protected void init() {
		this.COLUMNS = " id,title,descn,ext,ctime,utime ";
		this.INSERT_BEAN_COLUMNS = " :id,:title,:descn,:ext,:ctime,:utime ";
		this.UPDATE_BEAN_COLUMNS = " title=:title,descn=:descn,ext=:ext,utime=:utime ";
		this.enableObjectCache = false;
	}

	@Override
	public Subcategory mapRow(ResultSet rs, int rowNum) throws SQLException {
		Subcategory s = new Subcategory();
		s.setId(rs.getLong("id"));
		s.setTitle(rs.getString("title"));
		s.setDescn(rs.getString("descn"));
		s.setExt(rs.getString("ext"));
		s.setCtime(rs.getLong("ctime"));
		s.setUtime(rs.getLong("utime"));
		return s;
	}

	@Override
	public Subcategory loadByTitle(String title) throws DataAccessException {
		List<Subcategory> list = null;
		String sql = String.format("SELECT %s FROM %s WHERE title = ?",
				this.COLUMNS, super.getTable(0));
		try {
			list = getJdbcTemplate(0).query(sql, this, title);
			if (list != null && list.size() > 0) {
				Subcategory ret = list.get(0);
				return ret;
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
