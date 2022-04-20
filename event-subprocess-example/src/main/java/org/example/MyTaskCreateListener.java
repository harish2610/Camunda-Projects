package org.example;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class MyTaskCreateListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        //String assignee = "demo";
        //delegateTask.setAssignee((String) delegateTask.getVariable(assignee));
    }
}