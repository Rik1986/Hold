package com.gs7.web.planLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinaren.framework.model.pagination.Pager;
import com.gs7.Constants;
import com.gs7.domain.Log;
import com.gs7.domain.User;
import com.gs7.service.LogService;
import com.gs7.service.PlanService;
import com.gs7.service.ThirdCloudServer;
import com.gs7.service.UserService;
import com.gs7.web.BaseController;
import com.gs7.web.RequestToolKit;
import com.sohu.zh.enums.ProviderEnum;
import com.sohu.zh.model.ThirdUser;
import com.sohu.zh.request.PublishMsgRequest;

@Controller
@RequestMapping("/m/log")
public class LogController extends BaseController {

    Logger logger = LoggerFactory.getLogger(LogController.class);

    @Resource(name = "logService")
    public LogService logService;

    @Resource(name = "planService")
    public PlanService planService;

    @Resource(name = "userService")
    public UserService userService;

    @RequestMapping(value = { "/list" })
    public ModelAndView list(@ModelAttribute("_pager")
    Pager pager, @RequestParam(value = "pid", required = true)
    long pid, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        User user = RequestToolKit.getCurrentUser(request);
        // List<LogView> logs = logService.getPlanLog(pid);
        pager = logService.getPlanLog(pid, pager);
        model.addAllAttributes(pager.toModelAttribute());
        this.successView(model);
        return this.viewNegotiating("", request, response, model);
    }

    @RequestMapping(value = { "/create" })
    public ModelAndView create(@RequestParam(value = "pid", required = true)
    long pid, @RequestParam(value = "date", required = true)
    String date, @RequestParam(value = "success", required = true)
    int success, @RequestParam(value = "content", required = true)
    final String content, @RequestParam(value = "provider", required = true)
    final String[] provider, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        final User user = RequestToolKit.getCurrentUser(request);

        Log log = new Log();
        log.setPid(pid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.setDate(sdf.parse(date).getTime());
        log.setSuccess(success);
        log.setContent(content);
        logService.createLog(log);

        if (provider != null) {

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        List<ThirdUser> thirdUsers = userService.loadAllBindingByUser(user.getId());
                        List<Integer> providers = new ArrayList<Integer>();
                        for (String p : provider) {
                            providers.add(ProviderEnum.valueOf(p).getId());
                        }
                        for (ThirdUser thirdUser : thirdUsers) {
                            if (providers.contains(thirdUser.getProvider())) {
                                PublishMsgRequest publishMsgRequest = new PublishMsgRequest();
                                publishMsgRequest.setAppId(Constants.thirdCloudAppid);
                                publishMsgRequest.setMsg(content);
                                publishMsgRequest.setProvider(ProviderEnum.getProvider(thirdUser.getProvider()));
                                publishMsgRequest.setThirdCloudUserId(thirdUser.getId());
                                ThirdCloudServer.publishMsg(publishMsgRequest);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        this.successView(model);
        return this.viewNegotiating("", request, response, model);
    }
}
