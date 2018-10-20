package com.parstasmim.mtni.dwf.model.service;

import com.parstasmim.mtni.dwf.model.entity.Action;
import com.parstasmim.mtni.dwf.model.entity.Group;
import com.parstasmim.mtni.dwf.model.entity.User;
import com.parstasmim.mtni.dwf.model.repository.ActionRepository;
import com.parstasmim.mtni.dwf.model.repository.GroupRepository;
import com.parstasmim.mtni.dwf.model.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.persistence.EntityManager;
import java.sql.Blob;
import java.util.*;

@Service
@Transactional
public class ManagementServiceImpl implements ManagementService {

    protected static final Logger logger = LogManager.getLogger();
    @Autowired
    EntityManager entityManager;
    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private GroupRepository groupRepository;

    //========================================================================================================
//    @Override
//    public boolean isActionNeedAuthentication(String requestType, String actionUri) {
//        return actionRepository.countByRequestTypeAndUrlAndNeedAuthentication(requestType, actionUri, 1) == 1;
//    }

    //========================================================================================================
    @Override
    public boolean authenticateUser(String userName, String password) {
        int count = userRepository.countAllByUserNameAndPassword(userName, password);

        return count == 1;
    }

    //========================================================================================================
    @Override
    public boolean authorizeUser(String userName, String actionName) {
        Action action = actionRepository.findByDisplayName(actionName);
        User user = userRepository.findByUserName(userName);

        return user.getActions().contains(action);
    }

    //========================================================================================================
    @Override
    public List getUsersList() {
//        Page<User> users = userRepository.findAll(new PageRequest(1, 20));

        List<User> users = userRepository.findAll();
        return users;
    }

    //========================================================================================================
    @Override
    public boolean isUserExist(String userName) {
        return userRepository.countByUserName(userName) >= 1;
    }

    //========================================================================================================
    @Override
    public User getUserBy(String userName) {
        User user = userRepository.findByUserName(userName);

        return user;
    }

    //========================================================================================================
    @Override
    public Action getActionBy(String displayName) {
        Action action = actionRepository.findByDisplayName(displayName);
        return action;
    }

    //========================================================================================================
    @Override
    public Action getActionBy(Long id) {
        return null;
    }

    //========================================================================================================
    @Override
    public Action getActionBy(String requestType, String url) {
        Action action = actionRepository.findByRequestTypeAndUrl(requestType, url);
        return action;
    }

    //========================================================================================================
    @Override
    public User getUserBy(String userName, String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);

        return user;
    }

    //========================================================================================================
    @Override
    public User registerUser(String userName, String password, String email, Blob image) {
        User user = new User();

        user.setUserName(userName);


        user.setPassword(password);

        user.setEmail(email);
        user.setImage(image);

        return registerUser(user);
    }

    //========================================================================================================
    @Override
    public User registerUser(User user) {

        User persisted_user = userRepository.save(user);

        return persisted_user;
    }

    //========================================================================================================
    @Override
    public void assignUserActions(String userName, String... actions_names) {
        User user = userRepository.findByUserName(userName);

        Set<Action> new_actions = new HashSet();
        for (String action_name : actions_names) {
            Action action = actionRepository.findByDisplayName(action_name);
            new_actions.add(action);
        }

        for (Action new_action : new_actions) {
            user.getActions().add(new_action);
        }
    }

    //========================================================================================================
    @Override
    public void revokeUserActions(String userName, String... actions_names) {
        User user = userRepository.findByUserName(userName);

        Set<Action> new_actions = new HashSet();
        for (String action_name : actions_names) {
            new_actions.add(actionRepository.findByDisplayName(action_name));
        }

        for (Action new_action : new_actions) {
            user.getActions().remove(new_action);
        }
    }

    //========================================================================================================
    @Override
    public void joinUserToGroup(String userName, String groupName) {
        User user = userRepository.findByUserName(userName);
        Group group = groupRepository.findByDisplayName(groupName);

        user.getGroups().add(group);
    }

    //========================================================================================================
    @Override
    public void leaveUserFromGroup(String userName, String groupName) {
        User user = userRepository.findByUserName(userName);
        Group group = groupRepository.findByDisplayName(groupName);

        user.getGroups().remove(group);
    }

    //========================================================================================================
    @Override
    public void addAction(Action action) {
        actionRepository.save(action);
    }

    //========================================================================================================
    @Override
    public void removeAction(Action action) {
        actionRepository.delete(action);
    }

    //========================================================================================================
    @Override
    public void removeAction(Long id) {
        actionRepository.deleteById(id);
    }

    //========================================================================================================
    @Override
    public List<Action> getActionList() {
        return actionRepository.findAll();
    }

    @Override
    public List<Action> getAccessibleActionList() {
        return actionRepository.findByAccessibility(true);
    }

    //========================================================================================================
    @Override
    public void updateApplicationActions(RequestMappingHandlerMapping requestMappingHandlerMapping) throws Exception {
        logger.trace("App accesses are as below:");

        List<Action> currentActionsList = new ArrayList();

        Map<RequestMappingInfo, HandlerMethod> controllers = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : controllers.entrySet()) {

            Action action = new Action();
            String displayName = entry.getValue().getBean() + "/" + entry.getValue().getMethod().getName();

//            action.setNeedAuthentication(entry.getValue().getBean().toString().equalsIgnoreCase("indexController") ? true : false);

            action.setDisplayName(displayName);
            action.setAccessibility(true);
            action.setUrl(entry.getKey().getPatternsCondition().getPatterns().toString());
            action.setRequestType(entry.getKey().getMethodsCondition().getMethods().toString());

//            if (currentActionsList.contains(action)) {
//                throw new Exception(String.format("There is duplicate action display name: %s\nPlease make sure actions have unique display name.", displayName));
//            }

            currentActionsList.add(action);
            logger.trace(action);
        }

        List<Action> storedActionList = actionRepository.findAll();
        for (Action action : storedActionList) {
            action.setAccessibility(false);
            actionRepository.save(action);
        }

        for (Action action : currentActionsList) {

            if (storedActionList.contains(action)) {
                Action temp = storedActionList.get(storedActionList.indexOf(action));
                action.setId(temp.getId());
                action.setRegisteredDate(temp.getRegisteredDate());
            }
            actionRepository.save(action);

        }
    }

    //========================================================================================================
    @Override
    public void assignFullAccessToUser(String userName) {
        List<Action> appActionList = actionRepository.findAll();
        List<String> actions_names = new ArrayList();
        for (Action action : appActionList) {
            actions_names.add(action.getDisplayName());
        }
        logger.info("Admin Actions: " + actions_names);

        assignUserActions(userName, actions_names.toArray(new String[]{}));
    }

    //========================================================================================================
    @Override
    public void initializeSystemUser() {
        String adminUserName = env.getProperty("app.admin.username");
        String adminPassword = env.getProperty("app.admin.password");
        String appName = env.getProperty("app.name");

        if (!isUserExist(adminUserName)) {
            User user = new User();
            user.setUserName(adminUserName);
            user.setPassword(adminPassword);
            user.setDisplayName(String.format("%s [%s App User]", adminUserName, appName.toUpperCase()));

            registerUser(user);
        }

    }
}
