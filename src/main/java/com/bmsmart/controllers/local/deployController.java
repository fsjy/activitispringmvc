package com.bmsmart.controllers.local;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class deployController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 测试使用在实现Delegate的class中使用variable来传递参数的 TaskService运行入口
     * 并且加入网关的条件选择路径，测试传递条件值${input}是否正确
     * 测试使用Parallel网关
     * <p>
     * ignore "POST" and "GET"
     *
     * @return
     */
    @RequestMapping(value = "local/deploy", method = RequestMethod.POST)
    public String executeRuleService(String modelId) throws IOException {


        String id = modelId;


        byte[] bytes = repositoryService.getModelEditorSource(modelId);

        String strOut = new String(bytes, "UTF-8");

        System.out.println(strOut);

        strOut = strOut.replace("BusinessRule_TEST2", "BusinessRule").replace("BusinessRule_TEST", "BusinessRule");


        ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(strOut);

        deployModelerModel(modelNode);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("自定义_BMS_", "");

        return "modellist";

    }

    protected void deployModelerModel(final ObjectNode modelNode) {
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        String processName = "temp_" + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name("temp_name")
                .addString(processName, new String(bpmnBytes))
                .deploy();

        //ExplorerApp.get().getViewManager().showDeploymentPage(deployment.getId());
    }

}
