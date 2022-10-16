package com.camunda.bpmn.web.exceptions;

public class BpmnServiceException extends RuntimeException {
    public BpmnServiceException() {
        super();
    }

    public BpmnServiceException(String message) {
        super(message);
    }
}
