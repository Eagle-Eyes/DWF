package com.parstasmim.mtni.dwf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaRepositories(basePackages = {"com.parstasmim.mtni.dwf.model"})
@EnableTransactionManagement
class AppConfiguration implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "authenticationInterceptor")
    private HandlerInterceptor authenticationInterceptor;

    @Autowired
    @Qualifier(value = "authorizationInterceptor")
    private HandlerInterceptor authorizationInterceptor;

    private String[] public_paths = new String[]{
            AppStringResources.empty,
            AppStringResources.root,
            AppStringResources.rr_public_index1,
            AppStringResources.rr_public_index2,
            AppStringResources.rr_public_index3,
            AppStringResources.rr_public_login,
            AppStringResources.rr_public_logout,
            AppStringResources.rr_public_error
    };

    //========================================================================================================
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).order(Ordered.HIGHEST_PRECEDENCE).excludePathPatterns(public_paths);
        registry.addInterceptor(authorizationInterceptor).order(Ordered.LOWEST_PRECEDENCE).excludePathPatterns(public_paths);
    }

    // ========================================================================================================
//    @Bean
//    public HandlerInterceptor authenticationInterceptor() {
//        return new AuthenticationInterceptor();
//    }

    //
//    //========================================================================================================
//    @Bean
//    public HandlerInterceptor authorizationInterceptor() {
//        return new AuthorizationInterceptor();
//    }
//
//    //========================================================================================================
//    @Bean
//    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//        return new RequestMappingHandlerMapping();
//    }

}
