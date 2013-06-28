package com.gs7.dao.impl;

import com.chinaren.framework.common.annotation.AopDao;
import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.UserDao;
import com.gs7.domain.User;

@AopDao
public class UserDaoImpl extends AbstractBackendDao<User> implements UserDao {

    @Override
    protected void init() {
        super.init();
    }

}
