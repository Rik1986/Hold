package com.gs7.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.common.annotation.AopDao;
import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.UserPlanDao;
import com.gs7.domain.UserPlan;

@AopDao
public class UserPlanDaoImpl extends AbstractBackendDao<UserPlan> implements UserPlanDao {

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public List<UserPlan> getUserPlan(long uid, int status) {

        List<UserPlan> result = null;
        String sql = String.format("select %s from %s where uid = ? and status= ?   order by ctime desc ",
                this.COLUMNS, this.getTable(uid));
        try {
            result = this.getJdbcTemplate(uid).query(sql, this, uid, status);
            if (null == result) {
                result = new ArrayList<UserPlan>();
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            result = new ArrayList<UserPlan>();
        }
        return result;

    }

    @Override
    public UserPlan load(long uid, long pid) {
        List<UserPlan> list = null;
        String sql = String.format("SELECT %s FROM %s WHERE uid = ? and pid = ?", this.COLUMNS, super.getTable(uid));
        try {
            list = getJdbcTemplate(uid).query(sql, this, uid, pid);
            if (list != null && list.size() > 0) {
                UserPlan ret = list.get(0);
                return ret;
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
