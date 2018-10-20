package com.parstasmim.mtni.dwf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication//(scanBasePackages = {"com.parstasmim.mtni.dwf"})
public class DwfApplication {

    public static void main(String[] args) {

        System.out.println("DWF Application Starting ...");
        ApplicationContext applicationContext =
                SpringApplication.run(DwfApplication.class, args);

        System.out.println("Application Bean Names:");
        for (String name : applicationContext.getBeanDefinitionNames()) {
            System.out.println(String.format("\t\t\t%s", name));
        }
        System.out.println("Application started successfully");

    }
}