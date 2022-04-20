package org.example;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SetVariables implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
        String var = (String) execution.getVariable("employeeName");
        var = var.toUpperCase();
        System.out.println(var);
        //execution.setVariable("input", var);
    }
}
