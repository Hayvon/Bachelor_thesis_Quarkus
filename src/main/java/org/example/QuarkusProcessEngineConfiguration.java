package org.example;

import io.agroal.api.AgroalDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

@ApplicationScoped
public class QuarkusProcessEngineConfiguration extends StandaloneProcessEngineConfiguration {

    //Configuration of the Process Engine for Quarkus
    public QuarkusProcessEngineConfiguration() {
        super();
        setDatabaseSchemaUpdate("true");
        setJdbcUrl("jdbc:postgresql://172.16.180.38:5432/Quarkus");
        setJdbcDriver("org.postgresql.Driver");
        setJdbcUsername("postgres");
        setJdbcPassword("postgres");
        setDatabaseSchemaUpdate("update");
    }

}
