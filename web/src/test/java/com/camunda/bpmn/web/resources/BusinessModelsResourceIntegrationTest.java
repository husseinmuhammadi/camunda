package com.camunda.bpmn.web.resources;

import com.camunda.bpmn.generated.v1.model.BusinessProcessModel;
import com.camunda.bpmn.integration.IntegrationTest;
import com.camunda.bpmn.web.client.BusinessProcessModelingNotationProviderClient;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static com.camunda.bpmn.utils.ResourceLoader.BusinessProcessModelNotation.INVALID_XML;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class BusinessModelsResourceIntegrationTest {

    public static final String ENDPOINT_BPMN_DIAGRAM_RESOURCE = "/api/v1/business-model";

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private BusinessProcessModelingNotationProviderClient client;

    @Test
    void shouldReturnInternalServerError_whenThereIsErrorInRemoteServerResponse() throws Exception {
        Mockito.when(client.getBpmnDiagram()).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_BPMN_DIAGRAM_RESOURCE)).andExpectAll(
                status().isInternalServerError()
        );
    }

    @Test
    void shouldReturnValidBpmn_whenGetBpmnDiagram() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(ENDPOINT_BPMN_DIAGRAM_RESOURCE)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_XML)
                ).andReturn();

        String bpmn = mvcResult.getResponse().getContentAsString();

        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(
                bpmn.getBytes(StandardCharsets.UTF_8)));

        assertDoesNotThrow(() -> Bpmn.validateModel(bpmnModelInstance));
    }

    @Test
    void shouldReturnInternalServerError_whenRemoteServerResponseIsNotValidResponse() throws Exception {
        final BusinessProcessModel bpm = new BusinessProcessModel()
                .id("invoice:2:07bc6830-dc12-11e8-a8e9-0242ac110002")
                .bpmn20Xml(INVALID_XML.readJson());

        when(client.getBpmnDiagram()).thenReturn(ResponseEntity.ok(bpm));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_BPMN_DIAGRAM_RESOURCE)).andExpectAll(
                status().isInternalServerError()
        );
    }

}