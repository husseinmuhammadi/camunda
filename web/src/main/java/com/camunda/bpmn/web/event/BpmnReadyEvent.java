package com.camunda.bpmn.web.event;

import lombok.Value;

@Value
public class BpmnReadyEvent {
    String bpmn;
    String startNode;
    String endNode;
}
