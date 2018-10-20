package com.parstasmim.mtni.dwf.controller;

import com.parstasmim.mtni.dwf.configuration.AppStringResources;
import com.parstasmim.mtni.dwf.model.service.ManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {})
public class indexController {

    protected static final Logger logger = LogManager.getLogger();

    @Autowired
    private ManagementService managementService;

    //==================================================================================================================
    @RequestMapping(value = {AppStringResources.root, AppStringResources.rr_public_index1, AppStringResources.rr_public_index2, AppStringResources.rr_public_index3}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map index(HttpSession httpSession, HttpServletRequest httpRequest) {


        Map indexMap = new HashMap();
        Map sessionMap = new HashMap();

        Enumeration<String> sessionAttributeNames = httpSession.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String sa = sessionAttributeNames.nextElement();
            sessionMap.put(sa, httpSession.getAttribute(sa));
        }

        indexMap.put("Application Name", "Data Warehouse Feeder");
        indexMap.put("Error", httpRequest.getParameter("error"));
        indexMap.put("Message", httpRequest.getParameter("message"));
        indexMap.put("HttpSession", sessionMap);

        return indexMap;
    }

    //==================================================================================================================
    @RequestMapping(value = {AppStringResources.rr_public_login}, method = {RequestMethod.POST, RequestMethod.GET})
    public void login(@RequestParam String username, @RequestParam String password, HttpServletRequest httpRequest, HttpServletResponse httpResponse, HttpSession httpSession) throws IOException {
        if (managementService.authenticateUser(username, password)) {

            Map sessionMap = new HashMap();

            sessionMap.put(AppStringResources.sessionLogonUserId, username);
            sessionMap.put("getMethod", httpRequest.getMethod());
            sessionMap.put("getRemoteUser", httpRequest.getRemoteUser());
            sessionMap.put("getAuthType", httpRequest.getAuthType());
            sessionMap.put("getRequestedSessionId", httpRequest.getRequestedSessionId());
            sessionMap.put("getQueryString", httpRequest.getQueryString());
            sessionMap.put("getRequestURI", httpRequest.getRequestURI());
            sessionMap.put("getRequestURL", httpRequest.getRequestURL());
            sessionMap.put("getRemoteAddr", httpRequest.getRemoteAddr());
            sessionMap.put("getRemoteHost", httpRequest.getRemoteHost());
            sessionMap.put("getRemotePort", httpRequest.getRemotePort());
            sessionMap.put("isRequestedSessionIdFromCookie", httpRequest.isRequestedSessionIdFromCookie());
            sessionMap.put("getCookies", Arrays.toString(httpRequest.getCookies()));

            httpSession.setMaxInactiveInterval(60);
            httpSession.setAttribute(AppStringResources.sessionLogonUser, sessionMap);
            httpResponse.sendRedirect(String.format("%s%s?message=Welcome To DWF", AppStringResources.rr_management, AppStringResources.rr_home1));

            logger.info(String.format("sessionMap:\n%s", sessionMap.toString()));
        } else {
            httpResponse.sendRedirect("?error=1&message=Invalid username or password!");
        }

    }

    //==================================================================================================================
    @RequestMapping(value = {AppStringResources.rr_public_logout}, method = {RequestMethod.POST, RequestMethod.GET})
    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse, HttpSession httpSession) throws IOException {

        httpSession.removeAttribute(AppStringResources.sessionLogonUser);
        httpResponse.sendRedirect("?message=Successfully logged out");
        logger.info(String.format("sessionMap:\n%s" + httpSession.getAttribute(AppStringResources.sessionLogonUser)));
    }
}
