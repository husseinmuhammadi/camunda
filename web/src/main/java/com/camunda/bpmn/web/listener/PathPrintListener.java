package com.camunda.bpmn.web.listener;

import com.camunda.bpmn.dto.AbstractGraph;
import com.camunda.bpmn.web.event.PathFoundEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Stack;

@Component
@RequiredArgsConstructor
public class PathPrintListener {

    private final Logger logger;

    @EventListener
    public void printPath(PathFoundEvent event) {
        if (event.getPath() == null || event.getPath().isEmpty())
            logger.warn("No path found");
        printPath(event.getPath(), System.out);
    }

    private void printPath(Stack<AbstractGraph.Node> path, PrintStream out) {
        for (AbstractGraph.Node node : path) {
            out.printf("%s,", node.getValue());
        }
        out.println();
    }
}
