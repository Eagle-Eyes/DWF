package com.parstasmim.mtni.dwf.model.service;

import com.parstasmim.mtni.dwf.model.entity.Action;
import com.parstasmim.mtni.dwf.model.entity.User;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.sql.Blob;
import java.util.List;

public interface ManagementService {

    public void addAction(Action action);

    public void removeAction(Action action);

    public void removeAction(Long id);

    public List<Action> getActionList();

    public List<Action> getAccessibleActionList();

    public void updateApplicationActions(RequestMappingHandlerMapping requestMappingHandlerMapping) throws Exception;

    public boolean authenticateUser(String userName, String password);

//    public boolean isActionNeedAuthentication(String actionUri, String requestType);

    public boolean authorizeUser(String userName, String actionName);

    public List<User> getUsersList();

    public boolean isUserExist(String userName);

//    public Map getUserBy(String userName);

    public User getUserBy(String userName);

    public Action getActionBy(String displayName);

    public Action getActionBy(Long id);

    public Action getActionBy(String requestType, String url);

    public User getUserBy(String userName, String password);

    public User registerUser(String userName, String password, String email, Blob image);

    public User registerUser(User user);

    public void assignUserActions(String userName, String... actions_names);

    public void revokeUserActions(String userName, String... actions_names);

    public void joinUserToGroup(String userName, String groupName);

    public void leaveUserFromGroup(String userName, String groupName);

    public void assignFullAccessToUser(String adminUserName);

    public void initializeSystemUser();

}