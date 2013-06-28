package com.gs7.dao;

import java.util.List;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.gs7.domain.UserBinding;

public interface UserBindingDao extends ILongDao<UserBinding, Long> {

    public List<UserBinding> loadAllBindingByUser(long uid);

    public void deleteBinding(long uid, long thirdId);

}
