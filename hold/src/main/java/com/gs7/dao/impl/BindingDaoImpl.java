package com.gs7.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.common.annotation.AopDao;
import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.BindingDao;
import com.gs7.domain.Binding;
import com.gs7.domain.UserBinding;

@AopDao
public class BindingDaoImpl extends AbstractBackendDao<Binding> implements BindingDao {

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public Binding findUserByThirdId(long thirdId) {
    	List<Binding> result = null;
        String sql = String.format("select %s from %s where thirdId = ?    ",
                this.COLUMNS, this.getTable(thirdId));
        try {
            result = this.getJdbcTemplate(thirdId).query(sql, this, thirdId);
            if (null == result) {
                result = new ArrayList<Binding>();
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            result = new ArrayList<Binding>();
        }
        if(result==null||result.size()==0){
        	return null;
        }
        return result.iterator().next();
    }

    @Override
    public void deleteBinding(long uid, Long thirdId) {
    	 String sql = String.format("delete %s from %s where uid = ? and thirdId = ?",
                 this.COLUMNS, this.getTable(uid));
         try {
            this.getJdbcTemplate(uid).update(sql, uid, thirdId);
         } catch (DataAccessException e) {
            throw e;
         }

    }

}
