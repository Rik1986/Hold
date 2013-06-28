package com.gs7.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinaren.framework.model.pagination.Pager;
import com.chinaren.framework.model.pagination.PagerUtil;
import com.gs7.dao.LogDao;
import com.gs7.dao.PlanLogDao;
import com.gs7.domain.Log;
import com.gs7.domain.PlanLog;
import com.gs7.service.LogService;
import com.gs7.view.LogView;

@Service("logService")
public class LogServiceImpl implements LogService {
	@Resource(name = "logDao")
	LogDao logDao;

	@Resource(name = "planLogDao")
	PlanLogDao planLogDao;

	@Override
	public void createLog(Log log) {
		long id = logDao.insert(log);

		PlanLog planLog = new PlanLog();
		planLog.setLid(id);
		planLog.setPid(log.getPid());
		planLog.setSort(System.currentTimeMillis());
		planLog.setStatus(PlanLog.status_nomorl);
		planLogDao.update(planLog);
	}

	@Override
	public void updateLog(Log log) {
		logDao.update(log);

		PlanLog planLog = planLogDao.load(log.getPid(), log.getId());
		planLog.setStatus(log.getStatus());
		planLogDao.update(planLog);
	}

	@Override
	public Pager getPlanLog(long pid, Pager pager) {
		
		long cursor = pager.getCursor();
		if (cursor == 0) {
			cursor = Long.MAX_VALUE;
		}
		int size = pager.getNewTotalSize();
		List<PlanLog> planLogs = planLogDao.getPlanLogByPid(pid,
				PlanLog.status_nomorl, cursor, size);
		PagerUtil.newResult2Response(planLogs, pager);
		planLogs = pager.getData();
		List<LogView> logs = new ArrayList<LogView>();
		for (PlanLog planLog : planLogs) {
			Log log = logDao.load(planLog.getLid());
			logs.add(new LogView(log));
		}
		pager.setData(logs);
		return pager;
	}
}
