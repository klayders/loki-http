package com.stoloto.logs.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class ConfTest {

    @PostConstruct
    public void traceLog() {
        log.info("start");
    }
}
