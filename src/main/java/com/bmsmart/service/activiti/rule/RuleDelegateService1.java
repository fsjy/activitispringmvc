package com.bmsmart.service.activiti.rule;

import org.activiti.engine.delegate.BusinessRuleTaskDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.LocalBusinessRuleTaskDelegateImpl;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;


public class RuleDelegateService1 extends LocalBusinessRuleTaskDelegateImpl {

    @Override
    public void execute(ActivityExecution execution) throws Exception {




        System.out.println("Instance of " + this.getClass().getSimpleName() + ":");
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println(this.toString());
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println();

        System.out.println("Input value is:");
        System.out.println("++++++++++++++++++++++++++++++++");
        //System.out.println(this.expression.toString());
        getRuleVariableInputIdExpressions().forEach(new Consumer<Expression>() {
            @Override
            public void accept(Expression expression) {
                System.out.println(expression.getExpressionText());
            }
        });

        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println();




//        BasicDataSource dataSource = (BasicDataSource) ctx.getBean("dataSource");
//
//        System.out.println("Spring bean dataSource is:");
//        System.out.println("++++++++++++++++++++++++++++++++");
//        System.out.println(dataSource.toString());
//        System.out.println("++++++++++++++++++++++++++++++++");
//        System.out.println();
    }
}
