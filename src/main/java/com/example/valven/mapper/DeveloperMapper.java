package com.example.valven.mapper;

import com.example.valven.domain.Developer;
import com.example.valven.entity.DeveloperEntity;
import com.example.valven.util.base.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeveloperMapper extends BaseMapper<DeveloperEntity, Developer> {


}
