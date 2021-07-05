package com.khoubyari.example.api.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {
    protected final Logger LOGGER = LoggerFactory.getLogger(RestControllerAdvice.class);

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

  /*  @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public void handleRuntimeException(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) {
        try {
         //   jacksonMessageConverter.write(new MyRestResult(translateMessage(exception)), MediaType.APPLICATION_JSON,
         //           new ServletServerHttpResponse(response));
            jacksonMessageConverter.write(new MyRestResult(exception.getMessage(), MediaType.APPLICATION_JSON,
                       new ServletServerHttpResponse(response));
            response.flushBuffer(); // Flush to commit the response
        } catch (IOException e) {
            LOGGER.error("Error in handleRuntimeException", e);
        }
    }*/

    @ExceptionHandler(NoSuchElementException.class)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}