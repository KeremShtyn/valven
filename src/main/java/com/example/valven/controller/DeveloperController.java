package com.example.valven.controller;

import com.example.valven.api.DeveloperApi;
import com.example.valven.domain.Developer;
import com.example.valven.dto.DeveloperDTO;
import com.example.valven.mapper.DeveloperDTOMapper;
import com.example.valven.service.DeveloperService;
import com.example.valven.util.response.ValvenGenerator;
import com.example.valven.util.response.ValvenResponse;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeveloperController implements DeveloperApi {

    private DeveloperService developerService;

    private DeveloperDTOMapper developerDTOMapper;

    public DeveloperController(DeveloperService developerService, DeveloperDTOMapper developerDTOMapper) {
        this.developerService = developerService;
        this.developerDTOMapper = developerDTOMapper;
    }

    @Override
    public ValvenResponse<Object> getDeveloperByUsername(String username) {
        DeveloperDTO developer = developerDTOMapper.toDTO(developerService.findByUsername(username));
        return ValvenGenerator.generateSignResponse(developer);
    }
}
