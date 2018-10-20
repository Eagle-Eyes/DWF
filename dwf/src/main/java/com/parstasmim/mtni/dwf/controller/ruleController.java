package com.parstasmim.mtni.dwf.controller;

import com.parstasmim.mtni.dwf.configuration.AppStringResources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppStringResources.rr_rules1)
public class ruleController {
    protected static final Logger logger = LogManager.getLogger();

    @RequestMapping(value = {AppStringResources.empty})
    public String index() {
        return "Rule Management";
    }
}
