package com.bmsmart.service.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CRF训练模型服务
 * Add by Yanglu 2017.10.26
 */
public class CrfDelegateService extends DelegateService implements JavaDelegate {

    /**
     * 需要实现activiti的execute接口
     *
     * @param execution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {


        // 获得本service所取得的variable
        execution.getVariableInstancesLocal();

        // 获得包括parent的所有的variable
        execution.getVariableInstances();


        System.out.println("-- CrfDelegateService START -- : " + execution.toString());

        execution.setVariable(getServiceName().concat("_output"), getServiceName().concat("_output"));

        //execution.setVariable("inputSecond", 2);

        System.out.println("-- CrfDelegateService END   -- : " + execution.toString());

    }
}
