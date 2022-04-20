package org.example;

import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class MyDecisionResultListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
//        DmnDecisionResult decisionResult = (DmnDecisionResult) execution.getVariable("decisionResult");
//        String assignee = (String) decisionResult.getSingleResult().get("assignee");
//        String candidateGroups = (String) decisionResult.getSingleResult().get("candidateGroups");
//
//        execution.setVariable("assignee", assignee);
//        execution.setVariable("candidateGroups", candidateGroups);
    }
}