package com.camunda.bpmn.web.client;

import com.camunda.bpmn.generated.v1.api.DefaultApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "xml-bpmn-provider",
        path = "/prod/engine-rest/process-definition/key/invoice",
        url = "${camunda.bpmn.diagrams.url}"
)
public interface BusinessProcessModelingNotationProviderClient extends DefaultApi {
}
