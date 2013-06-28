package com.gs7.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.chinaren.framework.model.pk.LongPair;
import com.gs7.domain.SubcategoryMessage;

public interface SubcategoryMessageDao extends
		ILongDao<SubcategoryMessage, LongPair> {

	public List<SubcategoryMessage> getSubcategoryMessages(long subcategoryId,
			int status) throws DataAccessException;

	public List<SubcategoryMessage> getSubcategoryMessages(long subcategoryId,
			int status, long cursor, int size) throws DataAccessException;

	public SubcategoryMessage load(long subcategoryId, long messageId)
			throws DataAccessException;

}
