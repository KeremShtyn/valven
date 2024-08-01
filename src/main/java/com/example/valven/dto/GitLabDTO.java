package com.example.valven.dto;

import lombok.Data;

@Data

public class GitLabDTO {

    private String id;
    private String short_id;
    private String created_at;
    private String title;
    private String message;
    private GitLabAuthorDTO author;


}
