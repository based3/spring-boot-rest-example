package com.khoubyari.example.api.rest;

import com.khoubyari.example.domain.RestErrorInfo;
import com.khoubyari.example.exception.DataFormatException;
import com.khoubyari.example.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Extensible based class for REST resource "controllers".
 * It contains exception mapping and other common REST API functionality
 */
public abstract class AbstractRestHandler implements ApplicationEventPublisherAware {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected ApplicationEventPublisher eventPublisher;

    protected static final String  DEFAULT_PAGE_SIZE = "100";
    protected static final String DEFAULT_PAGE_NUM = "0";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public RestErrorInfo handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        LOG.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

        return new RestErrorInfo(ex, "Data format error.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        LOG.info("ResourceNotFoundException handler:" + ex.getMessage());

        return new RestErrorInfo(ex, "Resource not found.");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    // TODO Replace with exception mapping
    public static <T> T checkResourceFound(final T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException("resource not found");
        }
        return resource;
    }

}