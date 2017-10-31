package com.bmsmart.service.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *  CRF训练模型服务
 *  Add by Yanglu 2017.10.26
 *
 */
public class CrfDelegateService implements JavaDelegate {

    /**
     * 需要实现activiti的execute接口
     *
     *
     * @param execution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {


        System.out.println("CRF model service executed : " + execution.toString());

        execution.setVariable("crfService", "crfService.value");



    }
}
