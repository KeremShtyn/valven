package com.example.valven.service;

import com.example.valven.dto.GitLabDTO;
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

import static com.example.valven.util.api.PlatformApiEndPoints.GITLAB_COMMITS_ENDPOINT;

@Service
public class GitLabService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<GitLabDTO> fetchCommits(String projectId, String token) {
        String url = String.format(GITLAB_COMMITS_ENDPOINT,
                projectId, LocalDateTime.now().minusMonths(1).toInstant(ZoneOffset.UTC).toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitLabDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, GitLabDTO[].class);
        return Arrays.asList(response.getBody());
    }
}
