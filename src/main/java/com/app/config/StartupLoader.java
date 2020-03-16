package com.app.config;

import com.app.service.InitialDataService;
import com.app.service.impl.InitialDataServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupLoader implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LogManager.getLogger(InitialDataServiceImpl.class);

    private boolean alreadySetup = false;

    @Autowired
    private InitialDataService initialDataService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Start AUTO loader");
        if(!alreadySetup) {
            alreadySetup = initialDataService.initData();
        }
    }
}
