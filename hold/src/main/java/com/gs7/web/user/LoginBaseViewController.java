package com.gs7.web.user;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinaren.framework.common.mail.SendErrorMailUtil;
import com.chinaren.framework.common.util.InnerCache;
import com.chinaren.framework.common.util.MD5Utils;
import com.chinaren.framework.common.util.RequestUtils;
import com.gs7.Constants;
import com.gs7.domain.User;
import com.gs7.service.UserService;
import com.gs7.web.BaseController;
import com.gs7.web.RequestToolKit;
import com.sohu.zh.enums.ProviderEnum;

/**
 * 
 * @author yongbiaoli
 * 
 */
public abstract class LoginBaseViewController extends BaseController {

    Logger logger = LoggerFactory.getLogger(LoginBaseViewController.class);

    @Resource(name = "userService")
    protected UserService userService;

    protected InnerCache cache = InnerCache.getInnerCache();

    protected final static String URL_AFTER_LOGIN = "/account/afterLogin";

    protected static final int cookieTime = 60 * 60 * 24 * 14;

    /**
     * 
     * @param provider
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    public abstract ModelAndView thirdLogin(@RequestParam(value = "provider", required = true)
    String provider, @RequestParam(value = "opt", required = false, defaultValue = "close")
    String opt, HttpServletRequest request, HttpServletResponse response, final Model model) throws Exception;

    protected ModelAndView _thirdLogin(String provider, String type, String opt, HttpServletRequest request,
            HttpServletResponse response, final Model model) throws Exception {
        // 所有第三方登陆/绑定的中转视图
        String ru = "";
        User currentUser = RequestToolKit.getCurrentUser(request);
        // 拼参数
        Map<String, String> formparms = new HashMap<String, String>();
        if (currentUser != null) {
            formparms.put("uid", Long.toString(currentUser.getId()));
            // 加密
            formparms.put("uidEn", MD5Utils.getMD5Str("" + currentUser.getId() + currentUser.getSalt()));
        }
        if (provider.equals("t.qq")) {
            provider = "tqq";
        }
        formparms.put("provider", provider);
        formparms.put("type", type);
        formparms.put("opt", opt);
        formparms.put("version", RequestToolKit.getVersion(request));
        // 获取最终的ru
        Map<String, Object> map = new HashMap<String, Object>();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        cache.setObject(uuid, 60 * 60 * 24, formparms);
        map.put("jsonValue", uuid);
        ru = this.getReidrectUrl(request, provider, map);
        model.addAttribute("appId", Constants.thirdCloudAppid);
        model.addAttribute("provider", provider);
        model.addAttribute("type", type);
        model.addAttribute("forceLogin", true);
        model.addAttribute("reUrl", ru);

        logger.info("time  thirdLoginEnd uid:{}", getCurrentUid(request));
        return new ModelAndView("redirect:" + Constants.thirdCloudUrl);
    }

    private String getReidrectUrl(HttpServletRequest request, String provider, Map<String, Object> formparms)
            throws Exception {
        String ru = RequestUtils.addParams(RequestToolKit.getFullUrl(request, getAfterLoginUrl()), formparms);
        ru = URLEncoder.encode(ru, "utf-8");
        return ru;
    }

    protected String getAfterLoginUrl() {
        return URL_AFTER_LOGIN;
    }

    public abstract ModelAndView afterLogin(@RequestParam(value = "jsonValue", required = false)
    String jsonValue, @RequestParam(value = "provider", required = false)
    String provider, @RequestParam(value = "uid", required = false)
    Long uid, @RequestParam(value = "uidEn", required = false)
    String uidEn, HttpServletRequest request, HttpServletResponse response, final Model model);

    public ModelAndView _afterLogin(String uidEn, String provider, String type, long orgUserId, boolean checkUidEn,
            String thirdIdStr, final HttpServletRequest request, HttpServletResponse response, final Model model) {
        try {
            ProviderEnum providerEnum = ProviderEnum.valueOf(provider);

            // 在third-cloud就已经出现错误
            String errorMsg = request.getParameter("errorMsg");
            if (StringUtils.isNotBlank(errorMsg) && StringUtils.isBlank(request.getParameter("id"))) {
                String snapMsg = "third-cloud返回错误:,uid:" + orgUserId + ",request:"
                        + RequestToolKit.getCompleteUrl(request);
                model.addAttribute("errorMsg", providerEnum.getCname() + "获取用户信息失败");
                return commonError(true, snapMsg, request, response, model);
            }

            // 验证参数
            if (StringUtils.isBlank(thirdIdStr)) {
                String snapMsg = "third-cloud返回参数错误,uid:" + orgUserId + ",request:"
                        + RequestToolKit.getCompleteUrl(request);
                return commonError(true, snapMsg, request, response, model);
            }

            boolean check = false;
            // thirdID
            long thirdId = Integer.parseInt(thirdIdStr);

            String sr = "";

            //
            synchronized (this) {
                // 登陆
                User userLoadBythirdId = userService.findUserByThirdId(thirdId);
                if (userLoadBythirdId != null) {
                    String viewName = this.write2Response(userLoadBythirdId, request, response, model);
                    return this.viewNegotiating(viewName, request, response, model);
                }
                // 注册
                if (orgUserId == 0) {

                    User newUser = userService.createByThirdId(super.getRemoteIp(request), thirdId);
                    String viewName = this.write2Response(newUser, request, response, model);
                    return this.viewNegotiating(viewName, request, response, model);
                }
                // 绑定
                {
                    User user = null;
                    user = this.userService.getUser(orgUserId);

                    if (StringUtils.isNotBlank(uidEn) && checkUidEn) {

                        if (MD5Utils.getMD5Str("" + user.getId() + user.getSalt()).equals(uidEn)) {

                            check = true;

                        }
                        if (!check) {
                            String snapMsg = "密钥验证失败,uidEn:" + uidEn + ",sr:" + sr;
                            model.addAttribute("errorMsg", "密钥验证失败");
                            return checkError(snapMsg, request, response, model);
                        }
                        userService.bindByThirdId(user.getId(), thirdId);
                    }

                    String viewName = this.write2Response(user, request, response, model);
                    return this.viewNegotiating(viewName, request, response, model);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = "未知异常:" + SendErrorMailUtil.formartException(e);
            return commonError(true, errorMsg, request, response, model);
        }
    }

    /**
     * 切换账号错误
     */
    public ModelAndView switchOrBindAccountError(String errorMsg, HttpServletRequest request,
            HttpServletResponse response, final Model model) {
        return commonError(false, errorMsg, request, response, model);
    }

    /**
     * 验证错误
     */
    public ModelAndView checkError(String errorMsg, HttpServletRequest request, HttpServletResponse response,
            final Model model) {
        return commonError(true, errorMsg, request, response, model);
    }

    /**
     * 通用错误
     */
    public ModelAndView commonError(boolean sendMail, String errorMsg, HttpServletRequest request,
            HttpServletResponse response, final Model model) {
        if (sendMail) {

            SendErrorMailUtil.sendErrorMailReal("afterLogin 错误：", null, "125503048@qq.com",
                    RequestToolKit.getCompleteUrl(request) + ",errorMsg:" + errorMsg);
        }
        if (model.asMap().get("errorMsg") == null) {
            model.addAttribute("errorMsg", "糟糕，页面没打开...");
        }
        return this.viewNegotiating("third/error", request, response, model);
    }

    protected abstract String write2Response(User user, HttpServletRequest request, HttpServletResponse response,
            final Model model) throws Exception;

}