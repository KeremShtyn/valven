package com.example.valven.service;

import com.example.valven.domain.Commit;
import com.example.valven.dto.CommitDTO;

import com.example.valven.mapper.CommitDTOMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.example.valven.util.api.PlatformApiEndPoints.GITHUB_COMMITS_ENDPOINT;

@Service
public class GitHubService {

    public static final String APPLICATION_VND_GITHUB_JSON = "application/vnd.github+json";
    public static final String X_GIT_HUB_API_VERSION = "X-GitHub-Api-Version";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CommitDTOMapper commitDTOMapper;

    public GitHubService(CommitDTOMapper commitDTOMapper) {
        this.commitDTOMapper = commitDTOMapper;
    }

    public List<Commit>  fetchCommits(String token, String owner, String repo) {
        String url = GITHUB_COMMITS_ENDPOINT + owner + "/" + repo + "/commits";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", APPLICATION_VND_GITHUB_JSON);
        headers.set("Authorization", "Bearer " + token);
        headers.set(X_GIT_HUB_API_VERSION, "2022-11-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        List<CommitDTO> commitList = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            for (JsonNode node : root) {
                CommitDTO commit = new CommitDTO();
                commit.setHash(node.get("sha").asText());
                commit.setTimestamp(Timestamp.valueOf(node.get("commit").get("author").get("date").asText().replace("T", " ").replace("Z", "")));
                commit.setMessage(node.get("commit").get("message").asText());
                commit.setDeveloperUsername(node.get("commit").get("author").get("name").asText());
                commitList.add(commit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commitDTOMapper.toDomainList(commitList);
    }
}
