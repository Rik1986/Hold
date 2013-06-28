package com.gs7.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinaren.framework.common.util.MD5Utils;
import com.gs7.domain.User;

@Controller
@RequestMapping("/m/account")
public class LoginMobileViewController extends LoginBaseViewController {

    private final String type = "mobile";

    @RequestMapping("/connect")
    @Override
    public ModelAndView thirdLogin(@RequestParam(value = "provider", required = true)
    String provider, @RequestParam(value = "opt", required = false)
    String opt, HttpServletRequest request, HttpServletResponse response, final Model model) throws Exception {
        logger.info("time  thirdLogin uid:{}", getCurrentUid(request));
        return _thirdLogin(provider, type, opt, request, response, model);
    }

    @Override
    @RequestMapping("/afterLogin")
    public ModelAndView afterLogin(@RequestParam(value = "jsonValue", required = true)
    String jsonValue, @RequestParam(value = "provider", required = false)
    String provider, @RequestParam(value = "uid", required = false)
    Long uid, @RequestParam(value = "uidEn", required = false)
    String uidEn, HttpServletRequest request, HttpServletResponse response, final Model model) {
        Map map = (Map) cache.getObject(jsonValue);
        provider = map.get("provider") == null ? null : map.get("provider").toString();
        uid = map.get("uid") == null ? null : Long.parseLong(map.get("uid").toString());
        uidEn = map.get("uidEn") == null ? null : map.get("uidEn").toString();

        for (Object key : map.keySet()) {
            request.setAttribute(key.toString(), map.get(key));
        }
        return _afterLogin(uidEn, provider, type, uid == null ? 0 : uid, true, request.getParameter("id"), request,
                response, model);
    }

    @Override
    protected String write2Response(User orgUser, HttpServletRequest request, HttpServletResponse response, Model model)
            throws Exception {
        String viewName = "redirect:/success";
        long uidl = orgUser.getId();
        long salt = orgUser.getSalt();
        String token = MD5Utils.getMD5Str("" + uidl + salt);
        String uid = Long.toString(uidl);
        model.addAttribute("token", token);
        model.addAttribute("uid", uid);
        return viewName;

    }

    @Override
    protected String getAfterLoginUrl() {
        return "/m" + URL_AFTER_LOGIN;
    }

}
