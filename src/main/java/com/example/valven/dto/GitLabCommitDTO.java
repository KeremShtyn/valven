package com.example.valven.dto;

import lombok.Data;

@Data

public class GitLabCommitDTO {

    private String id;
    private String short_id;
    private String created_at;
    private String title;
    private String message;
    private Author author;

    @Data
    public static class Author {
        private String name;
        private String email;
    }
}
