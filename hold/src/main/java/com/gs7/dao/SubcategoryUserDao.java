package com.gs7.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.chinaren.framework.model.pk.LongPair;
import com.gs7.domain.SubcategoryUser;

public interface SubcategoryUserDao extends ILongDao<SubcategoryUser, LongPair> {

	public List<SubcategoryUser> getSubcategoryUsers(long subcategoryId,
			int status) throws DataAccessException;

	public List<SubcategoryUser> getSubcategoryUsers(long subcategoryId,
			int status, long cursor, int size) throws DataAccessException;

	public SubcategoryUser load(long subcategoryId, long userId)
			throws DataAccessException;
}
