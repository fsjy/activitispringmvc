package org.activiti.engine.impl.bpmn.behavior;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.*;
import org.activiti.engine.impl.LocalBusinessRuleTaskDelegateImpl;
import org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionUtil;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.delegate.JavaDelegateInvocation;
import org.activiti.engine.impl.delegate.RuleTaskDelegateInvocation;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import sun.net.ExtendedOptionsImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Add by Yanglu 2017.11.5
 * 实现对RuleTask的DelegateExpression定义的执行以及后续处理的连续调用
 * 即使用leave(execution)方法实现后续处理的调用
 */
public class LocalRuleTaskDelegateExpressionBehavior extends TaskActivityBehavior implements ActivityBehavior, ExecutionListener {


    protected BusinessRuleTaskDelegate businessRuleTaskDelegate;

    protected Expression expression;


    private HashSet<Expression> ruleVariableInputIdExpressions = new HashSet<>();
    private HashSet<Expression> ruleIdExpressions = new HashSet<>();
    protected String resultVariableName;
    protected boolean exclude;


    public void addRuleVariableInputIdExpression(Expression expression) {
        this.ruleVariableInputIdExpressions.add(expression);
    }

    public void addRuleIdExpression (Expression expression) {
        this.ruleIdExpressions.add(expression);
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public void setResultVariableName(String resultVariableName) {
        this.resultVariableName = resultVariableName;
    }


    public LocalRuleTaskDelegateExpressionBehavior(Expression expression) {
        this.expression = expression;
    }


    //unchecked//
    public void notify(DelegateExecution execution) throws Exception {

        execute((ActivityExecution) execution);

    }

    @Override
    public void execute(ActivityExecution execution) throws Exception {

        Object delegate = null;

        if (businessRuleTaskDelegate == null) {
            // 获得spring管理的bean
            delegate = DelegateExpressionUtil.resolveDelegateExpression(expression, execution, null);
            if (delegate instanceof BusinessRuleTaskDelegate) {

                businessRuleTaskDelegate = (BusinessRuleTaskDelegate) delegate;

                ((LocalBusinessRuleTaskDelegateImpl) businessRuleTaskDelegate).setRuleIdExpressions(this.ruleIdExpressions);
                ((LocalBusinessRuleTaskDelegateImpl) businessRuleTaskDelegate).setRuleVariableInputIdExpressions(this.ruleVariableInputIdExpressions);


                // Bug fixes Modified by Yanglu 在businessRuleTaskDelegate初始化后设置相应xml中定义的输入输出参数
                businessRuleTaskDelegate.setExclude(exclude);

                // 问题，如果采用原生的处理结构，需要增加RuleTaskDelegateInvocation的Class
                Context.getProcessEngineConfiguration()
                        .getDelegateInterceptor()
                        .handleInvocation(new RuleTaskDelegateInvocation(
                                (BusinessRuleTaskDelegate) businessRuleTaskDelegate, execution));

            } else {
                throw new ActivitiIllegalArgumentException("Delegate businessRuleTaskDelegate " + expression + "didn't resolve"
                        + BusinessRuleTaskDelegate.class + "by local logic modified by Darcular");
            }


        } else {
            throw new ActivitiIllegalArgumentException("BusinessRuleTaskDelegate in LocalRuleTaskDelegateExpressionBehavior is null");
        }

        leave(execution);
    }
}
