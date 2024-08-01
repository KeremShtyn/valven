package com.example.valven.util.base;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseDTO implements Serializable {

    private String id;

    private Long version = 0L;

    private LocalDateTime deletedDate;

    private LocalDateTime createdDate;

    private LocalDateTime modifyDate;

    private String createUser;

    private String modifyUser;

}
