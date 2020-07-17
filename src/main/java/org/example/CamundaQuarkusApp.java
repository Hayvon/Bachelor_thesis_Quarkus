package org.example;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

import javax.enterprise.inject.Produces;
import javax.servlet.annotation.WebListener;

@ProcessApplication(name = "Quarkus Demo App")
@WebListener
@QuarkusMain
public class CamundaQuarkusApp extends ServletProcessApplication {
    public static void main(String[] args){
       Quarkus.run();
    }
}
