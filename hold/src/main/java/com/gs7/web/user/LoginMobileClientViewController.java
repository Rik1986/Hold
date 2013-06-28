package com.gs7.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chinaren.framework.common.mail.SendErrorMailUtil;
import com.chinaren.framework.common.util.MD5Utils;
import com.gs7.domain.User;
import com.gs7.exception.ErrorCodeEnum;
import com.gs7.service.ThirdCloudServer;
import com.gs7.web.RequestToolKit;
import com.sohu.zh.model.ThirdUser;
import com.sohu.zh.result.AccessTokenResult;

@Controller
@RequestMapping("/m/accountClient")
public class LoginMobileClientViewController extends LoginBaseViewController {

    private final String type = "mobile";

    @RequestMapping("/afterLogin")
    public ModelAndView afterLogin(@RequestParam(value = "provider", required = true)
    String provider, @RequestParam(value = "uid", required = false)
    String identity, @RequestParam(value = "expires_in", required = false)
    String expiresIn, @RequestParam(value = "access_token", required = false)
    String accessToken, @RequestParam(value = "refresh_token", required = false)
    String refreshToken, HttpServletRequest request, HttpServletResponse response, final Model model) {
        AccessTokenResult token = new AccessTokenResult();
        token.setAccessToken(accessToken);
        token.setExpireIn(AccessTokenResult.getRealExpireInTime(Long.parseLong(expiresIn)));
        token.setRefreshToken(refreshToken);
        token.setUid(identity);
        String clientName = provider + "Yehuo";
        ThirdUser thirdUser;
        try {
            thirdUser = ThirdCloudServer.createOrUpdateUser(getRemoteIp(request), clientName, token);
        } catch (Exception e) {
            logger.error("", e);
            return commonError(true, e.getMessage(), request, response, model);
        }
        User user = RequestToolKit.getCurrentUser(request);
        long uid = (user == null ? 0 : user.getId());
        return _afterLogin("", provider, type, uid, false, thirdUser.getId().toString(), request, response, model);
    }

    @Override
    protected String write2Response(User orgUser, HttpServletRequest request, HttpServletResponse response, Model model)
            throws Exception {
        String viewName = "";
        long uidl = orgUser.getId();
        long salt = orgUser.getSalt();
        String token = MD5Utils.getMD5Str("" + uidl + salt);
        String uid = Long.toString(uidl);
        model.addAttribute("token", token);
        model.addAttribute("uid", uid);
        this.successView(model);
        return viewName;

    }

    /**
     * 通用错误
     */
    @Override
    public ModelAndView commonError(boolean sendMail, String errorMsg, HttpServletRequest request,
            HttpServletResponse response, final Model model) {
        if (sendMail) {
            SendErrorMailUtil.sendErrorMailReal("client afterLogin 错误：", null, "125503048@qq.com",
                    RequestToolKit.getCompleteUrl(request) + ",errorMsg:" + errorMsg);
        }
        String errorMessage = "糟糕，失败了。";
        if (model.asMap().get("errorMsg") != null) {
            errorMessage = model.asMap().get("errorMsg").toString();
        }
        this.failedView(model, ErrorCodeEnum.BINDING_FAIL.code, errorMessage, errorMessage);
        return viewNegotiating(request, response, model);
    }

    @Override
    protected String getAfterLoginUrl() {
        return null;
    }

    @Override
    public ModelAndView thirdLogin(@RequestParam(value = "provider", required = true)
    String provider, @RequestParam(value = "opt", required = false)
    String opt, HttpServletRequest request, HttpServletResponse response, final Model model) throws Exception {
        return null;
    }

    @Override
    public ModelAndView afterLogin(String jsonValue, String provider, Long uid, String uidEn,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        return null;
    }
}
