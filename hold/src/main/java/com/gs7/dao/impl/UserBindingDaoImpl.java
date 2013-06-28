package com.gs7.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.common.annotation.AopDao;
import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.UserBindingDao;
import com.gs7.domain.UserBinding;
import com.gs7.domain.UserPlan;

@AopDao
public class UserBindingDaoImpl extends AbstractBackendDao<UserBinding> implements UserBindingDao {

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public List<UserBinding> loadAllBindingByUser(long uid) {
    	List<UserBinding> result = null;
        String sql = String.format("select %s from %s where uid = ?   order by sort desc ",
                this.COLUMNS, this.getTable(uid));
        try {
            result = this.getJdbcTemplate(uid).query(sql, this, uid);
            if (null == result) {
                result = new ArrayList<UserBinding>();
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            result = new ArrayList<UserBinding>();
        }
        return result;
    }

    @Override
    public void deleteBinding(long uid, long thirdId) {
    	 String sql = String.format("delete %s from %s where uid = ? and thirdId = ?",
                 this.COLUMNS, this.getTable(uid));
         try {
            this.getJdbcTemplate(uid).update(sql, uid, thirdId);
         } catch (DataAccessException e) {
            throw e;
         }
    }

}
