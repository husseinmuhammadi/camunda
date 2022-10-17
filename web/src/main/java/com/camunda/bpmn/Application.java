package com.camunda.bpmn;

import com.camunda.bpmn.generated.v1.model.BusinessProcessModel;
import com.camunda.bpmn.web.client.BusinessProcessModelingNotationProviderClient;
import com.camunda.bpmn.web.event.BpmnReadyEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EnableSwagger2
@EnableFeignClients
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final Logger logger;
    private final BusinessProcessModelingNotationProviderClient client;
    private final ApplicationEventPublisher publisher;

    public static void main(String[] args) {
        if (startAsWebApplication(args))
            SpringApplication.run(Application.class, args);
        else
            new SpringApplicationBuilder(Application.class).web(WebApplicationType.NONE).run(args);
    }

    private static boolean startAsWebApplication(String[] args) {
        return Arrays.asList(args).contains("--web");
    }

    @Override
    public void run(String... args) throws Exception {

        List<String> arguments = Arrays.stream(args).filter(arg -> !arg.startsWith("--")).collect(Collectors.toList());

        if (arguments.size() < 2) {
            logger.warn("Please specify the startId and endId");
            return;
        }

        String startNode = arguments.get(0);
        String endNode = arguments.get(1);

        ResponseEntity<BusinessProcessModel> response = client.getBpmnDiagram();
        if (!response.getStatusCode().is2xxSuccessful() || !response.hasBody())
            return;

        String bpmn20Xml = response.getBody().getBpmn20Xml();
        publisher.publishEvent(new BpmnReadyEvent(bpmn20Xml, startNode, endNode));
    }
}
