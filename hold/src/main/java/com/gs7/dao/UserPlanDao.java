package com.gs7.dao;

import java.util.List;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.gs7.domain.UserPlan;

public interface UserPlanDao extends ILongDao<UserPlan, Long> {

    public List<UserPlan> getUserPlan(long uid, int status);

    public UserPlan load(long uid, long pid);

}
