package com.gabo.kchika.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gabo.kchika.exceptions.TitleNotValidException;

@ControllerAdvice
public class ErrorHandlerController {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity <Map<String, Object>> 
        IllegalArgumentHandler(IllegalArgumentException ex){
            final var response = new HashMap<String, Object>();

            response.put("code", HttpStatus.BAD_REQUEST.value());
            response.put("status", HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.put("message", ex.getMessage());

            return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = TitleNotValidException.class)
    public ResponseEntity<Map<String, Object>> 
        TitleNotValidExceptionr(TitleNotValidException ex){
            final var response = new HashMap<String, Object>();

            response.put("code", HttpStatus.BAD_REQUEST.value());
            response.put("status", HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.put("message", ex.getMessage());

            return ResponseEntity.badRequest().body(response);
    }
}
