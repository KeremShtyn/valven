package com.example.valven.util.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@RequiredArgsConstructor
public class ValvenException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SIGN_ERROR_DETAIL = "An unexpected error occurred! Please contact the api support!";

    @Getter
    private final String errorMessage;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String errorDetail;

    @Getter
    private final ErrorCode errorCode;

    @Getter
    private String[] args;

    public ValvenException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getName(), cause);
        this.errorCode = errorCode;
        this.errorMessage = cause.getMessage();
        this.errorDetail = !StringUtils.isEmpty(cause.getMessage()) ? cause.getMessage() : SIGN_ERROR_DETAIL;
    }

    public ValvenException(ErrorCode errorCode) {
        super(errorCode.getName());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getName();
        this.errorDetail = null;
    }

    public ValvenException(ErrorCode errorCode, String[] args) {
        super(errorCode.getName());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getName();
        this.args = args;
        this.errorDetail = null;
    }


    public ValvenException(ErrorCode errorCode, String errorDetail) {
        super(errorCode.getName());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getName();
        this.errorDetail = errorDetail;
    }

}
