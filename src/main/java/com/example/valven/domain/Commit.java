package com.example.valven.domain;

import com.example.valven.util.base.BaseDomain;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Commit extends BaseDomain {

    private String hash;
    private Timestamp timestamp;
    private String message;
    private String author;
    private String patch;
    private String developerId;
    private String developerUsername;
    private String developerEmail;
}
