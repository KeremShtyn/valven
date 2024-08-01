package com.example.valven.service;

import com.example.valven.dto.GitHubCommitDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<GitHubCommitDTO>  fetchCommits(String owner, String repo, String token) {
        String url = String.format("https://api.github.com/repos/%s/%s/commits?since=%s",
                owner, repo, LocalDateTime.now().minusMonths(1).toInstant(ZoneOffset.UTC).toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + token);
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitHubCommitDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, GitHubCommitDTO[].class);
        return Arrays.asList(response.getBody());
    }
}
