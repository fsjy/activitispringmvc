package org.activiti.spring;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.bpmn.parser.handler.LocalBusinessRuleParseHandler;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.activiti.spring.autodeployment.AutoDeploymentStrategy;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 配置xml中加入preBpmnParseHandlers 的配置
 *
 * @author Yanglu
 */
public class LocalSpringProcessEngineConfiguration extends SpringProcessEngineConfiguration {

    /**
     * 加入自定义的<businessRuleTask>的parseHandler
     * 在{{@link ProcessEngineConfigurationImpl}的方法getDefaultDeployers中进行此方法调用
     *
     * @return
     */
    @Override
    public List<BpmnParseHandler> getPreBpmnParseHandlers() {
        List<BpmnParseHandler> bpmnParseHandlerList = new ArrayList<BpmnParseHandler>();
        bpmnParseHandlerList.add(new LocalBusinessRuleParseHandler());
        preBpmnParseHandlers = bpmnParseHandlerList;
        return preBpmnParseHandlers;
    }
}
