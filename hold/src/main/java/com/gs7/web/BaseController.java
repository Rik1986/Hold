package com.gs7.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.chinaren.framework.web.view.MappingResponseJsonView;
import com.chinaren.framework.web.view.ResponseObject;
import com.gs7.exception.ErrorCodeEnum;

/**
 * 12-3-17 下午3:38
 * 
 * @author jiaguotian Copyright 2012 Sohu.com Inc. All Rights Reserved.
 */
@Controller
public class BaseController {

    // protected static final String partViewDir = Constants.partViewDir;

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected final static String DATA_FORMAT = "f";

    protected final static String DATA_FORMAT_JSON = "json";

    protected final static String DATA_FORMAT_XML = "xml";

    protected final static String DATA_FORMAT_TEXT = "text";

    protected void sendError(HttpServletResponse response, int sc, String msg) throws IOException {
        response.sendError(sc, msg);
    }

    /**
     * 根据viewName 和 data_format 自动生成相应的 ModelAndView
     * 
     * @param viewName
     * @param request
     * @param response
     * @param model
     * @return
     */
    protected ModelAndView viewNegotiating(String viewName, final HttpServletRequest request,
            final HttpServletResponse response, final Model model) {
        ModelAndView modelAndView = null;
        if (StringUtils.isNotEmpty(viewName)) {
            modelAndView = new ModelAndView(viewName, model.asMap());
        } else {
            modelAndView = jsonView(request, response, model);
        }
        return modelAndView;
    }

    //

    /**
     * 非页面视图，根据data_format 自动生成相应的ModelAndView
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    protected ModelAndView viewNegotiating(final HttpServletRequest request, final HttpServletResponse response,
            final Model model) {
        ModelAndView modelAndView = null;
        String f = request.getParameter(DATA_FORMAT);

        // 默认为json
        if (StringUtils.isBlank(f)) {
            f = DATA_FORMAT_JSON;
        }

        if (DATA_FORMAT_JSON.equalsIgnoreCase(f)) {
            modelAndView = jsonView(request, response, model);
        }
        // TODO: 需要支持其他类型的返回(xml、text)
        return modelAndView;
    }

    /**
     * 返回标准ResponseObject格式的json
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    protected ModelAndView jsonView(final HttpServletRequest request, final HttpServletResponse response,
            final Model model) {
        ModelAndView modelAndView = null;
        MappingJacksonJsonView view = new MappingResponseJsonView();
        modelAndView = new ModelAndView(view, model.asMap());
        return modelAndView;
    }

    /**
     * print后会关闭out流
     * 
     * @param response
     * @param message
     * @throws IOException
     */
    protected void printAsJavaScript(HttpServletResponse response, String message) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(message);
        out.close();
    }

    /**
     * 构造一个成功model
     * 
     * @param model
     */
    protected void successView(Model model) {
        this.successView(model, ResponseObject.SUCCESS_TEXT);
    }

    /**
     * 构造一个成功model
     * 
     * @param model
     * @param statusText
     */
    protected void successView(Model model, String statusText) {
        model.addAttribute(ResponseObject.STATUS, ResponseObject.STATUS_CODE_OK);
        model.addAttribute(ResponseObject.STATUS_TEXT, statusText);
    }

    /**
     * 构造一个错误model
     * 
     * @param model
     */
    protected void failedView(Model model) {
        model.addAttribute(ResponseObject.STATUS, ResponseObject.STATUS_CODE_FAILED);
        model.addAttribute(ResponseObject.STATUS_TEXT, ResponseObject.FAILED_TEXT);
    }

    /**
     * 填写错误信息到model
     * 
     * @param model
     * @param status
     * @param statusText
     */
    protected void failedView(Model model, int status, String statusText) {
        failedView(model, status, statusText, "");
    }

    /**
     * 填写错误信息到model
     * 
     * @param model
     * @param status
     * @param statusText
     * @param errorMsg
     */
    protected void failedView(Model model, int status, String statusText, String errorMsg) {
        model.addAttribute(ResponseObject.STATUS, status);
        model.addAttribute(ResponseObject.STATUS_TEXT, statusText);
        // model.addAttribute(ResponseObject.ERROR_MSG, errorMsg);内部错误消息
    }

    /**
     * 从错误码表里获取错误信息，回填写到model里
     * 
     * @param model
     * @param status
     */
    protected void failedView(Model model, ErrorCodeEnum status) {
        failedView(model, status.code, status.text, status.errMsg);
    }

    protected String getRemoteIp(HttpServletRequest request) {
        String remoteip = request.getRemoteAddr();
        if (request.getHeader("X-Real-IP") != null) {
            remoteip = request.getHeader("X-Real-IP");
            LOGGER.debug("realip-X-Real-IP: " + remoteip);
        }
        // 开始->增加X-Forwarded-For取出IP的逻辑
        if (request.getHeader("X-Forwarded-For") != null) {
            String remoteips = request.getHeader("X-Forwarded-For");
            if (remoteips.contains(",")) {
                remoteip = remoteips.split(",")[0];
            } else {
                remoteip = remoteips;
            }
            LOGGER.debug("realip-X-Forwarded-For: " + remoteip);
        }
        // 结束->增加X-Forwarded-For取出IP的逻辑
        if (!"".equals(remoteip)) {
            return remoteip;
        }
        return "127.0.0.1";
    }

    protected long getCurrentUid(HttpServletRequest request) {
        try {
            return RequestToolKit.getCurrentUser(request).getId();
        } catch (Exception e) {
            return 0;
        }
    }

}
