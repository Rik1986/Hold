package com.gs7.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.gs7.dao.LongPairBackendBaseDao;
import com.gs7.dao.SubcategoryMessageDao;
import com.gs7.domain.SubcategoryMessage;

public class SubcategoryMessageDaoImpl extends
		LongPairBackendBaseDao<SubcategoryMessage> implements
		SubcategoryMessageDao {

	@Override
	protected void init() {
		this.COLUMNS = " id,subcategoryId,messageId,status,ctime,utime ";
		this.INSERT_BEAN_COLUMNS = " :id,:subcategoryId,:messageId,:status,:ctime,:utime ";
		this.UPDATE_BEAN_COLUMNS = " subcategoryId=:subcategoryId,messageId=:messageId,status=:status,utime=:utime ";
		this.enableObjectCache = false;
	}

	@Override
	public SubcategoryMessage mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		SubcategoryMessage sm = new SubcategoryMessage();
		sm.setId(rs.getLong("id"));
		sm.setMessageId(rs.getLong("messageId"));
		sm.setSubcategoryId(rs.getLong("subcategoryId"));
		sm.setStatus(rs.getInt("status"));
		sm.setCtime(rs.getLong("ctime"));
		sm.setUtime(rs.getLong("utime"));
		return sm;
	}

	@Override
	public List<SubcategoryMessage> getSubcategoryMessages(long subcategoryId,
			int status) throws DataAccessException {
		List<SubcategoryMessage> result = null;
		String sql = String
				.format("select %s from %s where subcategoryId = ? and status=? order by ctime desc",
						this.COLUMNS, this.getTable(subcategoryId));
		try {
			result = this.getJdbcTemplate(subcategoryId).query(sql, this,
					subcategoryId, status);
			if (null == result)
				result = new ArrayList<SubcategoryMessage>();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = new ArrayList<SubcategoryMessage>();
		}
		return result;
	}

	@Override
	public List<SubcategoryMessage> getSubcategoryMessages(long subcategoryId,
			int status, long cursor, int size) throws DataAccessException {
		List<SubcategoryMessage> result = null;
		String sql = String
				.format("select %s from %s where subcategoryId = ? and status= ? and ctime < ?  order by ctime desc limit ?",
						this.COLUMNS, this.getTable(subcategoryId));
		try {
			result = this.getJdbcTemplate(subcategoryId).query(sql, this,
					subcategoryId, status, cursor, size);
			if (null == result)
				result = new ArrayList<SubcategoryMessage>();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = new ArrayList<SubcategoryMessage>();
		}
		return result;
	}

	@Override
	public SubcategoryMessage load(long subcategoryId, long messageId)
			throws DataAccessException {
		List<SubcategoryMessage> list = null;
		String sql = String.format(
				"SELECT %s FROM %s WHERE subcategoryId = ? and messageId = ?",
				this.COLUMNS, super.getTable(subcategoryId));
		try {
			list = getJdbcTemplate(subcategoryId).query(sql, this,
					subcategoryId, messageId);
			if (list != null && list.size() > 0) {
				SubcategoryMessage ret = list.get(0);
				return ret;
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
