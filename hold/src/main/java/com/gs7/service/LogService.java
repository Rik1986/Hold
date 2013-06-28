package com.gs7.service;

import com.chinaren.framework.model.pagination.Pager;
import com.gs7.domain.Log;

public interface LogService {

    public void createLog(Log log);

    public void updateLog(Log log);

    // public List<LogView> getPlanLog(long pid);

    public Pager getPlanLog(long pid, Pager pager);

}
