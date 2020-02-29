/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Sava
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    
    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException apiException) {
        return generateResponseEntity(apiException);
    }

    private ResponseEntity<?> generateResponseEntity(ApiException apiException) {
        logger.warn(apiException.getMessage());
        return ResponseEntity.status(apiException.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiException(apiException.getMessage(), apiException.getHttpStatus()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(final ApplicationException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
        return generateResponseEntity(new ApiException(e.getMessage(), BAD_REQUEST));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(final EntityExistsException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
        return generateResponseEntity(new ApiException(e.getMessage(), BAD_REQUEST));
    }

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public ResponseEntity<?> handleEntityConflictsException(final HttpClientErrorException e) {
        return generateResponseEntity(new ApiException(e.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(final EntityNotFoundException e) {
        return generateResponseEntity(new ApiException(e.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(final RuntimeException e) {
        if(e.getMessage().toLowerCase().indexOf("access is denied") > -1) {
            return new ResponseEntity<>("Unauthorized Access", HttpStatus.UNAUTHORIZED);
        }
        return generateResponseEntity(new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handleUnsupportedOperationException(
            final UnsupportedOperationException e) {
        return generateResponseEntity(new ApiException(e.getMessage(), HttpStatus.NOT_IMPLEMENTED));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e,
            WebRequest request) {
        ApiException apiException = new ApiException(e, HttpStatus.BAD_REQUEST);
        apiException.setMessage(String.format("The parameter '%s' of value '%s' "
                + "could not be converted to type '%s'", e.getName(),
                e.getValue(), e.getRequiredType().getSimpleName()));
        return generateResponseEntity(apiException);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ApiException>> handle(ConstraintViolationException e) {
        List<ApiException> exceptionList = new ArrayList<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            ApiException apiException = new ApiException(violation.getMessageTemplate(), violation.getMessage());
            exceptionList.add(apiException);
        }
        return new ResponseEntity<>(exceptionList, HttpStatus.BAD_REQUEST);
    }
}
