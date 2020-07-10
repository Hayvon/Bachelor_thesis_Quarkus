package org.example;

import io.agroal.api.AgroalDataSource;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

@ApplicationScoped
public class QuarkusProcessEngineConfiguration extends StandaloneProcessEngineConfiguration {

    public QuarkusProcessEngineConfiguration() {
        super();
        setDatabaseSchemaUpdate("true");
       //setJdbcUrl("jdbc:h2:file:C:/dockerVolume/QuarkusDB5");
        setJdbcUrl("jdbc:postgresql://localhost:5432/Quarkus");
        //setJdbcDriver("org.h2.Driver");
        setJdbcDriver("org.postgresql.Driver");
        setJdbcUsername("postgres");
        setJdbcPassword("postgres");
    }

}
