package com.wallester.backend.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

/**
 * Exception handler catches all exceptions of this application and wraps it into {@link ResponseErrorDto}
 */
@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {
    /**
     * Catches Jackson's exception after JSON parsing.
     *
     * @param ex        Exception
     * @param response  Server response
     * @return          Response-error DTO
     */
    @ExceptionHandler(value = JsonProcessingException.class)
    @ResponseBody
    public ResponseErrorDto handleJacksonError(JsonProcessingException ex, HttpServletResponse response) {
        log.error("Jackson error: ", ex);
        ResponseErrorDto dto = new ResponseErrorDto();
        dto.setTime(OffsetDateTime.now());
        dto.setMessage("JSON parse exception, check your request params");
        dto.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return dto;
    }

    /**
     * Catches exception {@link ApiException} and returns object with error description
     *
     * @param ex        Exception
     * @param response  Server response
     * @return          Response-error DTO
     */
    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public ResponseErrorDto handleApiExceptions(ApiException ex, HttpServletResponse response) {
        log.error("API exception: ", ex);
        return getResponseErrorDto(ex, response);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseErrorDto handleIllegalArgumentExceptions(IllegalArgumentException ex, HttpServletResponse response) {
        log.error("IllegalArgumentException exception: {}", ex.getMessage(), ex);
        ResponseErrorDto dto = new ResponseErrorDto();
        dto.setTime(OffsetDateTime.now());
        dto.setMessage(ex.getMessage());
        dto.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return dto;
    }

    @NotNull
    private ResponseErrorDto getResponseErrorDto(ApiException ex, HttpServletResponse response) {
        ResponseErrorDto dto = new ResponseErrorDto();
        dto.setTime(OffsetDateTime.now());
        dto.setMessage(ex.getMessage());
        int status = ex.getStatus();
        dto.setStatus(status);
        response.setStatus(status);
        return dto;
    }
}