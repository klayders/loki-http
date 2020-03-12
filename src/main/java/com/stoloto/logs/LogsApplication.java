package com.stoloto.logs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LogsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogsApplication.class, args);
    }

}
