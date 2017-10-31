package com.bmsmart.service.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Optional;

/**
 * 字典模型服务
 * Add by Yanglu 2017.10.26
 */
public class DicDelegateService extends DelegateService implements JavaDelegate {


    /**
     * 需要实现activiti的execute接口
     *
     * @param execution
     * @throws Exception
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ex(execution);

    }
}
