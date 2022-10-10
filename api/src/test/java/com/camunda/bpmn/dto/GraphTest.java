package com.camunda.bpmn.dto;

import com.camunda.bpmn.exceptions.VertexNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void whenGraphDoesNotContainVertex_thenFindVertexShouldThrowException() {
        Graph graph = new Graph();
        assertThrows(VertexNotFoundException.class, () -> graph.findNode("A"));
    }

    @Test
    void whenAddNewNeighbourToVertex_thenItShouldAddToGraphVertex() {
        Graph graph = new Graph();
        graph.addNode("A").addNeighbour("B").addNeighbour("C").addNeighbour("D");

        AbstractGraph.Node vertexA = assertDoesNotThrow(() -> graph.findNode("A"));
        assertEquals("A", vertexA.getValue());

        AbstractGraph.Node vertexB = assertDoesNotThrow(() -> graph.findNode("B"));
        assertEquals("B", vertexB.getValue());

        assertTrue(vertexA.getNeighbours().contains(vertexB));
    }

    @Test
    void whenAddRepeatedVertex_thenItShouldTakeExistingVertex() {
        Graph graph = new Graph();
        graph.addNode("A").addNeighbour("B").addNeighbour("C").addNeighbour("D");
        graph.addNode("A").addNeighbour("E").addNeighbour("F");

        AbstractGraph.Node vertexA = assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = assertDoesNotThrow(() -> graph.findNode("B"));
        AbstractGraph.Node vertexE = assertDoesNotThrow(() -> graph.findNode("E"));

        assertEquals(5, vertexA.getNeighbours().size());
        assertTrue(vertexA.getNeighbours().contains(vertexB));
        assertTrue(vertexA.getNeighbours().contains(vertexE));
    }

    @Test
    void whenAddExistingVertexAsNeighbour_thenItShouldGetTheExistingReference() {
        Graph graph = new Graph();
        graph.addNode("A").addNeighbour("B");
        graph.addNode("C").addNeighbour("A");
        graph.addNode("B").addNeighbour("C");

        AbstractGraph.Node vertexA = assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = assertDoesNotThrow(() -> graph.findNode("B"));
        AbstractGraph.Node vertexC = assertDoesNotThrow(() -> graph.findNode("C"));

        assertTrue(vertexA.getNeighbours().contains(vertexB));
        assertTrue(vertexB.getNeighbours().contains(vertexC));
        assertTrue(vertexC.getNeighbours().contains(vertexA));
    }

    @Test
    void whenAddEdgeWithNewVertex_thenItShouldAddNewVertexToGraphNodes() {
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        AbstractGraph.Node vertexA = assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = assertDoesNotThrow(() -> graph.findNode("B"));
        assertTrue(vertexA.getNeighbours().contains(vertexB));
    }

    @Test
    void givenGraphWithVertexA_whenAddEdgeContainingExistingNode_shouldGetThatNode() {
        Graph graph = new Graph();
        graph.addNode("B");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");
        AbstractGraph.Node vertexA = assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = assertDoesNotThrow(() -> graph.findNode("B"));
        AbstractGraph.Node vertexC = assertDoesNotThrow(() -> graph.findNode("C"));
        assertTrue(vertexA.getNeighbours().contains(vertexB));
        assertTrue(vertexA.getNeighbours().contains(vertexC));
        assertTrue(vertexB.getNeighbours().contains(vertexC));
    }

    @Test
    void whenAddingVertexAndNeighbours_thenTheNeighbourPeekTheExistingNode() throws Exception {
        Graph graph = new Graph();
        graph.addNode("S");
        graph.findNode("S").addNeighbour("A").addNeighbour("B");
        graph.findNode("A").addNeighbour("C").addNeighbour("D");
        graph.findNode("B").addNeighbour("C").addNeighbour("E");

        Assertions.assertThat(graph.findNode("A").getNeighbours())
                .extracting(AbstractGraph.Node::getValue).contains("C", "D");
        Assertions.assertThat(graph.findNode("B").getNeighbours())
                .extracting(AbstractGraph.Node::getValue).contains("C", "E");
    }

    @MethodSource("graphArgs")
    @ParameterizedTest
    void givenDfsRecursive_whenTraverseGraph_thenItShouldCrossTheTarget(
            Graph graph, String start, String target) {
        AbstractGraph.Node[] visited = graph.dfsRecursive(start);
        assertTrue(Arrays.stream(visited).map(AbstractGraph.Node::getValue).anyMatch(target::equals));
    }

    @MethodSource("graphArgs")
    @ParameterizedTest
    void givenDfsIterative_whenTraverseGraph_thenItShouldCrossTheTarget(
            Graph graph, String start, String target) {
        AbstractGraph.Node[] visited = graph.dfsIterative(start);
        assertTrue(Arrays.stream(visited).map(AbstractGraph.Node::getValue).anyMatch(target::equals));
    }

    @MethodSource("graphArgs")
    @ParameterizedTest
    void givenBfsAlgorithm_whenTraverseGraph_thenItShouldCrossTheTarget(
            Graph graph, String start, String target) {
        AbstractGraph.Node[] visited = graph.bfs(start);
        assertTrue(Arrays.stream(visited).map(AbstractGraph.Node::getValue).anyMatch(target::equals));
    }

    private static Stream<Arguments> graphArgs() {
        return Stream.of(
                Arguments.of(graph(), "A1", "A"),
                Arguments.of(graph1(), "A", "C")
        );
    }

    private static Graph graph() {
        Graph graph = new Graph();
        graph.addNode("S").addNeighbour("A").addNeighbour("A1");
        graph.findNode("A").addNeighbour("A2");
        graph.findNode("A1").addNeighbour("B1").addNeighbour("B2");
        graph.findNode("B1").addNeighbour("C1").addNeighbour("C2");
        graph.addEdge("C1", "S").addEdge("C2", "A").addEdge("A2", "B2");

        return graph;
    }

    private static Graph graph1() {
        Graph graph = new Graph();
        graph.addNode("A").addNeighbour("B").addNeighbour("C");
        graph.addEdge("B", "D").addEdge("D", "C");
        return graph;
    }
}