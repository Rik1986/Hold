package com.gs7.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.MessageDao;
import com.gs7.domain.Message;

public class MessageDaoImpl extends AbstractBackendDao<Message> implements
		MessageDao {

	@Override
	protected void init() {
		this.COLUMNS = " id,userId,content,status,ctime,utime ";
		this.INSERT_BEAN_COLUMNS = " :id,:userId,:content,:status,:ctime,:utime ";
		this.UPDATE_BEAN_COLUMNS = " userId=:userId,content=:content,status=:status,utime=:utime ";
		this.enableObjectCache = false;
	}

	@Override
	public Message mapRow(ResultSet rs, int arg1) throws SQLException {
		Message result = new Message();
		result.setId(rs.getLong("id"));
		result.setCtime(rs.getLong("ctime"));
		result.setUtime(rs.getLong("utime"));
		result.setContent(rs.getString("content"));
		result.setStatus(rs.getInt("status"));
		result.setUserId(rs.getLong("userId"));
		return result;
	}

}
