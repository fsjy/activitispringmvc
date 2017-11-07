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
package org.activiti.engine.impl.bpmn.parser.factory;

import org.activiti.bpmn.model.*;
import org.activiti.engine.impl.bpmn.behavior.*;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.CompensateEventDefinition;
import org.activiti.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;

/**
 * 追加使得BusinessRuleTask可支持UEL写法的方法
 *
 * @author Yanglu
 */
public interface LocalActivityBehaviorFactory {

    // 增加可以识别UEL的RuleTaskDelegatExession方法
    // Add by Yanglu 2017.11.2
    public abstract ActivityBehavior createBusinessRuleTaskDelegateExpressionActivityBehavior(LocalBusinessRuleTask localBusinessRuleTask);

    // Add by Yanglu 2017.11.7
    // 修改BusinessRuleTask 为 LocalBusinessRuleTask
    public abstract ActivityBehavior createBusinessRuleTaskActivityBehavior(LocalBusinessRuleTask localBusinessRuleTask);

}