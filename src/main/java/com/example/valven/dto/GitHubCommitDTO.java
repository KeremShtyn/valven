package com.example.valven.dto;

import lombok.Data;

@Data
public class GitHubCommitDTO {

    private String sha;
    private Commit commit;

    @Data
    public static class Commit {
        private Author author;
        private String message;
    }

    @Data
    public static class Author {
        private String name;
        private String email;
        private String date;
    }
}
