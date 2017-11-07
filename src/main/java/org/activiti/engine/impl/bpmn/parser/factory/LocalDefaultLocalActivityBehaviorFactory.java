package org.activiti.engine.impl.bpmn.parser.factory;

import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.LocalBusinessRuleTask;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.BusinessRuleTaskDelegate;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.LocalRuleTaskClassBehavior;
import org.activiti.engine.impl.bpmn.behavior.LocalRuleTaskDelegateExpressionBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.apache.commons.lang3.StringUtils;


/**
 * 增加支持DelegateExpress的UEL声明的behavior处理
 *
 * @author Yanglu
 */
public class LocalDefaultLocalActivityBehaviorFactory extends DefaultActivityBehaviorFactory implements LocalActivityBehaviorFactory {

    /**
     * Add by Yanglu 2017.11.3
     * 增加识别RuleTask的DelegateExpression的定义方法
     *
     * @param localBusinessRuleTask
     * @return
     */
    public ActivityBehavior createBusinessRuleTaskDelegateExpressionActivityBehavior(LocalBusinessRuleTask localBusinessRuleTask) {

        Expression delegateExpression = expressionManager.createExpression(localBusinessRuleTask.getImplementation());

        LocalRuleTaskDelegateExpressionBehavior localRuleTaskDelegateExpressionBehavior =  new LocalRuleTaskDelegateExpressionBehavior(delegateExpression);


        // Bug fixes Modified by Yanglu 2017.11.7 忘记加参数设定
        for (String ruleVariableInputObject : localBusinessRuleTask.getInputVariables()) {
            localRuleTaskDelegateExpressionBehavior.addRuleVariableInputIdExpression(expressionManager.createExpression(ruleVariableInputObject.trim()));
        }

        for (String rule : localBusinessRuleTask.getRuleNames()) {
            localRuleTaskDelegateExpressionBehavior.addRuleIdExpression(expressionManager.createExpression(rule.trim()));
        }

        localRuleTaskDelegateExpressionBehavior.setExclude(localBusinessRuleTask.isExclude());

        if (localBusinessRuleTask.getResultVariableName() != null && localBusinessRuleTask.getResultVariableName().length() > 0) {
            localRuleTaskDelegateExpressionBehavior.setResultVariable(localBusinessRuleTask.getResultVariableName());
        } else {
            localRuleTaskDelegateExpressionBehavior.setResultVariable("org.activiti.engine.rules.OUTPUT");
        }

        return localRuleTaskDelegateExpressionBehavior;

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
     * @param localBusinessRuleTask
     * @return
     */
    @Override
    public ActivityBehavior createBusinessRuleTaskActivityBehavior(LocalBusinessRuleTask localBusinessRuleTask) {

        LocalRuleTaskClassBehavior localRuleTaskClassBehavior = null;

        if (StringUtils.isNotEmpty(localBusinessRuleTask.getClassName())) {
            try {
                Class<?> clazz = Class.forName(localBusinessRuleTask.getClassName());
                //BusinessRuleTaskDelegate ruleActivity = (BusinessRuleTaskDelegate) clazz.newInstance();
                localRuleTaskClassBehavior = new LocalRuleTaskClassBehavior();

                localRuleTaskClassBehavior.setBusinessRuleTaskDelegate((BusinessRuleTaskDelegate) clazz.newInstance());

            } catch (Exception e) {
                throw new ActivitiException("Could not instantiate businessRuleTask (id:" + localBusinessRuleTask.getId() + ") class: " +
                        localBusinessRuleTask.getClassName(), e);
            }
        } else {
            localRuleTaskClassBehavior = new LocalRuleTaskClassBehavior();
        }

        for (String ruleVariableInputObject : localBusinessRuleTask.getInputVariables()) {
            localRuleTaskClassBehavior.addRuleVariableInputIdExpression(expressionManager.createExpression(ruleVariableInputObject.trim()));
        }

        for (String rule : localBusinessRuleTask.getRuleNames()) {
            localRuleTaskClassBehavior.addRuleIdExpression(expressionManager.createExpression(rule.trim()));
        }

        localRuleTaskClassBehavior.setExclude(localBusinessRuleTask.isExclude());

        if (localBusinessRuleTask.getResultVariableName() != null && localBusinessRuleTask.getResultVariableName().length() > 0) {
            localRuleTaskClassBehavior.setResultVariable(localBusinessRuleTask.getResultVariableName());
        } else {
            localRuleTaskClassBehavior.setResultVariable("org.activiti.engine.rules.OUTPUT");
        }

        return localRuleTaskClassBehavior;
    }


}
