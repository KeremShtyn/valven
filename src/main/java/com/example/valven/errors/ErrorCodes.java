package com.example.valven.errors;

import com.example.valven.util.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum ErrorCodes implements ErrorCode {

    SYSTEM_FAILURE(-1, "ErrorCodes.SYSTEM_FAILURE",HttpStatus.INTERNAL_SERVER_ERROR),
    DATA_NOT_FOUND(100, "ErrorCodes.ACCESS_DENIED", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(100, "ErrorCodes.USER_DENIED", HttpStatus.BAD_REQUEST),

    ACCESS_DENIED(101, "ErrorCodes.ACCESS_DENIED", HttpStatus.FORBIDDEN),
    DEVELOPER_DATA_CAN_NOT_BE_EMPTY(101, "ErrorCodes.DEVELOPER_DATA_CAN_NOT_BE_EMPTY", HttpStatus.BAD_REQUEST),
    DEVELOPER_USERNAME_CAN_NOT_BE_EMPTY(101, "ErrorCodes.DEVELOPER_USERNAME_CAN_NOT_BE_EMPTY", HttpStatus.BAD_REQUEST),
    DEVELOPER_EMAIL_CAN_NOT_BE_EMPTY(101, "ErrorCodes.DEVELOPER_EMAIL_CAN_NOT_BE_EMPTY", HttpStatus.BAD_REQUEST),

    ;

    private ErrorCodes(Integer code, String langKey, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.langKey = langKey;
    }

    @Getter
    private Integer code;

    @Getter
    private String langKey;

    @Getter
    private HttpStatus httpStatus;

    /**
     * @param code
     * @return
     */
    public ErrorCodes findByCode(Integer code) {
        return Arrays.asList(ErrorCodes.values()).stream().filter(f -> f.getCode().equals(code)).findFirst().orElse(ErrorCodes.SYSTEM_FAILURE);
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String langKey() {
        return this.langKey;
    }
}
