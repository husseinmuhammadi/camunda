package com.camunda.bpmn.web.resources;

import com.camunda.bpmn.integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
class BusinessModelsResourceIntegrationTest {

    public static final String ENDPOINT_BPMN_DIAGRAM_RESOURCE = "/api/v1/business-model";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnInternalServerError_whenThereIsErrorInRemoteServerResponse() {
//        mockMvc.perform(MockMvcRequestBuilders.get())
    }

    void shouldReturnXmlContent_whenGetBpmnDiagram(){

    }

    @Test
    void shouldReturnValidBpmn_whenGetBpmnDiagram()throws Exception{
        mockMvc.perform(get(ENDPOINT_BPMN_DIAGRAM_RESOURCE)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_XML)
                );
    }

}