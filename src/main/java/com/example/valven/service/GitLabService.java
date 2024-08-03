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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.valven.util.api.PlatformApiEndPoints.GITLAB_COMMITS_ENDPOINT;

@Service
public class GitLabService {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private CommitDTOMapper commitDTOMapper;

    public GitLabService(CommitDTOMapper commitDTOMapper) {
        this.commitDTOMapper = commitDTOMapper;
    }


    public List<Commit> fetchCommits(String projectId, String token) {
        String url = GITLAB_COMMITS_ENDPOINT + projectId + "/repository/commits";

        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        List<CommitDTO> commitList = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            for (JsonNode node : root) {
                CommitDTO commit = new CommitDTO();
                commit.setHash(node.get("id").asText());
                commit.setTimestamp(new Timestamp(dateFormat.parse(node.get("created_at").asText()).getTime()));
                commit.setMessage(node.get("message").asText());
                commit.setDeveloperUsername(node.get("author_name").asText());
                commit.setDeveloperEmail(node.get("author_email").asText());
                commitList.add(commit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commitDTOMapper.toDomainList(commitList);
    }
}
