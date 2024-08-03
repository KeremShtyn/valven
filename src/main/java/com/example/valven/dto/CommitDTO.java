package com.example.valven.dto;

import com.example.valven.util.base.BaseDTO;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommitDTO extends BaseDTO {

    private String hash;
    private Timestamp timestamp;
    private String message;
    private String author;
    private String patch;
    private String developerId;
    private String developerUsername;
    private String developerEmail;
}
