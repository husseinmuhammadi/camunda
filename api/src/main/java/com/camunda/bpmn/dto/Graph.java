package com.camunda.bpmn.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class Graph extends AbstractGraph {
    public Node addNode(String vertex) {
        return super.addNode(new Node(vertex));
    }

    public Node findNode(String vertex) {
        return super.findNode(new Node(vertex));
    }

    public Graph addEdge(String from, String to) {
        super.addEdge(new Node(from), new Node(to));
        return this;
    }

    public Node[] dfsRecursive(String value) {
        Set<Node> visited = new LinkedHashSet<>();
        super.dfsRecursive(findNode(new Node(value)), visited);
        return visited.toArray(new Node[0]);
    }
}
