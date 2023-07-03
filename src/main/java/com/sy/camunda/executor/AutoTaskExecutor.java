package com.sy.camunda.executor;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AutoTaskExecutor{


    @Bean
    JavaDelegate t107() {
        return execution -> {

            log.info("...............");
            log.info("t107 execute ...");

            log.info("t107 finish ...");
        };
    }

    @Bean
    JavaDelegate t701() {
        return execution -> {
            log.info("...............");
            log.info("t701 execute ...");

            log.info("t701 finish ...");
        };
    }


    @Bean
    JavaDelegate t114() {
        return execution -> {

            log.info("...............");
            log.info("t114 execute ...");


            log.info("t114 finish ...");
        };
    }


    @Bean
    JavaDelegate t117() {
        return execution -> {

            log.info("...............");
            log.info("t117 execute ...");

            log.info("t117 finish ...");
        };
    }


    @Bean
    JavaDelegate t119() {
        return execution -> {

            log.info("...............");
            log.info("t119 execute ...");


            log.info("t119 finish ...");
        };
    }



}