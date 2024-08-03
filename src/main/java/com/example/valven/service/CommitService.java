package com.example.valven.service;

import com.example.valven.domain.Commit;
import com.example.valven.entity.CommitEntity;
import com.example.valven.mapper.CommitMapper;
import com.example.valven.repository.CommitRepository;
import com.example.valven.util.Platform;
import com.example.valven.util.ValvenPageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

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


    public List<Commit> save(Platform platform, String token, String owner, String repo) {
        List<Commit> allCommits = new ArrayList<>();

        if (platform == Platform.GITHUB) {
            allCommits.addAll(gitHubService.fetchCommits(token, owner, repo));
        } else if (platform == Platform.GITLAB) {
            allCommits.addAll(gitLabService.fetchCommits(owner, token));
        }

        List<CommitEntity> commitEntities = commitMapper.toEntityList(allCommits);

        return commitMapper.toListDomainObject(commitRepository.saveAll(commitEntities));
    }


}
