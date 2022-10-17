package com.camunda.bpmn.web.listener;

import com.camunda.bpmn.dto.AbstractGraph;
import com.camunda.bpmn.dto.Graph;
import com.camunda.bpmn.web.event.BpmnReadyEvent;
import com.camunda.bpmn.web.event.PathFoundEvent;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

@RequiredArgsConstructor
@Component
public class BpmnProcessorListener {

    private final Logger logger;
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void processBpmnModel(BpmnReadyEvent event) {
        try {
            BpmnModelInstance bpmnModelInstance = createAndValidateBpmnModel(event.getBpmn());

            bpmnModelInstance.getModelElementsByType(SequenceFlow.class);

            Graph graph = createGraphFromBpmnModel(bpmnModelInstance);
            logger.info("Possible path between {}, {}", event.getStartNode(), event.getEndNode());
            Stack<AbstractGraph.Node> path = graph.findPath(event.getStartNode(), event.getEndNode());

            if (!path.isEmpty())
                publisher.publishEvent(new PathFoundEvent(path));
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private BpmnModelInstance createAndValidateBpmnModel(String bpmn) {
        logger.info("Validating invoice");
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(
                bpmn.getBytes(StandardCharsets.UTF_8)));
        Bpmn.validateModel(bpmnModelInstance);
        logger.info("Invoice validated");
        return bpmnModelInstance;
    }

    private Graph createGraphFromBpmnModel(BpmnModelInstance bpmnModelInstance) {
        Graph graph = new Graph();

        logger.info("Create graph for given bpmn");
        bpmnModelInstance.getModelElementsByType(FlowNode.class).stream().map(BaseElement::getId)
                .forEach(graph::addNode);

        bpmnModelInstance.getModelElementsByType(SequenceFlow.class).stream()
                .forEach(e -> graph.addEdge(e.getSource().getId(), e.getTarget().getId()));

        return graph;
    }

}
