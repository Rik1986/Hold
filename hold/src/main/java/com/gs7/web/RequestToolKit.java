package com.gs7.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gs7.domain.User;

/**
 * @author zhuhua 请求处理的公共类
 */
public class RequestToolKit {

    static Logger logger = LoggerFactory.getLogger(RequestToolKit.class);

    private static final String http = "http://";

    /**
     * 返回完整的url，把域名放到配置文件，不硬编码。
     * 
     * @param url
     * @return
     */
    public static String getFullUrl(HttpServletRequest request, String url) {
    	String temp = request.getServerName();
		int port = request.getServerPort();
		if (port == 80) {
			return http + temp + url;
		} else {
			return http + temp + ":" + port + url;
		}

    }

    /**
     * 获取当前user对象
     * 
     * @param request
     * @return
     */
    public static User getCurrentUser(HttpServletRequest request) {
        if (request.getAttribute("user") == null) {
            return null;
        } else {
            try {
                return ((User) request.getAttribute("user"));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 种cookie
     * 
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 种cookie
     * 
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(HttpServletResponse response, String path, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 根据名称读取cookie
     * 
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 根据名称读取cookie
     * 
     * @param request
     * @param name
     * @return
     */
    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 读取cookie的map
     * 
     * @param request
     * @return
     */
    public static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 获取完整的url
     * 
     * @param request
     * @return
     */

    @SuppressWarnings("rawtypes")
    public static String getCompleteUrl(HttpServletRequest request) {
        try {
            Map map = request.getParameterMap();
            String queryString = "";
            if (map != null) {
                for (Object key : map.keySet()) {
                    String[] values = (String[]) map.get(key);
                    if (values != null) {
                        for (String value : values) {
                            queryString = queryString + key + "=" + value + "&";
                        }
                    }
                }
            }

            String headString = ",heads:";
            try {
                Enumeration<String> headenumer = request.getHeaderNames();
                while (headenumer.hasMoreElements()) {
                    String head = headenumer.nextElement();
                    headString = headString + head + ":" + request.getHeader(head) + ",";
                }
            } catch (Exception e) {

            }

            if (org.apache.commons.lang.StringUtils.isNotBlank(queryString)) {
                return request.getRequestURI() + "?" + queryString + headString;
            } else {
                return request.getRequestURI() + headString;

            }
        } catch (Exception e) {
            return request.getRequestURI();

        }
    }

    public static String getVersion(HttpServletRequest request) {

        String version = StringUtils.isBlank(request.getHeader("v")) ? request.getHeader("V") : request.getHeader("v");
        if (version == null) {
            version = "";
        }
        return version;
    }

}
