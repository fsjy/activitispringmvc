package com.bmsmart.service.local.impl;

import com.bmsmart.model.User;
import com.bmsmart.service.local.LocalService;
import com.bmsmart.service.local.entities.LocalTaskServiceResult;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 */
public class LocalTaskServiceImpl implements LocalService{

    public static AtomicInteger INSTANCE_COUNT = new AtomicInteger(0);

    public LocalTaskServiceImpl() {
        INSTANCE_COUNT.incrementAndGet();
    }

    /**
     *
     * @param start 接收前一Service运行结果
     * @return 返回后一service运行所需输入
     */
    @Override
    public String getLocalTaskServiceResult(String start) {

        LocalTaskServiceResult result = new LocalTaskServiceResult();

        List<User> userList = new ArrayList<User>();

        userList.


        return String.valueOf(INSTANCE_COUNT.incrementAndGet());




    }
}
