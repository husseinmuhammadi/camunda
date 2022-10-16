package com.camunda.bpmn.utils;

import com.camunda.bpmn.generated.v1.model.BusinessProcessModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResourceLoader {

    private static final String BUSINESS_PROCESS_MODEL_NOTATION_INVOICE_APPROVAL = "mock-data/invoice-approval.xml";
    private static final String BUSINESS_PROCESS_MODEL_NOTATION_INVALID_INVOICE_APPROVAL = "mock-data/invalid-bpmn-content.xml";

    private static String readAsString(String path) throws IOException {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            if (in == null) throw new IllegalArgumentException(path + " not found");
            return IOUtils.toString(in, StandardCharsets.UTF_8.name());
        }
    }

    private static <T> T readAsType(String resourceName, Class<T> type) throws IOException {
        try (InputStream in = ResourceLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) throw new IllegalArgumentException(resourceName + " not found");
            return objectMapper.readValue(in, type);
        }
    }

    private static File getResourceAsFile(String resourceName) throws URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) throw new IllegalArgumentException(resourceName + " not found!");
        return new File(url.toURI());
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public interface Resource<T> {

        Class<T> getType();

        String getResourceName();

        default T readApiModel() throws IOException {
            return readAsType(getResourceName(), getType());
        }

        default String readJson() throws IOException {
            return readAsString(getResourceName());
        }

        default File getResourceFile() throws URISyntaxException {
            return getResourceAsFile(getResourceName());
        }
    }

    public enum BusinessProcessModelNotation implements Resource<BusinessProcessModel> {
        INVOICE_APPROVAL(BUSINESS_PROCESS_MODEL_NOTATION_INVOICE_APPROVAL),
        INVALID_XML(BUSINESS_PROCESS_MODEL_NOTATION_INVALID_INVOICE_APPROVAL);

        private final String resourceName;

        BusinessProcessModelNotation(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public Class<BusinessProcessModel> getType() {
            return BusinessProcessModel.class;
        }

        @Override
        public String getResourceName() {
            return resourceName;
        }
    }
}
