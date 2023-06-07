package com.meteor.jsherp.filter;

import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.constant.UserConstant;
import com.meteor.jsherp.domain.User;
import com.meteor.jsherp.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘鑫
 * @version 1.0
 */
@WebFilter(urlPatterns = "/*", initParams = {@WebInitParam(name = "ignorePath", value = "/jshERP-boot/user/login#" +
        "/jshERP-boot/user/registerUser#/jshERP-boot/user/randomImage#/jshERP-boot/platformConfig/getPlatform#" +
        "/jshERP-boot/v2/api-docs#/jshERP-boot/webjars#/jshERP-boot/systemConfig/static"),
        @WebInitParam(name = "ignoreUrl", value = ".ico#/doc.html")})
public class CheckUserFilter implements Filter {

    private static final String IGNORE_PATH = "ignorePath";
    private static final String IGNORE_URL = "ignoreUrl";

    @Resource
    private UserService userService;

    private String[] ignorePathArray;
    private String[] ignoreUrlArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        String ignorePath = filterConfig.getInitParameter(IGNORE_PATH);
        String ignoreUrl = filterConfig.getInitParameter(IGNORE_URL);
        if(StringUtils.hasText(ignorePath)) {
            ignorePathArray = ignorePath.split("#");
        }
        if (StringUtils.hasText(ignoreUrl)) {
            ignoreUrlArray = ignoreUrl.split("#");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = userService.getLoginUser(request.getHeader(ErpAllConstant.REQUEST_TOKEN_KEY));
        if(user != null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String uri = request.getRequestURI();
        if (StringUtils.hasText(uri)) {
            if(checkUrlWithIgnorePath(uri) || checkUrlWithIgnoreUrl(uri)){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            if(!uri.contains("/user/loginOut") && !uri.contains("/function/findMenuByPNumber")){
                response.getWriter().write("loginOut");
            }
        }
        response.setStatus(500);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean checkUrlWithIgnorePath(String uri){
        for (int i = 0; i < ignorePathArray.length; i++) {
            if(uri.startsWith(ignorePathArray[i])){
                return true;
            }
        }
        return false;
    }

    private static final String regexPrefix = "^.*";
    private static final String regexSuffix = ".*$";

    private boolean checkUrlWithIgnoreUrl(String uri){
        for (int i = 0; i < ignoreUrlArray.length; i++) {
            Pattern pattern = Pattern.compile(regexPrefix + ignoreUrlArray[i] + regexSuffix);
            Matcher matcher = pattern.matcher(uri);
            if(matcher.matches()){
                return true;
            }
        }
        return false;
    }

}
