package org.example;


import io.quarkus.runtime.Startup;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.camunda.bpm.engine.ProcessEngine;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
@Startup
public class FixDataSource {

    @Inject
    ProcessEngine processEngine;

    //Manual modification of the "maxConnectionPool" of Camunda BPM. Dramatically improves the throughput of the application
    @PostConstruct
    void onStartup() {
        DataSource dataSource = processEngine.getProcessEngineConfiguration().getDataSource();
        PooledDataSource pds = (PooledDataSource) dataSource;
        System.out.println("maxactive:" + pds.getPoolMaximumActiveConnections());
        pds.setPoolMaximumActiveConnections(5);
    }
}