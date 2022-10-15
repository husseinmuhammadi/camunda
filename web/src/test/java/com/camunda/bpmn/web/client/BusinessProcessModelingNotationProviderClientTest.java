package com.camunda.bpmn.web.client;

import com.camunda.bpmn.generated.v1.model.BusinessProcessModel;
import com.camunda.bpmn.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class BusinessProcessModelingNotationProviderClientTest {

    @Autowired
    private BusinessProcessModelingNotationProviderClient businessProcessModelingNotationProviderClient;

    @Test
    void shouldReturnXMLRepresentationOfInvoiceApproval_whenHitBPMNDiagramEndpointFromRemoteServer() {
        ResponseEntity<BusinessProcessModel> response = businessProcessModelingNotationProviderClient.getBpmnDiagram();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}