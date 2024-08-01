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

import static com.example.valven.util.api.PlatformApiEndPoints.GITHUB_COMMITS_ENDPOINT;

@Service
public class GitHubService {

    public static final String APPLICATION_VND_GITHUB_JSON = "application/vnd.github+json";
    public static final String X_GIT_HUB_API_VERSION = "X-GitHub-Api-Version";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<GitHubCommitDTO>  fetchCommits(String owner, String repo, String token) {
        String url = String.format(GITHUB_COMMITS_ENDPOINT,
                owner, repo, LocalDateTime.now().minusMonths(1).toInstant(ZoneOffset.UTC).toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", APPLICATION_VND_GITHUB_JSON);
        headers.set("Authorization", "Bearer " + token);
        headers.set(X_GIT_HUB_API_VERSION, "2022-11-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitHubCommitDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, GitHubCommitDTO[].class);
        return Arrays.asList(response.getBody());
    }
}
