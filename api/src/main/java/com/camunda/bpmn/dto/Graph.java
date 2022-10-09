package com.camunda.bpmn.dto;

public class Graph extends AbstractGraph {
    public Node addNode(String vertex) {
        return super.addNode(new Node(vertex));
    }

    public Node findNode(String vertex) {
        return super.findNode(new Node(vertex));
    }

    public AbstractGraph addEdge(String from, String to) {
        return super.addEdge(new Node(from), new Node(to));
    }
}
