package com.example.valven.service;

import com.example.valven.domain.Developer;
import com.example.valven.errors.ErrorCodes;
import com.example.valven.mapper.DeveloperMapper;
import com.example.valven.repository.DeveloperRepository;
import com.example.valven.util.exception.ValvenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class DeveloperService {

    private DeveloperRepository developerRepository;

    private DeveloperMapper developerMapper;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperMapper developerMapper) {
        this.developerRepository = developerRepository;
        this.developerMapper = developerMapper;
    }

    public Developer findByUsername(String username) {
        return developerRepository.findByUsername(username).map(developerMapper::toDomainObject).orElseThrow(() -> new ValvenException(ErrorCodes.DATA_NOT_FOUND));
    }

    public Developer createDeveloper(String name, String email){
        this.validateDeveloper(name, email);
        return save(name, email);
    }

    public Developer save(String name, String email){
        return developerMapper.toDomainObject(developerRepository.saveDeveloper(name, email));
    }

    private void validateDeveloper(String name, String email){
        if(StringUtils.hasText(email)){
            throw new ValvenException(ErrorCodes.DEVELOPER_EMAIL_CAN_NOT_BE_EMPTY);
        }
        if(StringUtils.hasText(name)){
            throw new ValvenException(ErrorCodes.DEVELOPER_USERNAME_CAN_NOT_BE_EMPTY);
        }
    }
}
