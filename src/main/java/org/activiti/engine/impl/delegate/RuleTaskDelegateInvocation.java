package org.activiti.engine.impl.delegate;

import org.activiti.engine.delegate.BusinessRuleTaskDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class RuleTaskDelegateInvocation extends DelegateInvocation {

    protected final BusinessRuleTaskDelegate delegateInstance;
    protected final DelegateExecution execution;

    public RuleTaskDelegateInvocation(BusinessRuleTaskDelegate delegateInstance, DelegateExecution execution) {
        this.delegateInstance = delegateInstance;
        this.execution = execution;
    }

    protected void invoke() throws Exception {
        delegateInstance.execute((ActivityExecution) execution);
    }

    public Object getTarget() {
        return delegateInstance;
    }

}
