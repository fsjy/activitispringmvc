//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.activiti.bpmn.model;

import java.util.ArrayList;
import java.util.List;

public class BusinessRuleTask extends Task {
    protected String resultVariableName;
    protected boolean exclude;
    protected List<String> ruleNames = new ArrayList();
    protected List<String> inputVariables = new ArrayList();
    protected String className;

    // Add by Yanglu 2017.11.3 增加implementation支持
    protected String implementation;

    // Add by Yanglu 2017.11.3 增加implementation支持
    public String getImplementation() {
        return implementation;
    }

    // Add by Yanglu 2017.11.3 增加implementation支持
    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public BusinessRuleTask() {

    }

    public boolean isExclude() {
        return this.exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public String getResultVariableName() {
        return this.resultVariableName;
    }

    public void setResultVariableName(String resultVariableName) {
        this.resultVariableName = resultVariableName;
    }

    public List<String> getRuleNames() {
        return this.ruleNames;
    }

    public void setRuleNames(List<String> ruleNames) {
        this.ruleNames = ruleNames;
    }

    public List<String> getInputVariables() {
        return this.inputVariables;
    }

    public void setInputVariables(List<String> inputVariables) {
        this.inputVariables = inputVariables;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BusinessRuleTask clone() {
        BusinessRuleTask clone = new BusinessRuleTask();
        clone.setValues(this);
        return clone;
    }

    public void setValues(BusinessRuleTask otherElement) {
        super.setValues(otherElement);
        this.setResultVariableName(otherElement.getResultVariableName());
        this.setExclude(otherElement.isExclude());
        this.setClassName(otherElement.getClassName());
        this.ruleNames = new ArrayList(otherElement.getRuleNames());
        this.inputVariables = new ArrayList(otherElement.getInputVariables());
    }
}
