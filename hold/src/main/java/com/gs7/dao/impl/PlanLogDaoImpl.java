package com.gs7.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.common.annotation.AopDao;
import com.gs7.dao.AbstractBackendDao;
import com.gs7.dao.PlanLogDao;
import com.gs7.domain.PlanLog;

@AopDao
public class PlanLogDaoImpl extends AbstractBackendDao<PlanLog> implements PlanLogDao {

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public List<PlanLog> getPlanLogByPid(long pid, int status, long cursor, int size) throws DataAccessException {
        List<PlanLog> result = null;
        String sql = String.format(
                "select %s from %s where pid = ? and status= ? and sort < ?  order by sort desc limit ?",
                this.COLUMNS, this.getTable(pid));
        try {
            result = this.getJdbcTemplate(pid).query(sql, this, pid, status, cursor, size);
            if (null == result) {
                result = new ArrayList<PlanLog>();
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            result = new ArrayList<PlanLog>();
        }
        return result;
    }

    @Override
    public PlanLog load(long pid, long lid) {
        List<PlanLog> list = null;
        String sql = String.format("SELECT %s FROM %s WHERE pid = ? and lid = ?", this.COLUMNS, super.getTable(pid));
        try {
            list = getJdbcTemplate(pid).query(sql, this, pid, lid);
            if (list != null && list.size() > 0) {
                PlanLog ret = list.get(0);
                return ret;
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] asdg){
    	System.out.println(System.currentTimeMillis()-(3*24l*60l*60l*1000l));
    	
    }
    
}
