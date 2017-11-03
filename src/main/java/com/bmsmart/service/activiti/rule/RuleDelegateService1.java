package com.bmsmart.service.activiti.rule;

import org.activiti.engine.delegate.BusinessRuleTaskDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;


public class RuleDelegateService1 implements BusinessRuleTaskDelegate, ApplicationContextAware {


    private Expression expression;

    private static ApplicationContext ctx;


    @Override
    public void addRuleVariableInputIdExpression(Expression inputId) {
        this.expression = inputId;
    }

    @Override
    public void addRuleIdExpression(Expression inputId) {

    }

    @Override
    public void setExclude(boolean exclude) {

    }

    @Override
    public void setResultVariable(String resultVariableName) {
        String x = resultVariableName;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    @Override
    public void execute(ActivityExecution execution) throws Exception {
        System.out.println("Instance of "+ this.getClass().getSimpleName() +":");
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println(this.toString());
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println();

        System.out.println("Input value is:");
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println(this.expression.toString());
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
