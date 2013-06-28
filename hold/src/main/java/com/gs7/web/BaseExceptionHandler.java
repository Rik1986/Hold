package com.gs7.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.chinaren.framework.common.mail.SendErrorMailUtil;
import com.gs7.exception.ErrorCodeEnum;
import com.gs7.exception.ServiceException;

//@Component
public abstract class BaseExceptionHandler extends BaseController implements HandlerExceptionResolver {

    Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
            Exception exception) {
        return processOnFail(request, response, object, exception);
    }

    public abstract ModelAndView processOnFail(HttpServletRequest request, HttpServletResponse response, Object object,
            Exception exception);

    public ModelAndView _processOnFail(HttpServletRequest request, HttpServletResponse response, Object object,
            Exception e, String errorTitle) {
        this.LOGGER.error(e.getMessage(), e);
        ExtendedModelMap model = new BindingAwareModelMap();
        if (e instanceof org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException) {

            return viewNegotiating("/error404", request, response, model);
        }

        ErrorCodeEnum codeEnum = ErrorCodeEnum.SYSTEM_ERROR;
        if (e instanceof ServiceException) {
            codeEnum = ((ServiceException) e).getErrorCodeEnum();
        } else {
            SendErrorMailUtil
                    .sendErrorMailTo(errorTitle, e, RequestToolKit.getCompleteUrl(request), "125503048@qq.com");
        }

        this.failedView(model, codeEnum);

        if (!request.getRequestURI().startsWith("/wap/")) {
            return viewNegotiating(request, response, model);
        } else {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    e.printStackTrace(new PrintStream(baos));
                    model.addAttribute("errorMsg", baos.toString());
                    model.addAttribute("exception", e);
                } finally {
                    baos.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return viewNegotiating("/error", request, response, model);
        }

    }

}