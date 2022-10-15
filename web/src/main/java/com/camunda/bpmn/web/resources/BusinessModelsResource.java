package com.camunda.bpmn.web.resources;

import com.camunda.bpmn.generated.v1.api.BusinessModelApi;
import com.camunda.bpmn.generated.v1.model.BusinessProcessModel;
import com.camunda.bpmn.web.client.BusinessProcessModelingNotationProviderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BusinessModelsResource implements BusinessModelApi {

    private final BusinessProcessModelingNotationProviderClient client;

    @Override
    public ResponseEntity<String> getBusinessProcessingModelingNotation() {
        ResponseEntity<BusinessProcessModel> response = client.getBpmnDiagram();

        return ResponseEntity.ok(response.getBody().getBpmn20Xml());
    }
}
