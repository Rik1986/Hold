package com.gs7.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.gs7.dao.LongPairBackendBaseDao;
import com.gs7.dao.SubcategoryUserDao;
import com.gs7.domain.SubcategoryUser;

public class SubcategoryUserDaoImpl extends
		LongPairBackendBaseDao<SubcategoryUser> implements SubcategoryUserDao {

	@Override
	protected void init() {
		this.COLUMNS = " id,subcategoryId,userId,status,ctime,utime ";
		this.INSERT_BEAN_COLUMNS = " :id,:subcategoryId,:userId,:status,:ctime,:utime ";
		this.UPDATE_BEAN_COLUMNS = " subcategoryId=:subcategoryId,userId=:userId,status=:status,utime=:utime ";
		this.enableObjectCache = false;
	}

	@Override
	public SubcategoryUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubcategoryUser su = new SubcategoryUser();
		su.setId(rs.getLong("id"));
		su.setSubcategoryId(rs.getLong("subcategoryId"));
		su.setUserId(rs.getLong("userId"));
		su.setStatus(rs.getInt("status"));
		su.setCtime(rs.getLong("ctime"));
		su.setUtime(rs.getLong("utime"));
		return su;
	}

	@Override
	public List<SubcategoryUser> getSubcategoryUsers(long subcategoryId,
			int status) throws DataAccessException {
		List<SubcategoryUser> result = null;
		String sql = String
				.format("select %s from %s where subcategoryId = ? and status=? order by ctime desc",
						this.COLUMNS, this.getTable(subcategoryId));
		try {
			result = this.getJdbcTemplate(subcategoryId).query(sql, this,
					subcategoryId, status);
			if (null == result)
				result = new ArrayList<SubcategoryUser>();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = new ArrayList<SubcategoryUser>();
		}
		return result;
	}

	@Override
	public List<SubcategoryUser> getSubcategoryUsers(long subcategoryId,
			int status, long cursor, int size) throws DataAccessException {
		List<SubcategoryUser> result = null;
		String sql = String
				.format("select %s from %s where subcategoryId = ? and status= ? and ctime < ?  order by ctime desc limit ?",
						this.COLUMNS, this.getTable(subcategoryId));
		try {
			result = this.getJdbcTemplate(subcategoryId).query(sql, this,
					subcategoryId, status, cursor, size);
			if (null == result)
				result = new ArrayList<SubcategoryUser>();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = new ArrayList<SubcategoryUser>();
		}
		return result;
	}

	@Override
	public SubcategoryUser load(long subcategoryId, long userId)
			throws DataAccessException {
		List<SubcategoryUser> list = null;
		String sql = String.format(
				"SELECT %s FROM %s WHERE subcategoryId = ? and userId = ?",
				this.COLUMNS, super.getTable(subcategoryId));
		try {
			list = getJdbcTemplate(subcategoryId).query(sql, this,
					subcategoryId, userId);
			if (list != null && list.size() > 0) {
				SubcategoryUser ret = list.get(0);
				return ret;
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
