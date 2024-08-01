package com.example.valven.controller;

import com.example.valven.api.CommitApi;
import com.example.valven.domain.Commit;
import com.example.valven.dto.CommitDTO;
import com.example.valven.mapper.CommitDTOMapper;
import com.example.valven.service.CommitService;
import com.example.valven.util.Platform;
import com.example.valven.util.ValvenPageable;
import com.example.valven.util.response.ValvenGenerator;
import com.example.valven.util.response.ValvenResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CommitController implements CommitApi {

    private CommitService commitService;

    private CommitDTOMapper commitDTOMapper;

    public CommitController(CommitService commitService, CommitDTOMapper commitDTOMapper) {
        this.commitService = commitService;
        this.commitDTOMapper = commitDTOMapper;
    }

    @Override
    public String fetchAndSaveCommits(Platform platform, String ownerOrProjectId, String repo, String token) {
        try {
            List<CommitDTO> commitList = commitDTOMapper.toListDTO(commitService.save(platform, ownerOrProjectId, repo, token));
            return "Commits are saved successfully";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    @Override
    public ValvenResponse<Object> getCommits(Map<String, Object> header, int page, int size) {
        ValvenPageable<Commit> commitPage = commitService.findAll(page, size);
        return ValvenGenerator.generateSignResponse(commitPage);
    }
}
