package com.bmsmart.service.activiti.java;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.runtime.Execution;

public class DelegateService {

    private String getServiceName() {

        return this.getClass().getSimpleName();
    }

    /**
     *  Thread-safe 不涉及成员变量
     *
     * @param execution
     */
    private void log(DelegateExecution execution) {

        // demo 设置本service的output参数
        execution.setVariable(execution.getCurrentActivityId().concat(getServiceName().concat("_output")), getServiceName().concat("_output"));
        System.out.println("| Variable :  " + execution.getCurrentActivityId().concat("_").concat(getServiceName().concat("_output")) + " -- |");
        System.out.println("| Instance :  " + execution.toString() + " -- |");
    }


    public void ex(DelegateExecution execution) {

        System.out.println("| ---------------------- START ---------------------- |");
        System.out.println("| Service  :  " + this.getServiceName() + " -- |");
        log(execution);
        System.out.println("| ----------------------  END  ---------------------- |");
        System.out.println();


    }


}
