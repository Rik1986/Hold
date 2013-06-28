package com.gs7.dao.impl;

import com.chinaren.framework.common.annotation.AopDao;
import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.PlanDao;
import com.gs7.domain.Plan;

@AopDao
public class PlanDaoImpl extends AbstractBackendDao<Plan> implements PlanDao {

    @Override
    protected void init() {
        super.init();
    }

}
