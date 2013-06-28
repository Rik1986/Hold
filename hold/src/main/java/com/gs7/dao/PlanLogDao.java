package com.gs7.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.chinaren.framework.core.dao.base.ILongDao;
import com.gs7.domain.PlanLog;

public interface PlanLogDao extends ILongDao<PlanLog, Long> {
    public List<PlanLog> getPlanLogByPid(long pid, int status, long cursor, int size) throws DataAccessException;

    public PlanLog load(long pid, long lid);
}
