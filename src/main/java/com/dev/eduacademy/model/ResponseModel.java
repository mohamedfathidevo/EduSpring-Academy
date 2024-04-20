package com.dev.eduacademy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * ResponseModel is a model class that represents the response sent by the API.
 * It contains the HTTP status, success status, data, and errors of the response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseModel {
    private HttpStatus status;
    private boolean success;
    private Object data;
    private Object errors;
}
