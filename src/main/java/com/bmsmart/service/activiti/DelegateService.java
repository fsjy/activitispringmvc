package com.bmsmart.service.activiti;

public class DelegateService {

    private String serviceName;


    public String getServiceName() {

        return this.getClass().getSimpleName();
    }
}
