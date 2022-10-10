package com.camunda.bpmn.exceptions;

public class VertexNotFoundException extends RuntimeException{

    public VertexNotFoundException() {
        super();
    }

    public VertexNotFoundException(String message) {
        super(message);
    }
}
