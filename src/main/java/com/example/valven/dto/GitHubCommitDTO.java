package com.example.valven.dto;

import lombok.Data;

@Data
public class GitHubCommitDTO {

    private GitHubDeveloperDTO author;
    private String message;
}
