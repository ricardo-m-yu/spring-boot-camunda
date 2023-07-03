package com.sy.camunda.config;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class CamundaConfiguration {

//    private final DataSource dataSource;
//
//    private final PlatformTransactionManager transactionManager;
//
//
//    public CamundaConfiguration(DataSource dataSource,
//                                PlatformTransactionManager transactionManager) {
//        this.dataSource = dataSource;
//        this.transactionManager = transactionManager;
//    }
//
//    @Bean
//    public SpringProcessEngineConfiguration engineConfiguration(@Value("classpath*:/bpmn/*.bpmn") Resource[] deploymentResources) {
//        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
//        configuration.setProcessEngineName("engine");
//        configuration.setDataSource(dataSource);
//        configuration.setTransactionManager(transactionManager);
//        configuration.setDatabaseSchemaUpdate("true");
//        configuration.setJobExecutorActivate(false);
//        configuration.setDeploymentResources(deploymentResources);
//        configuration.setHistoryLevel(HistoryLevel.HISTORY_LEVEL_FULL);
//        List<ProcessEnginePlugin> plugins = configuration.getProcessEnginePlugins();
//        if (null == plugins) plugins = new ArrayList<>();
//        configuration.setProcessEnginePlugins(plugins);
//        return configuration;
//    }

}
