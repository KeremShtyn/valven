package com.example.valven.service;

import com.example.valven.dto.GitLabCommitDTO;
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
public class GitLabService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<GitLabCommitDTO> fetchCommits(String projectId, String token) {
        String url = String.format("https://gitlab.com/api/v4/projects/%s/repository/commits?since=%s",
                projectId, LocalDateTime.now().minusMonths(1).toInstant(ZoneOffset.UTC).toString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GitLabCommitDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, GitLabCommitDTO[].class);
        return Arrays.asList(response.getBody());
    }
}
