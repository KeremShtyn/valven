package com.example.valven.util.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class ValvenExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(WebRequest webRequest, Exception exception) {
        log.error("message : {}  ex : {}", exception.getMessage(), exception);

        var responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        final HttpStatus httpStatus = StringUtils.isEmpty(responseStatus) ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        final String localizedMessage = exception.getLocalizedMessage();
        final String path = webRequest.getDescription(false);
        final String message = StringUtils.isEmpty(localizedMessage) ? localizedMessage : httpStatus.getReasonPhrase();

        var signError = new ValvenError.SignErrorBuilder()
                .withTimestamp(LocalDateTime.now())
                .withStatus(httpStatus.value())
                .withMessage(message)
                .withDetail(exception.getCause() != null ? exception.getCause().getLocalizedMessage() : "")
                .withPath(path)
                .withType(exception.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(signError, httpStatus);
    }

    @ExceptionHandler(ValvenException.class)
    public ResponseEntity<Object> handleSignException(WebRequest webRequest, ValvenException valvenException) {
        log.error("message : {}  ex : {}", valvenException.getMessage(), valvenException);
        final String path = webRequest.getDescription(false);


        ValvenError valvenError = new ValvenError.SignErrorBuilder()
                .withTimestamp(LocalDateTime.now())
                .withStatus(valvenException.getErrorCode().getCode())
                .withMessage(valvenException.getMessage())
                .withDetail(valvenException.getErrorDetail())
                .withPath(path)
                .withType(valvenException.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(valvenError, valvenException.getErrorCode().getHttpStatus());
    }


}
