package com.example.valven.dto;

import lombok.Data;

@Data
public class GitHubDTO {

    private String sha;
    private GitHubCommitDTO commit;


}
