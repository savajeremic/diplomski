/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.raider.exception;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Sava
 */
public class ApiException extends RuntimeException {
    private String code;
    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
    
    private List<String> errorList;

    public ApiException(String message) {
        this.message = message;
    }

    public ApiException(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
    
    public ApiException(String message, List<String> errorList) {
        this.message = message;
        this.errorList = errorList;
    }

    public ApiException(String message, HttpStatus httpStatus, List<String> errorList) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.errorList = errorList;
    }
    public ApiException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public ApiException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public ApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ApiException(Throwable throwable, HttpStatus httpStatus) {
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public ApiException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ApiException{" + "code=" + code + ", message=" + message + ", throwable=" + throwable + ", httpStatus=" + httpStatus + ", timestamp=" + timestamp + ", errorList=" + errorList + '}';
    }
}
