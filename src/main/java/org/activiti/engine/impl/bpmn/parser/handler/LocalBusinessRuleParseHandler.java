package org.activiti.engine.impl.bpmn.parser.handler;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.LocalBusinessRuleTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.LocalActivityBehaviorFactory;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

/**
 * 根据LocalBusinessRuleTask定义DelegateExpress以及class不同
 * 调用不同activityBehaviorFactory中的create方法
 *
 * @author Yanglu
 */
public class LocalBusinessRuleParseHandler extends AbstractActivityBpmnParseHandler<LocalBusinessRuleTask> {
    public LocalBusinessRuleParseHandler() {
    }

    public Class<? extends BaseElement> getHandledType() {
        return BusinessRuleTask.class;
    }

    protected void executeParse(BpmnParse bpmnParse, LocalBusinessRuleTask localBusinessRuleTask) {
        ActivityImpl activity = this.createActivityOnCurrentScope(bpmnParse, localBusinessRuleTask, "businessRuleTask");
        activity.setAsync(localBusinessRuleTask.isAsynchronous());
        activity.setExclusive(!localBusinessRuleTask.isNotExclusive());


        ActivityBehaviorFactory localActivityBehaviorFactory = null;

        // 判断是否为DelegateExpression模式定义 activiti:delegateExpression
        // 如果不是DelegateExpression模式，则按照原有logic进行处理
        if (ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equalsIgnoreCase(localBusinessRuleTask.getImplementationType())) {
            // 判断是否符合LocalActivityBehaviorFactory规则
            if (bpmnParse.getActivityBehaviorFactory() instanceof LocalActivityBehaviorFactory) {
                activity.setActivityBehavior(((LocalActivityBehaviorFactory) bpmnParse.getActivityBehaviorFactory()).createBusinessRuleTaskDelegateExpressionActivityBehavior(localBusinessRuleTask));
            } else {
                // 定义为DelegateExpression模式默认执行if分支，如果都到这个分支应该是有问题
                activity.setActivityBehavior(((LocalActivityBehaviorFactory) bpmnParse.getActivityBehaviorFactory()).createBusinessRuleTaskActivityBehavior(localBusinessRuleTask));
            }
        } else {
            activity.setActivityBehavior(((LocalActivityBehaviorFactory) bpmnParse.getActivityBehaviorFactory()).createBusinessRuleTaskActivityBehavior(localBusinessRuleTask));
        }

    }
}
