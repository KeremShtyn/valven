package com.example.valven.mapper;

import com.example.valven.domain.Developer;
import com.example.valven.dto.DeveloperDTO;
import com.example.valven.util.base.BaseDTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeveloperDTOMapper extends BaseDTOMapper<Developer, DeveloperDTO> {
}
