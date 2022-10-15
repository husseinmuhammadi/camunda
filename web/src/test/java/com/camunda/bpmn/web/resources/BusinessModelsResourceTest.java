package com.camunda.bpmn.web.resources;

import com.camunda.bpmn.web.client.BusinessProcessModelingNotationProviderClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessModelsResourceTest {

    @InjectMocks
    private BusinessModelsResource businessModelsResource;

    @Mock
    private BusinessProcessModelingNotationProviderClient client;

    @Test
    void shouldThrowRuntimeException_whenClientThrowRuntimeException() {

        when(client.getBpmnDiagram()).thenThrow(new RuntimeException("a"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                businessModelsResource.getBusinessProcessingModelingNotation());
    }

    @Test
    void shouldReturnBpmn_whenRemoteServerReturn(){

    }
}