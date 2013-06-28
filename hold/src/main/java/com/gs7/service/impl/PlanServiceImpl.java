package com.gs7.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gs7.dao.PlanDao;
import com.gs7.dao.SubcategoryUserDao;
import com.gs7.dao.UserPlanDao;
import com.gs7.domain.Plan;
import com.gs7.domain.SubcategoryUser;
import com.gs7.domain.UserPlan;
import com.gs7.service.PlanService;

@Service("planService")
public class PlanServiceImpl implements PlanService {

    @Resource(name = "planDao")
    PlanDao planDao;

    @Resource(name = "userPlanDao")
    UserPlanDao userPlanDao;

    @Resource(name = "subcategoryUserDao")
    SubcategoryUserDao subcategoryUserDao;

    @Override
    public void createPlan(Plan plan) {
        long id = planDao.insert(plan);
        UserPlan userPlan = new UserPlan();
        userPlan.setPid(id);
        userPlan.setSort(System.currentTimeMillis());
        userPlan.setUid(plan.getUid());
        userPlan.setStatus(UserPlan.status_nomorl);
        userPlanDao.update(userPlan);
        SubcategoryUser su = subcategoryUserDao.load(plan.getSubcategoryId(), plan.getUid());
        if (su == null) {
            su = new SubcategoryUser();
            su.setSubcategoryId(plan.getSubcategoryId());
            su.setUserId(plan.getUid());
            subcategoryUserDao.insert(su);
        }
    }

    @Override
    public Plan loadPlan(long pid) {
        return planDao.load(pid);
    }

    @Override
    public void updatePlan(Plan plan) {
        planDao.update(plan);
        UserPlan userPlan = userPlanDao.load(plan.getUid(), plan.getId());
        userPlan.setStatus(plan.getStatus());
        userPlanDao.update(userPlan);

    }

    @Override
    public List<Plan> getUserPlan(long uid, int[] statuses) {
    
        List<UserPlan> all = new ArrayList<UserPlan>();

        for (int status : statuses) {
            List<UserPlan> userPlans = userPlanDao.getUserPlan(uid, status);
            all.addAll(userPlans);
        }
        List<Long> pids = new ArrayList<Long>();
        for (UserPlan userPlan : all) {
            pids.add(userPlan.getPid());
        }
        return planDao.get(pids);
    }
}
