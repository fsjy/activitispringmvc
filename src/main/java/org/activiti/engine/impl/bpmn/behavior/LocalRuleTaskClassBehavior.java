package org.activiti.engine.impl.bpmn.behavior;

import org.activiti.engine.delegate.*;
import org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

/**
 * Add by Yanglu 2017.11.5
 * 实现对RuleTask的定义class的执行以及后续处理的连续调用
 * 即使用leave(execution)方法实现后续处理的调用
 */
public class LocalRuleTaskClassBehavior extends TaskActivityBehavior implements BusinessRuleTaskDelegate, ActivityBehavior, ExecutionListener {


    protected BusinessRuleTaskDelegate businessRuleTaskDelegate;

    public void setBusinessRuleTaskDelegate(BusinessRuleTaskDelegate businessRuleTaskDelegate) {
        this.businessRuleTaskDelegate = businessRuleTaskDelegate;
    }

    public void addRuleVariableInputIdExpression(Expression inputId) {

        businessRuleTaskDelegate.addRuleVariableInputIdExpression(inputId);

    }

    public void addRuleIdExpression(Expression inputId) {

        businessRuleTaskDelegate.addRuleIdExpression(inputId);

    }

    public void setExclude(boolean exclude) {

        businessRuleTaskDelegate.setExclude(exclude);

    }

    public void setResultVariable(String resultVariableName) {

        businessRuleTaskDelegate.setResultVariable(resultVariableName);

    }

    //unchecked//
    public void notify(DelegateExecution execution) throws Exception {

        execute((ActivityExecution) execution);

    }

    public void execute(ActivityExecution execution) throws Exception {
        businessRuleTaskDelegate.execute(execution);
        leave(execution);
    }
}
