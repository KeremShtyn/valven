package com.example.valven.service;

import com.example.valven.domain.Commit;
import com.example.valven.domain.Developer;
import com.example.valven.dto.GitHubDTO;
import com.example.valven.dto.GitLabDTO;
import com.example.valven.entity.CommitEntity;
import com.example.valven.mapper.CommitMapper;
import com.example.valven.repository.CommitRepository;
import com.example.valven.util.Platform;
import com.example.valven.util.ValvenPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommitService {

    private CommitRepository commitRepository;

    private CommitMapper commitMapper;

    private DeveloperService developerService;

    private GitHubService gitHubService;

    private GitLabService gitLabService;

    public CommitService(CommitRepository commitRepository, CommitMapper commitMapper, DeveloperService developerService, GitHubService gitHubService, GitLabService gitLabService) {
        this.commitRepository = commitRepository;
        this.commitMapper = commitMapper;
        this.developerService = developerService;
        this.gitHubService = gitHubService;
        this.gitLabService = gitLabService;
    }

    public ValvenPageable<Commit> findAll(int page, int size){
        Page<CommitEntity> commits = commitRepository.findAll(PageRequest.of(page, size));
        return new ValvenPageable<Commit>(commits.getTotalElements(),commits.getTotalPages(),commits.getPageable(),commitMapper.toListDomainObject(commits.getContent()));
    }


    public List<Commit> save(Platform platform, String ownerOrProjectId, String repo, String token) {
        List<Commit> commits;
        if (platform.equals(Platform.GITHUB)) {
            commits = gitHubService.fetchCommits(ownerOrProjectId, repo, token)
                    .stream()
                    .map(this::mapGitHubCommitToCommit)
                    .collect(Collectors.toList());
        } else if (platform.equals(Platform.GITLAB)) {
            commits = gitLabService.fetchCommits(ownerOrProjectId, token)
                    .stream()
                    .map(this::mapGitLabCommitToCommit)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Unknown platform: " + platform);
        }
        List<CommitEntity> commitEntities = commitMapper.toEntityList(commits);
        commitRepository.saveAll(commitEntities);
        return commits;
    }

    private Commit mapGitLabCommitToCommit(GitLabDTO dto) {
        Developer developer = developerService.findByUsername(dto.getAuthor().getName());
        if (!StringUtils.hasLength(developer.getUsername())){
            developer = developerService.createDeveloper(dto.getAuthor().getName(), dto.getAuthor().getEmail());
        }

        Commit commit = new Commit();
        commit.setHash(dto.getId());
        commit.setTimestamp(Timestamp.valueOf(dto.getCreated_at().replace('T', ' ').replace('Z', ' ')));
        commit.setMessage(dto.getMessage());
        commit.setAuthor(dto.getAuthor().getName());
        commit.setDeveloperUsername(developer.getUsername());

        return commit;
    }

    private Commit mapGitHubCommitToCommit(GitHubDTO dto) {
        Developer developer = developerService.findByUsername(dto.getCommit().getAuthor().getName());
        if (!StringUtils.hasLength(developer.getUsername())) {
            developer = developerService.createDeveloper(dto.getCommit().getAuthor().getName(), dto.getCommit().getAuthor().getEmail());
        }

        Commit commit = new Commit();
        commit.setHash(dto.getSha());
        commit.setTimestamp(Timestamp.valueOf(dto.getCommit().getAuthor().getDate().replace('T', ' ').replace('Z', ' ')));
        commit.setMessage(dto.getCommit().getMessage());
        commit.setAuthor(dto.getCommit().getAuthor().getName());
        commit.setDeveloperUsername(developer.getUsername());

        return commit;
    }

}
