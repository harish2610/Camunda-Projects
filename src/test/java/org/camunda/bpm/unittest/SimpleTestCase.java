/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.unittest;

import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Meyer
 * @author Martin Schimak
 */
public class SimpleTestCase {

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    @Test
    @Deployment(resources = {"testProcess.bpmn"})
    public void shouldExecuteProcess() {
        // Given we create a new process instance
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("testProcess");
        // Then it should be active
        assertThat(processInstance).isActive();
        // And it should be the only instance
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        // And there should exist just a single task within that process instance
        assertThat(task(processInstance)).isNotNull();

        // When we complete that task
        complete(task(processInstance));
        // Then the process instance should be ended
        assertThat(processInstance).isEnded();

        System.out.println("Method shouldExecuteProcess() completed");
    }

    @Test
    @Deployment(resources = {"compensation-order-not-ok.bpmn"})
    public void compensationOrderNotOk() {
        // Given we create a new process instance
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("test-compensation");
        // Then it should be active
        assertThat(processInstance).isActive();
        // And it should be the only instance
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        // And there should exist just a single task within that process instance
        assertThat(task(processInstance)).isNotNull();

        // When we complete that task
        complete(task(processInstance));

        complete(task(processInstance));
        
        Long shouldCompensateFirstMilis = historyService().createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("compensate_first").singleResult().getStartTime().getTime();
        Long shouldCompensateSecondMilis = historyService().createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("compensate_second").singleResult().getStartTime().getTime();
        // Then the process instance should be ended
        assertThat(shouldCompensateFirstMilis < shouldCompensateSecondMilis).isEqualTo(true);
    }
    
    @Test
    @Deployment(resources = {"compensation-order-ok.bpmn"})
    public void compensationOrderOk() {
        // Given we create a new process instance
        ProcessInstance processInstance = runtimeService().startProcessInstanceByKey("test-compensation");
        // Then it should be active
        assertThat(processInstance).isActive();
        // And it should be the only instance
        assertThat(processInstanceQuery().count()).isEqualTo(1);
        // And there should exist just a single task within that process instance
        assertThat(task(processInstance)).isNotNull();

        // When we complete that task
        complete(task(processInstance));

        complete(task(processInstance));
        
        Long shouldCompensateFirstMilis = historyService().createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("compensate_first").singleResult().getStartTime().getTime();
        Long shouldCompensateSecondMilis = historyService().createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("compensate_second").singleResult().getStartTime().getTime();
        // Then the process instance should be ended
        assertThat(shouldCompensateFirstMilis < shouldCompensateSecondMilis).isEqualTo(true);
    }

}
