package com.camunda.bpmn.web.resources;

import com.camunda.bpmn.generated.v1.model.BusinessProcessModel;
import com.camunda.bpmn.web.client.BusinessProcessModelingNotationProviderClient;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.xml.ModelParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static com.camunda.bpmn.utils.ResourceLoader.BusinessProcessModelNotation.INVALID_XML;
import static com.camunda.bpmn.utils.ResourceLoader.BusinessProcessModelNotation.INVOICE_APPROVAL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessModelsResourceTest {

    @InjectMocks
    private BusinessModelsResource businessModelsResource;

    @Mock
    private BusinessProcessModelingNotationProviderClient client;

    @Test
    void shouldThrowRuntimeException_whenClientThrowRuntimeException() {

        when(client.getBpmnDiagram()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () ->
                businessModelsResource.getBusinessProcessingModelingNotation());
    }

    @Test
    void shouldReturnBpmn_whenRemoteServerReturnValidXml() throws Exception {
        BusinessProcessModel bpm = new BusinessProcessModel()
                .id("invoice:2:07bc6830-dc12-11e8-a8e9-0242ac110002")
                .bpmn20Xml(INVOICE_APPROVAL.readJson());

        when(client.getBpmnDiagram()).thenReturn(ResponseEntity.ok(bpm));

        ResponseEntity<String> response = assertDoesNotThrow(() ->
                businessModelsResource.getBusinessProcessingModelingNotation());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());

        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(
                response.getBody().getBytes(StandardCharsets.UTF_8)));

        assertDoesNotThrow(() -> Bpmn.validateModel(bpmnModelInstance));
    }

    @Test
    void shouldThrowModelParseException_whenBpmnIsNotValidXml() throws Exception {
        final BusinessProcessModel bpm = new BusinessProcessModel()
                .id("invoice:2:07bc6830-dc12-11e8-a8e9-0242ac110002")
                .bpmn20Xml(INVALID_XML.readJson());

        when(client.getBpmnDiagram()).thenReturn(ResponseEntity.ok(bpm));

        assertThrows(ModelParseException.class, () ->
                businessModelsResource.getBusinessProcessingModelingNotation());
    }
}