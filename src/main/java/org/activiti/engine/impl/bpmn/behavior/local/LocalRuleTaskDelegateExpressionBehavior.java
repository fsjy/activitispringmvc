package org.activiti.engine.impl.bpmn.behavior.local;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.*;
import org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionUtil;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.delegate.JavaDelegateInvocation;
import org.activiti.engine.impl.delegate.RuleTaskDelegateInvocation;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

/**
 * Add by Yanglu 2017.11.5
 * 实现对RuleTask的DelegateExpression定义的执行以及后续处理的连续调用
 * 即使用leave(execution)方法实现后续处理的调用
 */
public class LocalRuleTaskDelegateExpressionBehavior extends TaskActivityBehavior implements BusinessRuleTaskDelegate, ActivityBehavior, ExecutionListener {


    protected BusinessRuleTaskDelegate businessRuleTaskDelegate;

    protected Expression expression;

    public LocalRuleTaskDelegateExpressionBehavior(Expression expression) {
        this.expression = expression;
    }

    public void addRuleVariableInputIdExpression(Expression inputId) {

        businessRuleTaskDelegate.addRuleVariableInputIdExpression(inputId);


    }

    public void addRuleIdExpression(Expression inputId) {

        businessRuleTaskDelegate.addRuleVariableInputIdExpression(inputId);

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

        Object delegate = null;

        if (businessRuleTaskDelegate == null) {
            // 获得spring管理的bean
            delegate = DelegateExpressionUtil.resolveDelegateExpression(expression, execution, null);
            if (delegate instanceof BusinessRuleTaskDelegate) {

                businessRuleTaskDelegate = (BusinessRuleTaskDelegate) delegate;

                // 问题，如果采用原生的处理结构，需要增加RuleTaskDelegateInvocation的Class
                Context.getProcessEngineConfiguration()
                        .getDelegateInterceptor()
                        .handleInvocation(new RuleTaskDelegateInvocation(
                                (BusinessRuleTaskDelegate) businessRuleTaskDelegate, execution));

            } else {
                throw new ActivitiIllegalArgumentException("Delegate expression " + expression + "didn't resolve"
                        + BusinessRuleTaskDelegate.class + "by local logic modified by Darcular");
            }


        } else {


        }


        leave(execution);
    }
}
