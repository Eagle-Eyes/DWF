package com.parstasmim.mtni.dwf.configuration;

import com.parstasmim.mtni.dwf.model.entity.Action;
import com.parstasmim.mtni.dwf.model.service.ManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

@Component
public class AppInitializer implements InitializingBean, DisposableBean {

    protected static final Logger logger = LogManager.getLogger();

    public static List<Action> appActions;

    @Autowired
    Environment env;

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    //========================================================================================================
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("update app actions ...");
        managementService.updateApplicationActions(requestMappingHandlerMapping);

        logger.info("initialize admin user ...");
        managementService.initializeSystemUser();

        logger.info("load app actions");
        appActions = managementService.getAccessibleActionList();

        logger.info("assign full access to admin");
        String adminUserName = env.getProperty("app.admin.username");
        managementService.assignFullAccessToUser(adminUserName);

    }

    //========================================================================================================
    @Override
    public void destroy() throws Exception {

    }
}
