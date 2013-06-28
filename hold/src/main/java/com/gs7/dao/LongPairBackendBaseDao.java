package com.gs7.dao;

import com.chinaren.framework.core.dao.base.LongPairBaseDao;
import com.chinaren.framework.model.LongBaseObject;
import com.chinaren.framework.model.pk.LongPair;

public class LongPairBackendBaseDao<T extends LongBaseObject<LongPair>> extends
		LongPairBaseDao<T> {

	@Override
	protected void afterLoad(T model) {

	}

	@Override
	protected void afterInsert(T model) {

	}

	@Override
	protected void afterDelete(T model) {

	}

}
