package com.parstasmim.mtni.dwf.interceptors;

import com.parstasmim.mtni.dwf.configuration.AppStringResources;
import com.parstasmim.mtni.dwf.model.service.ManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    protected static final Logger logger = LogManager.getLogger();

    @Autowired
    private Environment env;

    @Autowired
    private ManagementService managementService;

    //========================================================================================================
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        logger.info(String.format("URI: %s, Method: %s", request.getRequestURI(), request.getMethod()));
        boolean auth = authenticateBySession(request);
        logger.info(String.format("Authentication Status: %b", auth));

        if (!auth) {
            response.sendRedirect(AppStringResources.root);
        }

        return auth;
    }

    //========================================================================================================
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    //========================================================================================================
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    //========================================================================================================


    //========================================================================================================
    private boolean authenticateBySession(HttpServletRequest request) {

        logger.info(String.format("Session ID: %s", request.getSession().getId()));

        Map logonUserMap = (Map) request.getSession().getAttribute("logonUser");
        logger.info(String.format("preHandle: %s", logonUserMap));

        if (logonUserMap != null) {
            return true;
        }
        return false;
    }
}
