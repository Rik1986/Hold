package com.gs7.dao;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.gs7.domain.Binding;

public interface BindingDao extends ILongDao<Binding, Long> {

    public Binding findUserByThirdId(long thirdId);

    public void deleteBinding(long uid, Long id);

}
