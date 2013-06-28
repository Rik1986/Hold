package com.gs7.web.plan;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gs7.domain.Plan;
import com.gs7.domain.User;
import com.gs7.service.PlanService;
import com.gs7.web.BaseController;
import com.gs7.web.RequestToolKit;

@Controller
@RequestMapping("/m/plan")
public class PlanController extends BaseController {

    Logger logger = LoggerFactory.getLogger(PlanController.class);

    @Resource(name = "planService")
    public PlanService planService;

    @RequestMapping(value = { "/createPlan" })
    public ModelAndView createPlan(@RequestParam(value = "cid", required = true)
    long cid, @RequestParam(value = "title", required = true)
    String title, @RequestParam(value = "desc", required = false)
    String desc, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        User user = RequestToolKit.getCurrentUser(request);
        Plan plan = new Plan();
        plan.setSubcategoryId(cid);
        plan.setTitle(title);
        plan.setDesc(desc);
        planService.createPlan(plan);
        this.successView(model);
        return this.viewNegotiating("", request, response, model);
    }

    @RequestMapping(value = { "/list" })
    public ModelAndView list(@RequestParam(value = "uid", required = false)
    long uid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        User user = RequestToolKit.getCurrentUser(request);
        List<Plan> plans = null;
        if (uid == 0 || uid == user.getId()) {
            plans = planService.getUserPlan(user.getId(), new int[] { Plan.status_nomorl, Plan.status_private });
        } else {
            plans = planService.getUserPlan(uid, new int[] { Plan.status_nomorl });
        }
        model.addAttribute("result", plans);
        this.successView(model);
        return this.viewNegotiating("", request, response, model);
    }

    @RequestMapping(value = { "/update" })
    public ModelAndView update(@RequestParam(value = "status", required = true)
    int status, @RequestParam(value = "pid", required = true)
    long pid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        User user = RequestToolKit.getCurrentUser(request);
        Plan plan = planService.loadPlan(pid);
        plan.setStatus(status);
        planService.updatePlan(plan);
        this.successView(model);
        return this.viewNegotiating("", request, response, model);
    }

    /**
     * 评论别人的计划
     * 
     * 0 good 1 bad
     * 
     * @param type
     * @param pid
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = { "/comment" })
    public ModelAndView comment(@RequestParam(value = "type", required = true)
    int type, @RequestParam(value = "pid", required = true)
    long pid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        User user = RequestToolKit.getCurrentUser(request);
        Plan plan = planService.loadPlan(pid);
        if (type == 0) {
            plan.setGoodNum(plan.getGoodNum() + 1);
        } else {
            plan.setBadNum(plan.getBadNum() + 1);
        }
        planService.updatePlan(plan);
        this.successView(model);
        return this.viewNegotiating("", request, response, model);
    }
}
