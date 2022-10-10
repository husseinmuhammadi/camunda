package com.camunda.bpmn.dto;

import com.camunda.bpmn.exceptions.VertexNotFoundException;

import java.util.*;

public abstract class AbstractGraph {

    private final Set<Node> nodes = new HashSet<>();

    protected Node addNode(Node node) {
        return this.nodes.add(node) ? node : findNode(node);
    }

    protected Node findNode(Node node) {
        if (this.nodes.contains(node)) {
            Iterator<Node> iterator = this.nodes.iterator();
            while (iterator.hasNext()) {
                Node next = iterator.next();
                if (node.equals(next))
                    return next;
            }
        }

        throw new VertexNotFoundException("Vertex not found " + node.value);
    }

    protected AbstractGraph addEdge(Node from, Node to) {
        this.addNode(from).addNeighbour(to);
        return this;
    }

    public class Node {
        private final String value;
        private final Set<Node> neighbours = new HashSet<>();

        public Node(String value) {
            this.value = value;
        }

        public Node addNeighbour(String value) {
            return addNeighbour(new Node(value));
        }

        private Node addNeighbour(Node node) {
            this.neighbours.add(AbstractGraph.this.addNode(node));
            return this;
        }

        public String getValue() {
            return value;
        }

        public Set<Node> getNeighbours() {
            return neighbours;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public Node[] dfsIterative(String value) {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new LinkedHashSet<>();

        stack.push(this.findNode(new Node(value)));

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            visited.add(node);

            for (Node neighbour : node.getNeighbours()) {
                if (!visited.contains(neighbour))
                    stack.push(neighbour);
            }
        }

        return visited.toArray(new Node[0]);
    }

    protected void dfsRecursive(Node start, final Set<Node> visited) {
        visited.add(start);

        for (Node node : start.getNeighbours()) {
            if (!visited.contains(node)) {
                dfsRecursive(node, visited);
            }
        }
    }

    public Node[] bfs(String value) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new LinkedHashSet<>();

        Node start = findNode(new Node(value));

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node node = queue.remove();

            for (Node neighbour : node.getNeighbours()) {
                if (!visited.contains(neighbour)) {
                    queue.add(neighbour);
                    visited.add(neighbour);
                }
            }
        }

        return visited.toArray(new Node[0]);
    }
}
