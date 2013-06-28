package com.gs7.dao.impl;

import com.chinaren.framework.common.annotation.AopDao;
import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.LogDao;
import com.gs7.domain.Log;

@AopDao
public class LogDaoImpl extends AbstractBackendDao<Log> implements LogDao {

    @Override
    protected void init() {
        super.init();
    }

}
