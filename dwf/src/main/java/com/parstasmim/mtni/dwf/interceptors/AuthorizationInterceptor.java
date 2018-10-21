package com.parstasmim.mtni.dwf.interceptors;

import com.parstasmim.mtni.dwf.configuration.AppStringResources;
import com.parstasmim.mtni.dwf.model.entity.Action;
import com.parstasmim.mtni.dwf.model.entity.User;
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
import java.util.Map;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    protected static final Logger logger = LogManager.getLogger();

    @Autowired
    Environment env;

    @Autowired
    private ManagementService managementService;

    //========================================================================================================
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info(String.format("URI: %s, Method: %s", request.getRequestURI(), request.getMethod()));
        boolean auth = authorizePerAction(request);
        logger.info(String.format("Authorization Status: %b", auth));

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
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    //========================================================================================================
    private boolean authorizePerAction(HttpServletRequest request) {

        String userName = (String) ((Map) request.getSession().getAttribute(AppStringResources.sessionLogonUser)).get(AppStringResources.sessionLogonUserId);

        logger.trace(String.format("User Name: %s", userName));
        User logonUser = managementService.getUserBy(userName);

        for (Action userAction : logonUser.getActions()) {
            boolean hasActionAccess = userAction.urlsArray().contains(request.getRequestURI()) && userAction.requestTypesArray().contains(request.getMethod());
            String x = "";
            for (String s : userAction.urlsArray()) {
                x += s + "-";
            }
            String y = "";
            for (String s : userAction.requestTypesArray()) {
                y += s + "-";
            }

            logger.trace(String.format("User action: %s, %b", userAction.getDisplayName(), hasActionAccess));
            logger.trace(String.format("URLs: %s", x));
            logger.trace(String.format("ReqTypes: %s", y));
            if (hasActionAccess) {
                return true;
            }
        }
        return false;
    }
}
