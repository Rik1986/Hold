package com.gs7.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MobileExceptionHandler extends BaseExceptionHandler {

    @Override
    public ModelAndView processOnFail(HttpServletRequest request, HttpServletResponse response, Object object,
            Exception exception) {
        return super._processOnFail(request, response, object, exception, "mobile control unkonw error");
    }

}