package com.camunda.bpmn.web.event;

import com.camunda.bpmn.dto.AbstractGraph;
import lombok.Value;

import java.util.Stack;

@Value
public class PathFoundEvent {
    Stack<AbstractGraph.Node> path;
}
