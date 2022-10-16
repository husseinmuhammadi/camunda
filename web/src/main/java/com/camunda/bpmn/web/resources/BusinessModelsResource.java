package com.camunda.bpmn.web.resources;

import com.camunda.bpmn.generated.v1.api.BusinessModelApi;
import com.camunda.bpmn.generated.v1.model.BusinessProcessModel;
import com.camunda.bpmn.web.client.BusinessProcessModelingNotationProviderClient;
import com.camunda.bpmn.web.exceptions.BpmnServiceException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BusinessModelsResource implements BusinessModelApi {

    private final BusinessProcessModelingNotationProviderClient client;

    @Override
    public ResponseEntity<String> getBusinessProcessingModelingNotation() {
        ResponseEntity<BusinessProcessModel> response = client.getBpmnDiagram();

        if (response.getStatusCode().isError() || !response.hasBody())
            throw new BpmnServiceException("Error getting response from remote service");

        String bpmn = response.getBody().getBpmn20Xml();

        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(
                bpmn.getBytes(StandardCharsets.UTF_8)));
        Bpmn.validateModel(bpmnModelInstance);

        return ResponseEntity.ok(bpmn);
    }
}
