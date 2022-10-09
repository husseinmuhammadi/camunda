package com.camunda.bpmn.dto;

import com.javastudio.tutorial.twitter.oauth.exceptions.VertexNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        AbstractGraph.Node vertexA = Assertions.assertDoesNotThrow(() -> graph.findNode("A"));
        assertEquals("A", vertexA.getValue());

        AbstractGraph.Node vertexB = Assertions.assertDoesNotThrow(() -> graph.findNode("B"));
        assertEquals("B", vertexB.getValue());

        assertTrue(vertexA.getNeighbours().contains(vertexB));
    }

    @Test
    void whenAddRepeatedVertex_thenItShouldTakeExistingVertex() {
        Graph graph = new Graph();
        graph.addNode("A").addNeighbour("B").addNeighbour("C").addNeighbour("D");
        graph.addNode("A").addNeighbour("E").addNeighbour("F");

        AbstractGraph.Node vertexA = Assertions.assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = Assertions.assertDoesNotThrow(() -> graph.findNode("B"));
        AbstractGraph.Node vertexE = Assertions.assertDoesNotThrow(() -> graph.findNode("E"));

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

        AbstractGraph.Node vertexA = Assertions.assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = Assertions.assertDoesNotThrow(() -> graph.findNode("B"));
        AbstractGraph.Node vertexC = Assertions.assertDoesNotThrow(() -> graph.findNode("C"));

        assertTrue(vertexA.getNeighbours().contains(vertexB));
        assertTrue(vertexB.getNeighbours().contains(vertexC));
        assertTrue(vertexC.getNeighbours().contains(vertexA));
    }

    @Test
    void whenAddEdgeWithNewVertex_thenItShouldAddNewVertexToGraphNodes() {
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        AbstractGraph.Node vertexA = Assertions.assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = Assertions.assertDoesNotThrow(() -> graph.findNode("B"));
        assertTrue(vertexA.getNeighbours().contains(vertexB));
    }

    @Test
    void givenGraphWithVertexA_whenAddEdgeContainingExistingNode_shouldGetThatNode() {
        Graph graph = new Graph();
        graph.addNode("B");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");
        AbstractGraph.Node vertexA = Assertions.assertDoesNotThrow(() -> graph.findNode("A"));
        AbstractGraph.Node vertexB = Assertions.assertDoesNotThrow(() -> graph.findNode("B"));
        AbstractGraph.Node vertexC = Assertions.assertDoesNotThrow(() -> graph.findNode("C"));
        assertTrue(vertexA.getNeighbours().contains(vertexB));
        assertTrue(vertexA.getNeighbours().contains(vertexC));
        assertTrue(vertexB.getNeighbours().contains(vertexC));
    }

}