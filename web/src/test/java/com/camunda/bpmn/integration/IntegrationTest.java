package com.camunda.bpmn.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "server.port=0"
)
@AutoConfigureWireMock(port = 0, stubs = "classpath:wiremockstubs/mappings", files = "classpath:wiremockstubs")
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test"})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegrationTest {
}
