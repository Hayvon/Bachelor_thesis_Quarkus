package org.example;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.EmbeddedProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;

import javax.servlet.annotation.WebListener;

@ProcessApplication(name = "Quarkus Demo App")
@WebListener
public class CamundaQuarkusApp extends ServletProcessApplication {
}
