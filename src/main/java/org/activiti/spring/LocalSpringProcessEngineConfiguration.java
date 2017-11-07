package org.activiti.spring;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.bpmn.parser.handler.LocalBusinessRuleParseHandler;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.parse.BpmnParseHandler;

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
     *
     * @param preBpmnParseHandlers
     * @return
     */
    @Override
    public ProcessEngineConfigurationImpl setPreBpmnParseHandlers(List<BpmnParseHandler> preBpmnParseHandlers) {

        List<BpmnParseHandler> bpmnParseHandlerList = new ArrayList<BpmnParseHandler>();

        bpmnParseHandlerList.add(new LocalBusinessRuleParseHandler());

        this.preBpmnParseHandlers = bpmnParseHandlerList;

        return this;
    }
}
