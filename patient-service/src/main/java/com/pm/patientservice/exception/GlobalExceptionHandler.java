package com.pm.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
       Map<String, String> errors = new HashMap<>();
         ex.getBindingResult().getFieldErrors().forEach(error ->
              errors.put(error.getField(), error.getDefaultMessage()));

         return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {

        log.warn("Email address already exists {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", "Email already exists");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(
            PatientNotFoundException ex) {

        log.warn("Patient not found {}", ex.getMessage());

        Map<String, String> error = new HashMap<>();
        error.put("message", "Patient not found");
        return ResponseEntity.badRequest().body(error);
    }
}
