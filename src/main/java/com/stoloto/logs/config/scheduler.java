package com.stoloto.logs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableScheduling
@Component
public class scheduler {

    @Scheduled(fixedDelay = 20000)
    public void doIt() {
        log.info("start do work");
    }
}
