package com.bmsmart.service.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 *  前置处理，在网关之前处理选择，为网关选择分支提供input variable
 *  Add by Yanglu 2017.10.30
 *
 */
public class selectDelegateService implements JavaDelegate {

    /**
     * 需要实现activiti的execute接口
     *
     *
     * @param execution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {


        System.out.println("-- selectDelegateService START -- : " + execution.toString());

        int input = 0;
        System.out.println("setVariable input == " + input);
        execution.setVariable("input", input);
        execution.setVariable("inputSecond", 2);

        System.out.println("-- selectDelegateService END   -- : " + execution.toString());


    }
}
