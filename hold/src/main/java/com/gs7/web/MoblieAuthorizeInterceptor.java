package com.gs7.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinaren.framework.common.util.MD5Utils;
import com.chinaren.framework.web.view.ResponseObject;
import com.gs7.domain.User;
import com.gs7.exception.ServiceException;

/**
 * 
 * @author yongbiaoli
 * 
 */
@Component
public class MoblieAuthorizeInterceptor extends BaseAuthorizeInterceptor {

    Logger logger = LoggerFactory.getLogger(MoblieAuthorizeInterceptor.class);

    private static String wapUrl = "/wap/";

    public static String word = "客户端用token加上这句话再加个随机数做md5";

    @Override
    public void processOnFail(Method m, HttpServletRequest request, HttpServletResponse response) {
        String requestUri = request.getRequestURI();
        if (requestUri.startsWith(wapUrl)) {
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", "/error");
        } else {
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/javascript;charset=UTF-8");
            response.setStatus(ResponseObject.STATUS_CODE_FAILED);
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ("yes".equals(request.getAttribute("aes_login_error"))) {
                out.write("{\"status\":20001,\"data\":{},\"statusText\":\"login error\"} ");
            } else {
                out.write("{\"status\":10001,\"data\":{},\"statusText\":\"system error\"} ");
            }
            out.flush();
            out.close();
        }
    }

    @Override
    public User checkUser(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String requestUri = request.getRequestURI();

        if (requestUri.startsWith(wapUrl)) {
            // wap采用 用户名和密码来进行验证。
        } else {
            String uids = request.getHeader("mobileUid");
            String secretString = request.getHeader("mobileSecretKey");
            String mobileRandom = request.getHeader("mobileRandom");
            if (uids == null) {
                return null;
            }
            long uid = Integer.parseInt(uids);
            User user = userApiService.getUser(uid);
            String md5key = MD5Utils.getMD5Str(MD5Utils.getMD5Str("" + uid + user.getSalt()) + word + mobileRandom);
            if (md5key.equals(secretString) || URLEncoder.encode(md5key).equals(secretString)) {
                return user;
            }
            logger.info("login error:id:{},secret{}", uids, secretString);
            return null;
        }

        return null;

    }

    public static void main(String[] sdg) throws Exception {
        System.out.println(new Date(1348163966000l));

    }

}