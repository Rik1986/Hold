package com.gs7.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.support.HandlerMethodResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import com.gs7.domain.User;
import com.gs7.service.UserService;

/**
 * 
 * @author yongbiaoli
 * 
 */
public abstract class BaseAuthorizeInterceptor extends HandlerInterceptorAdapter {

    private final Map<Class<?>, ServletHandlerMethodResolver> methodResolverCache = new ConcurrentHashMap<Class<?>, ServletHandlerMethodResolver>();

    private final MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final Map<Class<? extends LoginFailInterface>, LoginFailInterface> LoginFailCached = new HashMap<Class<? extends LoginFailInterface>, LoginFailInterface>();

    @Resource(name = "userService")
    protected UserService userApiService;

    protected Logger logger = LoggerFactory.getLogger(BaseAuthorizeInterceptor.class);

    private static final int cookieTime = 60 * 60 * 24 * 14;

    public abstract void processOnFail(Method m, HttpServletRequest request, HttpServletResponse response);

    public abstract User checkUser(HttpServletRequest request, HttpServletResponse response)
            throws com.gs7.exception.ServiceException;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        logger.info("request:{}", RequestToolKit.getCompleteUrl(request));
        final boolean handlerOk = super.preHandle(request, response, handler);
        if (request.getAttribute("user") != null || "/favicon.ico".equals(request.getRequestURI())) {
            return handlerOk;
        }
        final ServletHandlerMethodResolver resolver = getMethodResolver(handler);
        final Method m = resolver.resolveHandlerMethod(request);

        boolean needLogin = false;

        String redirectUrl = null;
        LoginFailInterface[] loginFails = null;
        Login login = m.getAnnotation(Login.class);
        if (login != null) {
            needLogin = login.need();
            redirectUrl = login.redirectUrl();
            Class<? extends LoginFailInterface>[] loginFailClasses = login.loginFailClass();
            if (loginFailClasses != null && loginFailClasses.length > 0) {
                loginFails = new LoginFailInterface[loginFailClasses.length];
                for (int i = 0; i < loginFailClasses.length; i++) {
                    Class<? extends LoginFailInterface> loginFailClass = loginFailClasses[i];
                    if (LoginFailCached.get(loginFailClass) == null) {
                        LoginFailCached.put(loginFailClass, loginFailClass.newInstance());
                    }
                    loginFails[i] = LoginFailCached.get(loginFailClass);
                }
            }

        }
        if (handlerOk) {

            User user = checkUser(request, response);
            // 可以再这里判断用户是否 是选择了兴趣。
            if (user != null) {
                // 登陆成功 ，设置uid的cookie
                logger.debug("login in success::user:{}", user);

                request.setAttribute("user", user);

                return true;
            }

            if (needLogin) {
                // 首先 判断有没有 设置跳转的url
                if (StringUtils.hasLength(redirectUrl)) {
                    response.sendRedirect(redirectUrl);
                } else if (loginFails != null) {
                    // 然后 判断有没有 设置处理的逻辑
                    for (LoginFailInterface loginFail : loginFails) {
                        if (loginFail.onLoginFail(request, response)) {
                            return true;
                        }
                    }
                } else {
                    processOnFail(m, request, response);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    private ServletHandlerMethodResolver getMethodResolver(final Object handler) {
        final Class<?> handlerClass = ClassUtils.getUserClass(handler);
        ServletHandlerMethodResolver resolver = this.methodResolverCache.get(handlerClass);
        if (resolver == null) {
            synchronized (this.methodResolverCache) {
                resolver = this.methodResolverCache.get(handlerClass);
                if (resolver == null) {
                    resolver = new ServletHandlerMethodResolver(handlerClass);
                    this.methodResolverCache.put(handlerClass, resolver);
                }
            }
        }
        return resolver;
    }

    static class RequestMappingInfo {

        private final String[] patterns;

        private final RequestMethod[] methods;

        private final String[] params;

        private final String[] headers;

        RequestMappingInfo(final String[] patterns, final RequestMethod[] methods, final String[] params,
                final String[] headers) {
            this.patterns = patterns != null ? patterns : new String[0];
            this.methods = methods != null ? methods : new RequestMethod[0];
            this.params = params != null ? params : new String[0];
            this.headers = headers != null ? headers : new String[0];
        }

        public boolean hasPatterns() {
            return this.patterns.length > 0;
        }

        public String[] getPatterns() {
            return this.patterns;
        }

        public int getMethodCount() {
            return this.methods.length;
        }

        public int getParamCount() {
            return this.params.length;
        }

        public int getHeaderCount() {
            return this.headers.length;
        }

        public boolean matches(final HttpServletRequest request) {
            return matchesRequestMethod(request) && matchesParameters(request) && matchesHeaders(request);
        }

        public boolean matchesHeaders(final HttpServletRequest request) {
            return checkHeaders(this.headers, request);
        }

        public boolean matchesParameters(final HttpServletRequest request) {
            return checkParameters(this.params, request);
        }

        public boolean matchesRequestMethod(final HttpServletRequest request) {
            return checkRequestMethod(this.methods, request);
        }

        public Set<String> methodNames() {
            final Set<String> methodNames = new LinkedHashSet<String>(this.methods.length);
            for (final RequestMethod method : this.methods) {
                methodNames.add(method.name());
            }
            return methodNames;
        }

        @Override
        public boolean equals(final Object obj) {
            final RequestMappingInfo other = (RequestMappingInfo) obj;
            return (Arrays.equals(this.patterns, other.patterns) && Arrays.equals(this.methods, other.methods)
                    && Arrays.equals(this.params, other.params) && Arrays.equals(this.headers, other.headers));
        }

        @Override
        public int hashCode() {
            return ((Arrays.hashCode(this.patterns) * 23) + (Arrays.hashCode(this.methods) * 29)
                    + (Arrays.hashCode(this.params) * 31) + Arrays.hashCode(this.headers));
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(Arrays.asList(this.patterns));
            if (this.methods.length > 0) {
                builder.append(',');
                builder.append(Arrays.asList(this.methods));
            }
            if (this.headers.length > 0) {
                builder.append(',');
                builder.append(Arrays.asList(this.headers));
            }
            if (this.params.length > 0) {
                builder.append(',');
                builder.append(Arrays.asList(this.params));
            }
            return builder.toString();
        }
    }

    /**
     * Subclass of {@link RequestMappingInfo} that holds request-specific data.
     */
    static class RequestSpecificMappingInfo extends RequestMappingInfo {

        private final List<String> matchedPatterns = new ArrayList<String>();

        RequestSpecificMappingInfo(final String[] patterns, final RequestMethod[] methods, final String[] params,
                final String[] headers) {
            super(patterns, methods, params, headers);
        }

        RequestSpecificMappingInfo(final RequestMappingInfo other) {
            super(other.patterns, other.methods, other.params, other.headers);
        }

        public void addMatchedPattern(final String matchedPattern) {
            this.matchedPatterns.add(matchedPattern);
        }

        public void sortMatchedPatterns(final Comparator<String> pathComparator) {
            Collections.sort(this.matchedPatterns, pathComparator);
        }

        public String bestMatchedPattern() {
            return (!this.matchedPatterns.isEmpty() ? this.matchedPatterns.get(0) : null);
        }
    }

    static class RequestSpecificMappingInfoComparator implements Comparator<RequestSpecificMappingInfo> {

        private final Comparator<String> pathComparator;

        private final ServerHttpRequest request;

        RequestSpecificMappingInfoComparator(final Comparator<String> pathComparator, final HttpServletRequest request) {
            this.pathComparator = pathComparator;
            this.request = new ServletServerHttpRequest(request);
        }

        @Override
        public int compare(final RequestSpecificMappingInfo info1, final RequestSpecificMappingInfo info2) {
            final int pathComparison = this.pathComparator.compare(info1.bestMatchedPattern(),
                    info2.bestMatchedPattern());
            if (pathComparison != 0) {
                return pathComparison;
            }
            final int info1ParamCount = info1.getParamCount();
            final int info2ParamCount = info2.getParamCount();
            if (info1ParamCount != info2ParamCount) {
                return info2ParamCount - info1ParamCount;
            }
            final int info1HeaderCount = info1.getHeaderCount();
            final int info2HeaderCount = info2.getHeaderCount();
            if (info1HeaderCount != info2HeaderCount) {
                return info2HeaderCount - info1HeaderCount;
            }
            final int acceptComparison = compareAcceptHeaders(info1, info2);
            if (acceptComparison != 0) {
                return acceptComparison;
            }
            final int info1MethodCount = info1.getMethodCount();
            final int info2MethodCount = info2.getMethodCount();
            if ((info1MethodCount == 0) && (info2MethodCount > 0)) {
                return 1;
            } else if ((info2MethodCount == 0) && (info1MethodCount > 0)) {
                return -1;
            } else if ((info1MethodCount == 1) & (info2MethodCount > 1)) {
                return -1;
            } else if ((info2MethodCount == 1) & (info1MethodCount > 1)) {
                return 1;
            }
            return 0;
        }

        private int compareAcceptHeaders(final RequestMappingInfo info1, final RequestMappingInfo info2) {
            final List<MediaType> requestAccepts = this.request.getHeaders().getAccept();
            MediaType.sortByQualityValue(requestAccepts);

            final List<MediaType> info1Accepts = getAcceptHeaderValue(info1);
            final List<MediaType> info2Accepts = getAcceptHeaderValue(info2);

            for (final MediaType requestAccept : requestAccepts) {
                final int pos1 = indexOfIncluded(info1Accepts, requestAccept);
                final int pos2 = indexOfIncluded(info2Accepts, requestAccept);
                if (pos1 != pos2) {
                    return pos2 - pos1;
                }
            }
            return 0;
        }

        private int indexOfIncluded(final List<MediaType> infoAccepts, final MediaType requestAccept) {
            for (int i = 0; i < infoAccepts.size(); i++) {
                final MediaType info1Accept = infoAccepts.get(i);
                if (requestAccept.includes(info1Accept)) {
                    return i;
                }
            }
            return -1;
        }

        private List<MediaType> getAcceptHeaderValue(final RequestMappingInfo info) {
            for (final String header : info.headers) {
                final int separator = header.indexOf('=');
                if (separator != -1) {
                    final String key = header.substring(0, separator);
                    final String value = header.substring(separator + 1);
                    if ("Accept".equalsIgnoreCase(key)) {
                        return MediaType.parseMediaTypes(value);
                    }
                }
            }
            return Collections.emptyList();
        }
    }

    private class ServletHandlerMethodResolver extends HandlerMethodResolver {

        private final Map<Method, RequestMappingInfo> mappings = new HashMap<Method, RequestMappingInfo>();

        private ServletHandlerMethodResolver(final Class<?> handlerType) {
            init(handlerType);
        }

        @Override
        protected boolean isHandlerMethod(final Method method) {
            if (this.mappings.containsKey(method)) {
                return true;
            }
            final RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
            if (mapping != null) {
                final String[] patterns = mapping.value();
                RequestMethod[] methods = new RequestMethod[0];
                String[] params = new String[0];
                String[] headers = new String[0];
                if (!hasTypeLevelMapping() || !Arrays.equals(mapping.method(), getTypeLevelMapping().method())) {
                    methods = mapping.method();
                }
                if (!hasTypeLevelMapping() || !Arrays.equals(mapping.params(), getTypeLevelMapping().params())) {
                    params = mapping.params();
                }
                if (!hasTypeLevelMapping() || !Arrays.equals(mapping.headers(), getTypeLevelMapping().headers())) {
                    headers = mapping.headers();
                }
                final RequestMappingInfo mappingInfo = new RequestMappingInfo(patterns, methods, params, headers);
                this.mappings.put(method, mappingInfo);
                return true;
            }
            return false;
        }

        public Method resolveHandlerMethod(final HttpServletRequest request) throws ServletException {
            final String lookupPath = BaseAuthorizeInterceptor.this.urlPathHelper.getLookupPathForRequest(request);
            final Comparator<String> pathComparator = BaseAuthorizeInterceptor.this.pathMatcher
                    .getPatternComparator(lookupPath);
            final Map<RequestSpecificMappingInfo, Method> targetHandlerMethods = new LinkedHashMap<RequestSpecificMappingInfo, Method>();
            final Set<String> allowedMethods = new LinkedHashSet<String>(7);
            String resolvedMethodName = null;
            for (final Method handlerMethod : getHandlerMethods()) {
                final RequestSpecificMappingInfo mappingInfo = new RequestSpecificMappingInfo(
                        this.mappings.get(handlerMethod));
                boolean match = false;
                if (mappingInfo.hasPatterns()) {
                    for (String pattern : mappingInfo.getPatterns()) {
                        if (!hasTypeLevelMapping() && !pattern.startsWith("/")) {
                            pattern = "/" + pattern;
                        }
                        final String combinedPattern = getCombinedPattern(pattern, lookupPath, request);
                        if (combinedPattern != null) {
                            if (mappingInfo.matches(request)) {
                                match = true;
                                mappingInfo.addMatchedPattern(combinedPattern);
                            } else {
                                if (!mappingInfo.matchesRequestMethod(request)) {
                                    allowedMethods.addAll(mappingInfo.methodNames());
                                }
                                break;
                            }
                        }
                    }
                    mappingInfo.sortMatchedPatterns(pathComparator);
                } else {
                    match = mappingInfo.matches(request);
                    if (match && (mappingInfo.getMethodCount() == 0) && (mappingInfo.getParamCount() == 0)
                            && (resolvedMethodName != null) && !resolvedMethodName.equals(handlerMethod.getName())) {
                        match = false;
                    } else {
                        if (!mappingInfo.matchesRequestMethod(request)) {
                            allowedMethods.addAll(mappingInfo.methodNames());
                        }
                    }
                }
                if (match) {
                    Method oldMappedMethod = targetHandlerMethods.put(mappingInfo, handlerMethod);
                    if ((oldMappedMethod != null) && (oldMappedMethod != handlerMethod)) {
                        if ((BaseAuthorizeInterceptor.this.methodNameResolver != null) && !mappingInfo.hasPatterns()) {
                            if (!oldMappedMethod.getName().equals(handlerMethod.getName())) {
                                if (resolvedMethodName == null) {
                                    resolvedMethodName = BaseAuthorizeInterceptor.this.methodNameResolver
                                            .getHandlerMethodName(request);
                                }
                                if (!resolvedMethodName.equals(oldMappedMethod.getName())) {
                                    oldMappedMethod = null;
                                }
                                if (!resolvedMethodName.equals(handlerMethod.getName())) {
                                    if (oldMappedMethod != null) {
                                        targetHandlerMethods.put(mappingInfo, oldMappedMethod);
                                        oldMappedMethod = null;
                                    } else {
                                        targetHandlerMethods.remove(mappingInfo);
                                    }
                                }
                            }
                        }
                        if (oldMappedMethod != null) {
                            throw new IllegalStateException(
                                    "Ambiguous handler methods mapped for HTTP path '"
                                            + lookupPath
                                            + "': {"
                                            + oldMappedMethod
                                            + ", "
                                            + handlerMethod
                                            + "}. If you intend to handle the same path in multiple methods, then factor "
                                            + "them out into a dedicated handler class with that path mapped at the type level!");
                        }
                    }
                }
            }
            if (!targetHandlerMethods.isEmpty()) {
                final List<RequestSpecificMappingInfo> matches = new ArrayList<RequestSpecificMappingInfo>(
                        targetHandlerMethods.keySet());
                final RequestSpecificMappingInfoComparator requestMappingInfoComparator = new RequestSpecificMappingInfoComparator(
                        pathComparator, request);
                Collections.sort(matches, requestMappingInfoComparator);
                final RequestSpecificMappingInfo bestMappingMatch = matches.get(0);
                final String bestMatchedPath = bestMappingMatch.bestMatchedPattern();
                if (bestMatchedPath != null) {
                    extractHandlerMethodUriTemplates(bestMatchedPath, lookupPath, request);
                }
                return targetHandlerMethods.get(bestMappingMatch);
            } else {
                if (!allowedMethods.isEmpty()) {
                    throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                            StringUtils.toStringArray(allowedMethods));
                }
                throw new NoSuchRequestHandlingMethodException(lookupPath, request.getMethod(),
                        request.getParameterMap());
            }
        }

        private String getCombinedPattern(final String methodLevelPattern, final String lookupPath,
                final HttpServletRequest request) {
            if (hasTypeLevelMapping() && (!ObjectUtils.isEmpty(getTypeLevelMapping().value()))) {
                final String[] typeLevelPatterns = getTypeLevelMapping().value();
                for (String typeLevelPattern : typeLevelPatterns) {
                    if (!typeLevelPattern.startsWith("/")) {
                        typeLevelPattern = "/" + typeLevelPattern;
                    }
                    final String combinedPattern = BaseAuthorizeInterceptor.this.pathMatcher.combine(typeLevelPattern,
                            methodLevelPattern);
                    final String matchingPattern = getMatchingPattern(combinedPattern, lookupPath);
                    if (matchingPattern != null) {
                        return matchingPattern;
                    }
                }
                return null;
            }
            final String bestMatchingPattern = (String) request
                    .getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            if (StringUtils.hasText(bestMatchingPattern) && bestMatchingPattern.endsWith("*")) {
                final String combinedPattern = BaseAuthorizeInterceptor.this.pathMatcher.combine(bestMatchingPattern,
                        methodLevelPattern);
                final String matchingPattern = getMatchingPattern(combinedPattern, lookupPath);
                if ((matchingPattern != null) && !matchingPattern.equals(bestMatchingPattern)) {
                    return matchingPattern;
                }
            }
            return getMatchingPattern(methodLevelPattern, lookupPath);
        }

        private String getMatchingPattern(final String pattern, final String lookupPath) {
            if (pattern.equals(lookupPath)) {
                return pattern;
            }
            final boolean hasSuffix = pattern.indexOf('.') != -1;
            if (!hasSuffix) {
                final String patternWithSuffix = pattern + ".*";
                if (BaseAuthorizeInterceptor.this.pathMatcher.match(patternWithSuffix, lookupPath)) {
                    return patternWithSuffix;
                }
            }
            if (BaseAuthorizeInterceptor.this.pathMatcher.match(pattern, lookupPath)) {
                return pattern;
            }
            final boolean endsWithSlash = pattern.endsWith("/");
            if (!endsWithSlash) {
                final String patternWithSlash = pattern + "/";
                if (BaseAuthorizeInterceptor.this.pathMatcher.match(patternWithSlash, lookupPath)) {
                    return patternWithSlash;
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        private void extractHandlerMethodUriTemplates(final String mappedPattern, final String lookupPath,
                final HttpServletRequest request) {
            Map<String, String> variables = (Map<String, String>) request
                    .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            final int patternVariableCount = StringUtils.countOccurrencesOf(mappedPattern, "{");
            if (((variables == null) || (patternVariableCount != variables.size()))
                    && BaseAuthorizeInterceptor.this.pathMatcher.match(mappedPattern, lookupPath)) {
                variables = BaseAuthorizeInterceptor.this.pathMatcher.extractUriTemplateVariables(mappedPattern,
                        lookupPath);
                request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, variables);
            }
        }
    }

    public static boolean checkRequestMethod(final RequestMethod[] methods, final HttpServletRequest request) {
        if (ObjectUtils.isEmpty(methods)) {
            return true;
        }
        for (final RequestMethod method : methods) {
            if (method.name().equals(request.getMethod())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkParameters(final String[] params, final HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(params)) {
            for (final String param : params) {
                final int separator = param.indexOf('=');
                if (separator == -1) {
                    if (param.startsWith("!")) {
                        if (WebUtils.hasSubmitParameter(request, param.substring(1))) {
                            return false;
                        }
                    } else if (!WebUtils.hasSubmitParameter(request, param)) {
                        return false;
                    }
                } else {
                    final boolean negated = (separator > 0) && (param.charAt(separator - 1) == '!');
                    final String key = !negated ? param.substring(0, separator) : param.substring(0, separator - 1);
                    final String value = param.substring(separator + 1);
                    if (!value.equals(request.getParameter(key))) {
                        return negated;
                    }
                }
            }
        }
        return true;
    }

    public static boolean checkHeaders(final String[] headers, final HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(headers)) {
            for (final String header : headers) {
                final int separator = header.indexOf('=');
                if (separator == -1) {
                    if (header.startsWith("!")) {
                        if (request.getHeader(header.substring(1)) != null) {
                            return false;
                        }
                    } else if (request.getHeader(header) == null) {
                        return false;
                    }
                } else {
                    final boolean negated = (separator > 0) && (header.charAt(separator - 1) == '!');
                    final String key = !negated ? header.substring(0, separator) : header.substring(0, separator - 1);
                    final String value = header.substring(separator + 1);
                    if (isMediaTypeHeader(key)) {
                        final List<MediaType> requestMediaTypes = MediaType.parseMediaTypes(request.getHeader(key));
                        final List<MediaType> valueMediaTypes = MediaType.parseMediaTypes(value);
                        boolean found = false;
                        for (final Iterator<MediaType> valIter = valueMediaTypes.iterator(); valIter.hasNext()
                                && !found;) {
                            final MediaType valueMediaType = valIter.next();
                            for (final Iterator<MediaType> reqIter = requestMediaTypes.iterator(); reqIter.hasNext()
                                    && !found;) {
                                final MediaType requestMediaType = reqIter.next();
                                if (valueMediaType.includes(requestMediaType)) {
                                    found = true;
                                }
                            }

                        }
                        if (!found) {
                            return negated;
                        }
                    } else if (!value.equals(request.getHeader(key))) {
                        return negated;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isMediaTypeHeader(final String headerName) {
        return "Accept".equalsIgnoreCase(headerName) || "Content-Type".equalsIgnoreCase(headerName);
    }

    protected ModelAndView viewNegotiating(String string, HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    public void statistics(HttpServletRequest request, HttpServletResponse response) {
        String src = request.getHeader("Referer");// 上一级页面
        if (org.apache.commons.lang.StringUtils.isNotBlank(src)) {
            src = src.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"");
        }
        String uuid = "";// uuid
        String ad = "";// 渠道推广
        String uad = "";// 用户推广
        String mid = "";// 视频id

        ad = request.getParameter("ad");
        uad = request.getParameter("uad");
        mid = request.getParameter("mid");

        Cookie cookies[] = request.getCookies();
        Cookie c = null;

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                c = cookies[i];
                if (c.getName().equals("uuid")) {
                    uuid = c.getValue();
                } else if (c.getName().equals("cookiead") && org.apache.commons.lang.StringUtils.isBlank(ad)) {
                    ad = c.getValue();
                } else if (c.getName().equals("cookieuad") && org.apache.commons.lang.StringUtils.isBlank(uad)) {
                    uad = c.getValue();
                } else if (c.getName().equals("cookiemid") && org.apache.commons.lang.StringUtils.isBlank(mid)) {
                    mid = c.getValue();
                }
            }
        }
        int cookieTime = 60 * 60 * 24 * 30;

        if (org.apache.commons.lang.StringUtils.isBlank(uuid)) {
            uuid = UUID.randomUUID().toString();
            Cookie newCookie = new Cookie("uuid", uuid);
            newCookie.setMaxAge(Integer.MAX_VALUE);
            newCookie.setPath("/");
            response.addCookie(newCookie);
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(ad)) {
            Cookie newCookie = new Cookie("cookiead", ad);
            newCookie.setPath("/");
            newCookie.setMaxAge(cookieTime);
            response.addCookie(newCookie);
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(uad)) {
            Cookie newCookie = new Cookie("cookieuad", uad);
            newCookie.setPath("/");
            newCookie.setMaxAge(cookieTime);
            response.addCookie(newCookie);
        }

        if (org.apache.commons.lang.StringUtils.isNotBlank(mid)) {
            Cookie newCookie = new Cookie("cookiemid", mid);
            newCookie.setPath("/");
            newCookie.setMaxAge(cookieTime);
            response.addCookie(newCookie);
        }

        request.setAttribute("src", src);
        request.setAttribute("uuid", uuid);
        request.setAttribute("ad", ad);
        request.setAttribute("uad", uad);
        request.setAttribute("mid", uad);

    }

}