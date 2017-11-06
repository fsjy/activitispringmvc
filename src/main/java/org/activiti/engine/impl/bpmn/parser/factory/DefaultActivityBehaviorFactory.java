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
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.BusinessRuleTaskDelegate;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.*;
import org.activiti.engine.impl.bpmn.behavior.local.LocalRuleTaskClassBehavior;
import org.activiti.engine.impl.bpmn.behavior.local.LocalRuleTaskDelegateExpressionBehavior;
import org.activiti.engine.impl.bpmn.data.SimpleDataInputAssociation;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.CompensateEventDefinition;
import org.activiti.engine.impl.bpmn.parser.EventSubscriptionDeclaration;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.bpmn.webservice.MessageImplicitDataOutputAssociation;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Default implementation of the {@link ActivityBehaviorFactory}. Used when no
 * custom {@link ActivityBehaviorFactory} is injected on the
 * {@link ProcessEngineConfigurationImpl}.
 *
 * @author Joram Barrez
 */
public class DefaultActivityBehaviorFactory extends AbstractBehaviorFactory implements ActivityBehaviorFactory {

    // Start event
    public final static String EXCEPTION_MAP_FIELD = "mapExceptions";

    public NoneStartEventActivityBehavior createNoneStartEventActivityBehavior(StartEvent startEvent) {
        return new NoneStartEventActivityBehavior();
    }

    public EventSubProcessStartEventActivityBehavior createEventSubProcessStartEventActivityBehavior(StartEvent startEvent, String activityId) {
        return new EventSubProcessStartEventActivityBehavior(activityId);
    }

    // Task

    public TaskActivityBehavior createTaskActivityBehavior(Task task) {
        return new TaskActivityBehavior();
    }

    public ManualTaskActivityBehavior createManualTaskActivityBehavior(ManualTask manualTask) {
        return new ManualTaskActivityBehavior();
    }

    public ReceiveTaskActivityBehavior createReceiveTaskActivityBehavior(ReceiveTask receiveTask) {
        return new ReceiveTaskActivityBehavior();
    }

    public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask, TaskDefinition taskDefinition) {
        return new UserTaskActivityBehavior(userTask.getId(), taskDefinition);
    }

    // Service task

    public ClassDelegate createClassDelegateServiceTask(ServiceTask serviceTask) {
        Expression skipExpression;
        if (StringUtils.isNotEmpty(serviceTask.getSkipExpression())) {
            skipExpression = expressionManager.createExpression(serviceTask.getSkipExpression());
        } else {
            skipExpression = null;
        }
        return new ClassDelegate(serviceTask.getId(), serviceTask.getImplementation(),
                createFieldDeclarations(serviceTask.getFieldExtensions()), skipExpression, serviceTask.getMapExceptions());
    }

    public ServiceTaskDelegateExpressionActivityBehavior createServiceTaskDelegateExpressionActivityBehavior(ServiceTask serviceTask) {
        Expression delegateExpression = expressionManager.createExpression(serviceTask.getImplementation());
        Expression skipExpression;
        if (StringUtils.isNotEmpty(serviceTask.getSkipExpression())) {
            skipExpression = expressionManager.createExpression(serviceTask.getSkipExpression());
        } else {
            skipExpression = null;
        }
        return new ServiceTaskDelegateExpressionActivityBehavior(serviceTask.getId(), delegateExpression,
                skipExpression, createFieldDeclarations(serviceTask.getFieldExtensions()));
    }

    public ServiceTaskExpressionActivityBehavior createServiceTaskExpressionActivityBehavior(ServiceTask serviceTask) {
        Expression expression = expressionManager.createExpression(serviceTask.getImplementation());
        Expression skipExpression;
        if (StringUtils.isNotEmpty(serviceTask.getSkipExpression())) {
            skipExpression = expressionManager.createExpression(serviceTask.getSkipExpression());
        } else {
            skipExpression = null;
        }
        return new ServiceTaskExpressionActivityBehavior(serviceTask.getId(), expression, skipExpression, serviceTask.getResultVariableName());
    }

    public WebServiceActivityBehavior createWebServiceActivityBehavior(ServiceTask serviceTask) {
        return new WebServiceActivityBehavior();
    }

    public WebServiceActivityBehavior createWebServiceActivityBehavior(SendTask sendTask) {
        return new WebServiceActivityBehavior();
    }

    public MailActivityBehavior createMailActivityBehavior(ServiceTask serviceTask) {
        return createMailActivityBehavior(serviceTask.getId(), serviceTask.getFieldExtensions());
    }

    public MailActivityBehavior createMailActivityBehavior(SendTask sendTask) {
        return createMailActivityBehavior(sendTask.getId(), sendTask.getFieldExtensions());
    }

    protected MailActivityBehavior createMailActivityBehavior(String taskId, List<FieldExtension> fields) {
        List<FieldDeclaration> fieldDeclarations = createFieldDeclarations(fields);
        return (MailActivityBehavior) ClassDelegate.defaultInstantiateDelegate(MailActivityBehavior.class, fieldDeclarations);
    }

    // We do not want a hard dependency on Mule, hence we return ActivityBehavior and instantiate
    // the delegate instance using a string instead of the Class itself.
    public ActivityBehavior createMuleActivityBehavior(ServiceTask serviceTask, BpmnModel bpmnModel) {
        return createMuleActivityBehavior(serviceTask, serviceTask.getFieldExtensions(), bpmnModel);
    }

    public ActivityBehavior createMuleActivityBehavior(SendTask sendTask, BpmnModel bpmnModel) {
        return createMuleActivityBehavior(sendTask, sendTask.getFieldExtensions(), bpmnModel);
    }

    protected ActivityBehavior createMuleActivityBehavior(TaskWithFieldExtensions task, List<FieldExtension> fieldExtensions, BpmnModel bpmnModel) {
        try {

            Class<?> theClass = Class.forName("org.activiti.mule.MuleSendActivitiBehavior");
            List<FieldDeclaration> fieldDeclarations = createFieldDeclarations(fieldExtensions);
            return (ActivityBehavior) ClassDelegate.defaultInstantiateDelegate(theClass, fieldDeclarations);

        } catch (ClassNotFoundException e) {
            throw new ActivitiException("Could not find org.activiti.mule.MuleSendActivitiBehavior: ", e);
        }
    }

    // We do not want a hard dependency on Camel, hence we return ActivityBehavior and instantiate
    // the delegate instance using a string instead of the Class itself.
    public ActivityBehavior createCamelActivityBehavior(ServiceTask serviceTask, BpmnModel bpmnModel) {
        return createCamelActivityBehavior(serviceTask, serviceTask.getFieldExtensions(), bpmnModel);
    }

    public ActivityBehavior createCamelActivityBehavior(SendTask sendTask, BpmnModel bpmnModel) {
        return createCamelActivityBehavior(sendTask, sendTask.getFieldExtensions(), bpmnModel);
    }

    protected ActivityBehavior createCamelActivityBehavior(TaskWithFieldExtensions task, List<FieldExtension> fieldExtensions, BpmnModel bpmnModel) {
        try {
            Class<?> theClass = null;
            FieldExtension behaviorExtension = null;
            for (FieldExtension fieldExtension : fieldExtensions) {
                if ("camelBehaviorClass".equals(fieldExtension.getFieldName()) && StringUtils.isNotEmpty(fieldExtension.getStringValue())) {
                    theClass = Class.forName(fieldExtension.getStringValue());
                    behaviorExtension = fieldExtension;
                    break;
                }
            }

            if (behaviorExtension != null) {
                fieldExtensions.remove(behaviorExtension);
            }

            if (theClass == null) {
                // Default Camel behavior class
                theClass = Class.forName("org.activiti.camel.impl.CamelBehaviorDefaultImpl");
            }

            List<FieldDeclaration> fieldDeclarations = createFieldDeclarations(fieldExtensions);
            addExceptionMapAsFieldDeclaraion(fieldDeclarations, task.getMapExceptions());
            return (ActivityBehavior) ClassDelegate.defaultInstantiateDelegate(theClass, fieldDeclarations);

        } catch (ClassNotFoundException e) {
            throw new ActivitiException("Could not find org.activiti.camel.CamelBehavior: ", e);
        }
    }

    private void addExceptionMapAsFieldDeclaraion(List<FieldDeclaration> fieldDeclarations, List<MapExceptionEntry> mapExceptions) {
        FieldDeclaration exceptionMapsFieldDeclaration = new FieldDeclaration(EXCEPTION_MAP_FIELD, mapExceptions.getClass().toString(), mapExceptions);
        fieldDeclarations.add(exceptionMapsFieldDeclaration);

    }

    public ShellActivityBehavior createShellActivityBehavior(ServiceTask serviceTask) {
        List<FieldDeclaration> fieldDeclarations = createFieldDeclarations(serviceTask.getFieldExtensions());
        return (ShellActivityBehavior) ClassDelegate.defaultInstantiateDelegate(ShellActivityBehavior.class, fieldDeclarations);
    }


    /**
     * Add by Yanglu 2017.11.3
     * 增加识别RuleTask的DelegateExpression的定义方法
     *
     * @param businessRuleTask
     * @return
     */
    @Override
    public ActivityBehavior createBusinessRuleTaskDelegateExpressionActivityBehavior(BusinessRuleTask businessRuleTask) {


        LocalRuleTaskDelegateExpressionBehavior localRuleTaskDelegateExpressionBehavior =
                new LocalRuleTaskDelegateExpressionBehavior(expressionManager.createExpression(businessRuleTask.getImplementation()));


//        return new ServiceTaskDelegateExpressionActivityBehavior(serviceTask.getId(), delegateExpression,
//                skipExpression, createFieldDeclarations(serviceTask.getFieldExtensions()));


        return null;
    }

    /**
     * Modified by Yanglu 2017.11.3
     * 解决原生Activiti无法对RuleTaskService进行连续调用的问题
     * 【问题还原】：
     * 在原生Activiti中RuleTaskService如果声明了Class，则直接对此Class进行newInstance，
     * 在实际的activity调用的时候，即对ActivityBehavior进行调用后没有后处理来持续调用后续的flowNode
     * 所以在对BusinessRuleActivityBehavior进行初始化的时候通过LocalRuleTaskBehavior包装自己的RuleService类
     * 来实现后续的持续调用
     *
     * @param businessRuleTask
     * @return
     */
    public ActivityBehavior createBusinessRuleTaskActivityBehavior(BusinessRuleTask businessRuleTask) {
        // BusinessRuleTaskDelegate ruleActivity = null;
        LocalRuleTaskClassBehavior localRuleTaskClassBehavior = null;

        if (StringUtils.isNotEmpty(businessRuleTask.getClassName())) {
            try {
                Class<?> clazz = Class.forName(businessRuleTask.getClassName());
                // ruleActivity = (BusinessRuleTaskDelegate) clazz.newInstance();
                localRuleTaskClassBehavior = new LocalRuleTaskClassBehavior();

                localRuleTaskClassBehavior.setBusinessRuleTaskDelegate((BusinessRuleTaskDelegate) clazz.newInstance());

            } catch (Exception e) {
                throw new ActivitiException("Could not instantiate businessRuleTask (id:" + businessRuleTask.getId() + ") class: " +
                        businessRuleTask.getClassName(), e);
            }
        } else {
            localRuleTaskClassBehavior = new LocalRuleTaskClassBehavior();
        }

        for (String ruleVariableInputObject : businessRuleTask.getInputVariables()) {
            localRuleTaskClassBehavior.addRuleVariableInputIdExpression(expressionManager.createExpression(ruleVariableInputObject.trim()));
        }

        for (String rule : businessRuleTask.getRuleNames()) {
            localRuleTaskClassBehavior.addRuleIdExpression(expressionManager.createExpression(rule.trim()));
        }

        localRuleTaskClassBehavior.setExclude(businessRuleTask.isExclude());

        if (businessRuleTask.getResultVariableName() != null && businessRuleTask.getResultVariableName().length() > 0) {
            localRuleTaskClassBehavior.setResultVariable(businessRuleTask.getResultVariableName());
        } else {
            localRuleTaskClassBehavior.setResultVariable("org.activiti.engine.rules.OUTPUT");
        }

        return localRuleTaskClassBehavior;
    }

    // Script task

    public ScriptTaskActivityBehavior createScriptTaskActivityBehavior(ScriptTask scriptTask) {
        String language = scriptTask.getScriptFormat();
        if (language == null) {
            language = ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE;
        }
        return new ScriptTaskActivityBehavior(scriptTask.getId(), scriptTask.getScript(), language, scriptTask.getResultVariable(), scriptTask.isAutoStoreVariables());
    }

    // Gateways

    public ExclusiveGatewayActivityBehavior createExclusiveGatewayActivityBehavior(ExclusiveGateway exclusiveGateway) {
        return new ExclusiveGatewayActivityBehavior();
    }

    public ParallelGatewayActivityBehavior createParallelGatewayActivityBehavior(ParallelGateway parallelGateway) {
        return new ParallelGatewayActivityBehavior();
    }

    public InclusiveGatewayActivityBehavior createInclusiveGatewayActivityBehavior(InclusiveGateway inclusiveGateway) {
        return new InclusiveGatewayActivityBehavior();
    }

    public EventBasedGatewayActivityBehavior createEventBasedGatewayActivityBehavior(EventGateway eventGateway) {
        return new EventBasedGatewayActivityBehavior();
    }

    // Multi Instance

    public SequentialMultiInstanceBehavior createSequentialMultiInstanceBehavior(ActivityImpl activity, AbstractBpmnActivityBehavior innerActivityBehavior) {
        return new SequentialMultiInstanceBehavior(activity, innerActivityBehavior);
    }

    public ParallelMultiInstanceBehavior createParallelMultiInstanceBehavior(ActivityImpl activity, AbstractBpmnActivityBehavior innerActivityBehavior) {
        return new ParallelMultiInstanceBehavior(activity, innerActivityBehavior);
    }

    // Subprocess

    public SubProcessActivityBehavior createSubprocActivityBehavior(SubProcess subProcess) {
        return new SubProcessActivityBehavior();
    }

    // Call activity

    public CallActivityBehavior createCallActivityBehavior(CallActivity callActivity) {
        String expressionRegex = "\\$+\\{+.+\\}";

        CallActivityBehavior callActivityBehaviour = null;
        if (StringUtils.isNotEmpty(callActivity.getCalledElement()) && callActivity.getCalledElement().matches(expressionRegex)) {
            callActivityBehaviour = new CallActivityBehavior(expressionManager.createExpression(callActivity.getCalledElement()), callActivity.getMapExceptions());
        } else {
            callActivityBehaviour = new CallActivityBehavior(callActivity.getCalledElement(), callActivity.getMapExceptions());
        }
        callActivityBehaviour.setInheritVariables(callActivity.isInheritVariables());

        for (IOParameter ioParameter : callActivity.getInParameters()) {
            if (StringUtils.isNotEmpty(ioParameter.getSourceExpression())) {
                Expression expression = expressionManager.createExpression(ioParameter.getSourceExpression().trim());
                callActivityBehaviour.addDataInputAssociation(new SimpleDataInputAssociation(expression, ioParameter.getTarget()));
            } else {
                callActivityBehaviour.addDataInputAssociation(new SimpleDataInputAssociation(ioParameter.getSource(), ioParameter.getTarget()));
            }
        }

        for (IOParameter ioParameter : callActivity.getOutParameters()) {
            if (StringUtils.isNotEmpty(ioParameter.getSourceExpression())) {
                Expression expression = expressionManager.createExpression(ioParameter.getSourceExpression().trim());
                callActivityBehaviour.addDataOutputAssociation(new MessageImplicitDataOutputAssociation(ioParameter.getTarget(), expression));
            } else {
                callActivityBehaviour.addDataOutputAssociation(new MessageImplicitDataOutputAssociation(ioParameter.getTarget(), ioParameter.getSource()));
            }
        }

        return callActivityBehaviour;
    }

    // Transaction

    public TransactionActivityBehavior createTransactionActivityBehavior(Transaction transaction) {
        return new TransactionActivityBehavior();
    }

    // Intermediate Events

    public IntermediateCatchEventActivityBehavior createIntermediateCatchEventActivityBehavior(IntermediateCatchEvent intermediateCatchEvent) {
        return new IntermediateCatchEventActivityBehavior();
    }

    public IntermediateThrowNoneEventActivityBehavior createIntermediateThrowNoneEventActivityBehavior(ThrowEvent throwEvent) {
        return new IntermediateThrowNoneEventActivityBehavior();
    }

    public IntermediateThrowSignalEventActivityBehavior createIntermediateThrowSignalEventActivityBehavior(ThrowEvent throwEvent,
                                                                                                           Signal signal, EventSubscriptionDeclaration eventSubscriptionDeclaration) {
        return new IntermediateThrowSignalEventActivityBehavior(throwEvent, signal, eventSubscriptionDeclaration);
    }

    public IntermediateThrowCompensationEventActivityBehavior createIntermediateThrowCompensationEventActivityBehavior(ThrowEvent throwEvent,
                                                                                                                       CompensateEventDefinition compensateEventDefinition) {
        return new IntermediateThrowCompensationEventActivityBehavior(compensateEventDefinition);
    }

    // End events

    public NoneEndEventActivityBehavior createNoneEndEventActivityBehavior(EndEvent endEvent) {
        return new NoneEndEventActivityBehavior();
    }

    public ErrorEndEventActivityBehavior createErrorEndEventActivityBehavior(EndEvent endEvent, ErrorEventDefinition errorEventDefinition) {
        return new ErrorEndEventActivityBehavior(errorEventDefinition.getErrorCode());
    }

    public CancelEndEventActivityBehavior createCancelEndEventActivityBehavior(EndEvent endEvent) {
        return new CancelEndEventActivityBehavior();
    }

    public TerminateEndEventActivityBehavior createTerminateEndEventActivityBehavior(EndEvent endEvent) {
        return new TerminateEndEventActivityBehavior(endEvent);
    }

    // Boundary Events

    public BoundaryEventActivityBehavior createBoundaryEventActivityBehavior(BoundaryEvent boundaryEvent, boolean interrupting, ActivityImpl activity) {
        return new BoundaryEventActivityBehavior(interrupting, activity.getId());
    }

    public CancelBoundaryEventActivityBehavior createCancelBoundaryEventActivityBehavior(CancelEventDefinition cancelEventDefinition) {
        return new CancelBoundaryEventActivityBehavior();
    }

}
