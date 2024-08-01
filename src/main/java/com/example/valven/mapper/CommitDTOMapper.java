package com.example.valven.mapper;

import com.example.valven.domain.Commit;
import com.example.valven.dto.CommitDTO;
import com.example.valven.util.base.BaseDTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommitDTOMapper extends BaseDTOMapper<Commit, CommitDTO> {
}
