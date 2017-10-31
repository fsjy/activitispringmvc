package com.bmsmart.service.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 做最后一个总处理的Service
 * Add by Yanglu 2017.10.31
 */
public class LastDelegateService extends DelegateService implements JavaDelegate {

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

        ex(execution);


    }
}
