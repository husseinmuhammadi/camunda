package com.camunda.bpmn.web.advice;

import com.camunda.bpmn.web.exceptions.BpmnServiceException;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.model.xml.ModelParseException;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GeneralExceptionHandler {

    private final Logger logger;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({BpmnServiceException.class})
    public void handleBpmnServiceException(Exception e) {
        logger.error("error in getting bpmn from remote service: " + e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ModelParseException.class, ModelValidationException.class})
    public void hande() {

    }

    public void handleInternalServerError(Exception e) {

    }
}
