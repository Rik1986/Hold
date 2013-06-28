package com.gs7.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author yongbiaoli
 * 
 */
public interface LoginFailInterface {

    /**
     * 处理登陆失败的逻辑.<br>
     * 如果返回false,会继续执行下一个LoginFail处理类。如果所有的都返回false,会将结果传递给spring来处理，
     * 不会调用到control的逻辑。<br>
     * 如果返回true，不会执行以后的loginFail，会将结果传递给spring来处理，会调用到control的逻辑。
     * 
     * 
     * @param request
     * @param response
     * @return
     */
    public boolean onLoginFail(HttpServletRequest request, HttpServletResponse response);
}
