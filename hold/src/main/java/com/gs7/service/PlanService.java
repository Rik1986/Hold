package com.gs7.service;

import java.util.List;

import com.gs7.domain.Plan;

public interface PlanService {

    public void createPlan(Plan plan);

    public Plan loadPlan(long pid);

    public void updatePlan(Plan plan);

    public List<Plan> getUserPlan(long uid, int[] status);

}
