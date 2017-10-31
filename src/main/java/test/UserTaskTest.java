package test;

import org.activiti.engine.*;
import org.activiti.engine.impl.util.ReflectUtil;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.validation.ProcessValidatorFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Lu.yang
 * @Description:  Activiti初始化引擎调用例
 * @Date: 2017/10/13
 *
 */
public class UserTaskTest {

    private ProcessValidatorFactory processValidatorFactory = null;

    // <process id="my-process" isExecutable="true">
    private final static String ACTIVITI_PROCESS_ID = "my-process";


//    /**
//     *  对由xml生成的BpmnModel进行校验
//     *  返回错误List 如正确 List的size为0
//     *
//     * @param bpmnModel
//     * @return validator的错误List 如果有错List的size > 0
//     */
//    public List<ValidationError> validate(BpmnModel bpmnModel) {
//
//        if (this.processValidatorFactory == null) {
//            this.processValidatorFactory = new ProcessValidatorFactory();
//        }
//        ProcessValidator validator =processValidatorFactory.createDefaultProcessValidator();
//        return validator.validate(bpmnModel);
//
//    }
//
//
//    /**
//     *  由xml生成BpmnModel模型
//     *
//     * @param resource
//     * @return
//     */
//    public BpmnModel makeBpmnMode(String resource) throws UnsupportedEncodingException, XMLStreamException {
//
//        ClassLoader classLoader = ReflectUtil.getClassLoader();
//        InputStream xmlStream = classLoader.getResourceAsStream(resource);
//        XMLInputFactory xif= new XMLInputFactoryImpl();
//        InputStreamReader in = new InputStreamReader(xmlStream, "UTF-8");
//        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xif.createXMLStreamReader(in));
//
//        return null;
//    }





    public static void main(String[] args) throws Exception {


        /**
         *  #
         *  #
         *  #
         *  #
         *  #
         *  #
         *  #
         *  #
         *  #
         *
         *
         * 【Definition】
         *      发布流程定义到引擎
         *
         * 【Process】
         *      1 读入xml（流程图）
         *      2 deploy （发布）
         *
         *  【Q&A】
         *
         *      Q1 为什么要通过发布？
         *          xml文件是定义的蓝图，需要把图纸信息读入到引擎中，然后当需要使用的时候可以从引擎中取得
         *          实例进行使用
         *
         *      Q2 发布的方式有哪些？
         *          本文调用的方式是最基本方式，一般采用与spring的集成，在xml文件中定义ProcessEngine的bean，
         *          交给spring进行管理
         *
         */

        // 初始化引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获得repository服务
        RepositoryService repositoryService = processEngine.getRepositoryService();



        // 发布xml流程蓝图到服务中
        repositoryService.createDeployment()
                //.addClasspathResource("org/bmsmart/test/vacationRequest.bpmn20.xml")
                .addClasspathResource("testxml/serviceTask-activiti-expression-xml-data-transit-test.bpmn20.xml")
                //.addClasspathResource("org/bmsmart/test/process.bpmn20-2.xml")
                .deploy();



        // 【测试】计算当前实例的repositoryService的数量 即实例的数量
        System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());


        /**
         *  #########
         *          #
         *          #
         *          #
         *  #########
         *  #
         *  #
         *  #
         *  #########
         *
         *
         *  【Definition】
         *      启动流程实例
         *
         *  【Process】
         *      1 设定初始化的参数
         *      2 获取RuntimeService，即执行
         *      3 通过ProcessID启动实例
         *        ⚠⚠注意:使用的processDefinitionKey 即xml中定义的process id
         *
         */


        Map<String, Object> variables = new HashMap<String, Object>();

        // 参数设定
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        // 获得运行服务
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 获得实例（按xml中定义的ProcessID）


        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ACTIVITI_PROCESS_ID, variables);

        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process", variables);

        System.out.println("DefinitionId is : " + processInstance.getProcessDefinitionId());


        ClassLoader classLoader = ReflectUtil.getClassLoader();



//        InputStream xmlStream = classLoader.getResourceAsStream("org/bmsmart/test/process.bpmn20-2.xml");
//        XMLInputFactoryImpl xif= new XMLInputFactoryImpl();
//        InputStreamReader in = new InputStreamReader(xmlStream, "UTF-8");
//        //XMLStreamReader xtr = xif.createXMLStreamReader(in);
//        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xif.createXMLStreamReader(in));

        /**
         *  使用validate 进行xml的正确性验证
         *
         *
          */
        // BpmnModel bpmnModel =  repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
//        ProcessValidatorFactory factory = new ProcessValidatorFactory();
//        ProcessValidator validator =factory.createDefaultProcessValidator();
//        List<ValidationError> list = validator.validate(bpmnModel);



        //runtimeService.startProcessInstanceByKey("my-process", variables);

        System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());


        /**
         *  #########
         *          #
         *          #
         *          #
         *  #########
         *          #
         *          #
         *          #
         *  #########
         *
         *
         *  【Definition】
         *      启动任务服务
         *
         *  【Process】
         *      1
         *      2
         *
         *
         */

        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().list();


        System.out.println("/////////////////////Loop Start");
        for (Task task : tasks) {
            System.out.println("Task id : " + task.getId());
            System.out.println("Task available: " + task.getName());
        }

        System.out.println("/////////////////////Loop End");

        /**
         *  #       #
         *  #       #
         *  #       #
         *  #       #
         *  #########
         *          #
         *          #
         *          #
         *          #
         *
         *
         *  【Definition】
         *      启动任务服务
         *
         *  【Process】
         *      1
         *      2
         *
         *
         */


        Task task = tasks.get(0);

        System.out.println("Task id is :" + task.getId());
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("managerMotivation", "We have a tight deadline!");
        taskService.complete(task.getId(), taskVariables);
        
    }
}
