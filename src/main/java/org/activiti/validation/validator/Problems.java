/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.validation.validator;


/**
 * @author jbarrez
 */
public interface Problems {
	
	String ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE = "bmsmart-process-definition-not-executable";
	String PROCESS_DEFINITION_NOT_EXECUTABLE = "bmsmart-specific-process-definition-not-executable";
	
	String ASSOCIATION_INVALID_SOURCE_REFERENCE = "bmsmart-asscociation-invalid-source-reference";
	
	String ASSOCIATION_INVALID_TARGET_REFERENCE = "bmsmart-asscociation-invalid-target-reference";
	
	String EXECUTION_LISTENER_IMPLEMENTATION_MISSING = "bmsmart-execution-listener-implementation-missing";
	
	String EVENT_LISTENER_IMPLEMENTATION_MISSING = "bmsmart-event-listener-implementation-missing";
	String EVENT_LISTENER_INVALID_IMPLEMENTATION = "bmsmart-event-listener-invalid-implementation";
	String EVENT_LISTENER_INVALID_THROW_EVENT_TYPE = "bmsmart-event-listener-invalid-throw-event-type";
	
	String START_EVENT_MULTIPLE_FOUND = "bmsmart-start-event-multiple-found";
	String START_EVENT_INVALID_EVENT_DEFINITION = "bmsmart-start-event-invalid-event-definition";
	
	String SEQ_FLOW_INVALID_SRC = "bmsmart-seq-flow-invalid-src";
	String SEQ_FLOW_INVALID_TARGET = "bmsmart-seq-flow-invalid-target";
	
	String USER_TASK_LISTENER_IMPLEMENTATION_MISSING = "bmsmart-usertask-listener-implementation-missing";

	String SERVICE_TASK_INVALID_TYPE = "bmsmart-servicetask-invalid-type";
	String SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE = "bmsmart-servicetask-result-var-name-with-delegate";
	String SERVICE_TASK_MISSING_IMPLEMENTATION = "bmsmart-servicetask-missing-implementation";
	String SERVICE_TASK_WEBSERVICE_INVALID_OPERATION_REF = "bmsmart-servicetask-webservice-invalid-operation-ref";
	
	String SEND_TASK_INVALID_IMPLEMENTATION = "bmsmart-sendtask-invalid-implementation";
	String SEND_TASK_INVALID_TYPE = "bmsmart-sendtask-invalid-type";
	String SEND_TASK_WEBSERVICE_INVALID_OPERATION_REF = "bmsmart-send-webservice-invalid-operation-ref";
	
	String SCRIPT_TASK_MISSING_SCRIPT = "bmsmart-scripttask-missing-script";
	
	String MAIL_TASK_NO_RECIPIENT = "bmsmart-mailtask-no-recipient";
	String MAIL_TASK_NO_CONTENT = "bmsmart-mailtask-no-content";
	
	String SHELL_TASK_NO_COMMAND = "bmsmart-shelltask-no-command";
	String SHELL_TASK_INVALID_PARAM = "bmsmart-shelltask-invalid-param";
	
	String EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW = "bmsmart-exclusive-gateway-no-outgoing-seq-flow";
	String EXCLUSIVE_GATEWAY_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW = "bmsmart-exclusive-gateway-condition-not-allowed-on-single-seq-flow";
	String EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW = "bmsmart-exclusive-gateway-condition-on-seq-flow";
	String EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS = "bmsmart-exclusive-gateway-seq-flow-without-conditions";
	
	String EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS = "bmsmart-event-gateway-only-connected-to-intermediate-events";

	String BPMN_MODEL_TARGET_NAMESPACE_TOO_LONG = "bmsmart-bpmn-model-target-namespace-too-long";

	String PROCESS_DEFINITION_ID_TOO_LONG = "bmsmart-process-definition-id-too-long";
	String PROCESS_DEFINITION_NAME_TOO_LONG = "bmsmart-process-definition-name-too-long";
	String PROCESS_DEFINITION_DOCUMENTATION_TOO_LONG = "bmsmart-process-definition-documentation-too-long";

	String FLOW_ELEMENT_ID_TOO_LONG = "bmsmart-flow-element-id-too-long";

	String SUBPROCESS_MULTIPLE_START_EVENTS = "bmsmart-subprocess-multiple-start-event";
	String SUBPROCESS_START_EVENT_EVENT_DEFINITION_NOT_ALLOWED = "bmsmart-subprocess-start-event-event-definition-not-allowed";
	
	String EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION = "bmsmart-event-subprocess-invalid-start-event-definition";
	
	String BOUNDARY_EVENT_NO_EVENT_DEFINITION = "bmsmart-boundary-event-no-event-definition";
	String BOUNDARY_EVENT_INVALID_EVENT_DEFINITION = "bmsmart-boundary-event-invalid-event-definition";
	String BOUNDARY_EVENT_CANCEL_ONLY_ON_TRANSACTION = "bmsmart-boundary-event-cancel-only-on-transaction";
	String BOUNDARY_EVENT_MULTIPLE_CANCEL_ON_TRANSACTION = "bmsmart-boundary-event-multiple-cancel-on-transaction";
	
	String INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION = "bmsmart-intermediate-catch-event-no-eventdefinition";
	String INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION = "bmsmart-intermediate-catch-event-invalid-eventdefinition";
	
	String ERROR_MISSING_ERROR_CODE = "bmsmart-error-missing-error-code";
	String EVENT_MISSING_ERROR_CODE = "bmsmart-event-missing-error-code";
	String EVENT_TIMER_MISSING_CONFIGURATION = "bmsmart-event-timer-missing-configuration";
	
	String THROW_EVENT_INVALID_EVENTDEFINITION = "bmsmart-throw-event-invalid-eventdefinition";
	
	String MULTI_INSTANCE_MISSING_COLLECTION = "bmsmart-multi-instance-missing-collection";
	
	String MESSAGE_MISSING_NAME = "bmsmart-message-missing-name";
	String MESSAGE_INVALID_ITEM_REF = "bmsmart-message-invalid-item-ref";
	String MESSAGE_EVENT_MISSING_MESSAGE_REF = "bmsmart-message-event-missing-message-ref";
	String MESSAGE_EVENT_INVALID_MESSAGE_REF = "bmsmart-message-event-invalid-message-ref";
	String MESSAGE_EVENT_MULTIPLE_ON_BOUNDARY_SAME_MESSAGE_ID = "bmsmart-message-event-multiple-on-boundary-same-message-id";
	
	String OPERATION_INVALID_IN_MESSAGE_REFERENCE = "bmsmart-operation-invalid-in-message-reference";
	
	String SIGNAL_EVENT_MISSING_SIGNAL_REF = "bmsmart-signal-event-missing-signal-ref";
	String SIGNAL_EVENT_INVALID_SIGNAL_REF = "bmsmart-signal-event-invalid-signal-ref";
	
	String COMPENSATE_EVENT_INVALID_ACTIVITY_REF = "bmsmart-compensate-event-invalid-activity-ref";
	String COMPENSATE_EVENT_MULTIPLE_ON_BOUNDARY = "bmsmart-compensate-event-multiple-on-boundary";
	
	String SIGNAL_MISSING_ID = "bmsmart-signal-missing-id";
	String SIGNAL_MISSING_NAME = "bmsmart-signal-missing-name";
	String SIGNAL_DUPLICATE_NAME = "bmsmart-signal-duplicate-name";
	String SIGNAL_INVALID_SCOPE = "bmsmart-signal-invalid-scope";
	
	String DATA_ASSOCIATION_MISSING_TARGETREF = "bmsmart-data-association-missing-targetref";
	
	String DATA_OBJECT_MISSING_NAME = "bmsmart-data-object-missing-name";
	
	String END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION = "bmsmart-end-event-cancel-only-inside-transaction";
	
	String DI_INVALID_REFERENCE = "bmsmart-di-invalid-reference";
	String DI_DOES_NOT_REFERENCE_FLOWNODE = "bmsmart-di-does-not-reference-flownode";
	String DI_DOES_NOT_REFERENCE_SEQ_FLOW = "bmsmart-di-does-not-reference-seq-flow";
	
}
