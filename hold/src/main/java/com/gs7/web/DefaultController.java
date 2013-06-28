package com.gs7.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 12-3-17 下午3:30
 * 
 * @author jiaguotian Copyright 2012 Sohu.com Inc. All Rights Reserved.
 */
public class DefaultController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        if (uri != null && uri.length() > 1 && !(uri.indexOf("/") < 0)) {
            String redirect = null;
            // //特殊处理型如：/m/?msgid=
            // if (uri.matches("/m/")) {
            // redirect = specialUriM(request);
            // }
            // //特殊处理型如：http://t.sohu.com/d0ngyongw
            // if (query == null || query.isEmpty()) {
            // redirect = specialUriDomain(request);
            // }
            if (redirect != null) {
                ModelAndView view = new ModelAndView();
                view.setViewName("redirect:" + redirect);
                return view;
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return null;
    }
}
