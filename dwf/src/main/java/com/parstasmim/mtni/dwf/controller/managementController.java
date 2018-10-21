package com.parstasmim.mtni.dwf.controller;

import com.parstasmim.mtni.dwf.configuration.AppStringResources;
import com.parstasmim.mtni.dwf.model.entity.Action;
import com.parstasmim.mtni.dwf.model.entity.Group;
import com.parstasmim.mtni.dwf.model.entity.User;
import com.parstasmim.mtni.dwf.model.service.ManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(AppStringResources.rr_management)
public class managementController {

    protected static final Logger logger = LogManager.getLogger();

    @Autowired
    private ManagementService managementService;


    //==================================================================================================================
    @RequestMapping(value = {AppStringResources.empty, AppStringResources.root, AppStringResources.rr_home1, AppStringResources.rr_home2, AppStringResources.rr_home3}, method = {RequestMethod.GET, RequestMethod.POST})
    public Map home(HttpSession httpSession, HttpServletRequest httpRequest) {

        Map indexMap = new HashMap();
        Map sessionMap = new HashMap();

        Enumeration<String> sessionAttributeNames = httpSession.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String sa = sessionAttributeNames.nextElement();
            sessionMap.put(sa, httpSession.getAttribute(sa));
        }

        indexMap.put("Page", "Home");
        indexMap.put("Error", httpRequest.getParameter("error"));
        indexMap.put("Message", httpRequest.getParameter("message"));
        indexMap.put("HttpSession", sessionMap);

        return indexMap;
    }

    //==================================================================================================================

    //==================================================================================================================
    //==================================================================================================================
    @RequestMapping(value = {AppStringResources.rr_users}, method = {RequestMethod.GET, RequestMethod.POST})
    public List listAllUsers() {

        logger.info("listAllUsers:");
        List<User> users = managementService.getUsersList();
        List usersList = new ArrayList();
        for (User user : users) {

            SortedMap userInfo = new TreeMap();
            userInfo.put("ID", user.getId());
            userInfo.put("User Name", user.getUserName());
            userInfo.put("Display Name", user.getDisplayName());
            userInfo.put("Birth Date", user.getBirthDate());
            userInfo.put("Email", user.getEmail());

            List<SortedMap> actions = new ArrayList();
            for (Action action : user.getActions()) {
                SortedMap actionMap = new TreeMap();
                actionMap.put("ID", action.getId());
                actionMap.put("Display Name", action.getDisplayName());
                actionMap.put("URL", action.getUrl());
                actionMap.put("Request Type", action.getRequestType());
                actions.add(actionMap);
            }
            userInfo.put("Actions", actions);

            List<SortedMap> groups = new ArrayList();
            for (Group group : user.getGroups()) {
                SortedMap accessesMap = new TreeMap();
                accessesMap.put("ID", group.getId());
                accessesMap.put("Display Name", group.getDisplayName());
                groups.add(accessesMap);
            }
            userInfo.put("Groups", groups);
            usersList.add(userInfo);
        }

        return usersList;
    }

    //==================================================================================================================
    @RequestMapping(value = AppStringResources.rr_user, method = {RequestMethod.GET, RequestMethod.POST})
    public Map getUser(@PathVariable("username") String username) {

        logger.info(String.format("getUserBy: \"%s\"", username));
        User user = managementService.getUserBy(username);

        SortedMap userInfo = new TreeMap();
        userInfo.put("ID", user.getId());
        userInfo.put("User Name", user.getUserName());
        userInfo.put("Display Name", user.getDisplayName());
        userInfo.put("Birth Date", user.getBirthDate());
        userInfo.put("Email", user.getEmail());

        List<SortedMap> actions = new ArrayList();
        for (Action action : user.getActions()) {
            SortedMap actionMap = new TreeMap();
            actionMap.put("ID", action.getId());
            actionMap.put("Display Name", action.getDisplayName());
            actionMap.put("URL", action.getUrl());
            actionMap.put("Request Type", action.getRequestType());
            actions.add(actionMap);
        }
        userInfo.put("Actions", actions);

        List<SortedMap> groups = new ArrayList();
        for (Group group : user.getGroups()) {
            SortedMap accessesMap = new TreeMap();
            accessesMap.put("ID", group.getId());
            accessesMap.put("Display Name", group.getDisplayName());
            groups.add(accessesMap);
        }
        userInfo.put("Groups", groups);

        return userInfo;
    }

    //==================================================================================================================
    @RequestMapping(value = {AppStringResources.rr_actions}, method = {RequestMethod.GET, RequestMethod.POST})
    public List listAllActions() {

        logger.info("listAllActions:");
        List<Action> actions = managementService.getActionList();

        List actionsList = new ArrayList();
        for (Action action : actions) {

            SortedMap actionInfo = new TreeMap();
            actionInfo.put("ID", action.getId());
            actionInfo.put("Display Name", action.getDisplayName());
            actionInfo.put("URL", action.getUrl());
            actionInfo.put("Request Type", action.getRequestType());

            actionsList.add(actionInfo);
        }

        return actionsList;
    }
}
