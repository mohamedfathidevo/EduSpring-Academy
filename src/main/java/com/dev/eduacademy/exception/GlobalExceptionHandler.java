package com.dev.eduacademy.exception;

import com.dev.eduacademy.model.ResponseModel;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a global exception handler for the application.
 * It uses the @ControllerAdvice annotation to provide centralized exception handling across all @RequestMapping methods.
 * It handles various types of exceptions and returns a custom response wrapped in a ResponseEntity.
 *
 * @author mohamedfathidevo
 * @version 1.0
 * @since 2023.3.5
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method handles validation exceptions.
     * It extracts field errors from the MethodArgumentNotValidException and returns them in a custom response.
     *
     * @param ex The MethodArgumentNotValidException object.
     * @return ResponseEntity<ResponseModel> The custom response wrapped in a ResponseEntity.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .success(false)
                        .errors(errors)
                        .build()
                );
    }

    /**
     * This method handles IllegalArgumentException.
     * It returns the exception message in a custom response.
     *
     * @param ex The IllegalArgumentException object.
     * @return ResponseEntity<ResponseModel> The custom response wrapped in a ResponseEntity.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(
                        ResponseModel
                                .builder()
                                .status(HttpStatus.BAD_REQUEST)
                                .success(false)
                                .errors(ex.getMessage())
                                .build()
                );
    }

    /**
     * This method handles RuntimeException.
     * It returns the exception message in a custom response.
     *
     * @param ex The RuntimeException object.
     * @return ResponseEntity<ResponseModel> The custom response wrapped in a ResponseEntity.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ResponseModel
                                .builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .success(false)
                                .errors(ex.getMessage())
                                .build()
                );
    }

    /**
     * This method handles EntityNotFoundException.
     * It returns the exception message in a custom response.
     *
     * @param ex The EntityNotFoundException object.
     * @return ResponseEntity<ResponseModel> The custom response wrapped in a ResponseEntity.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseModel
                        .builder()
                        .status(HttpStatus.NOT_FOUND)
                        .success(false)
                        .errors(ex.getMessage())
                        .build()
                );
    }

    /**
     * This method handles EntityExistsException.
     * It returns the exception message in a custom response.
     *
     * @param ex The EntityExistsException object.
     * @return ResponseEntity<ResponseModel> The custom response wrapped in a ResponseEntity.
     */
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ResponseModel
                                .builder()
                                .status(HttpStatus.CONFLICT)
                                .success(false)
                                .errors(ex.getMessage())
                                .build()
                );
    }

    /**
     * This method handles BadCredentialsException.
     * It returns the exception message in a custom response.
     *
     * @param ex The BadCredentialsException object.
     * @return ResponseEntity<ResponseModel> The custom response wrapped in a ResponseEntity.
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ResponseModel> handelBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ResponseModel
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED)
                                .success(false)
                                .errors(ex.getMessage())
                                .build()
                );
    }

    /**
     * This method handles AuthenticationCredentialsNotFoundException.
     * It returns a custom response indicating that authentication credentials were not found.
     *
     * @param ex The AuthenticationCredentialsNotFoundException object.
     * @return ResponseEntity<ResponseModel> The custom response wrapped in a ResponseEntity.
     */
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ResponseModel> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ResponseModel
                                .builder()
                                .status(HttpStatus.UNAUTHORIZED)
                                .success(false)
                                .errors("Authentication credentials not found")
                                .build()
                );
    }

}
