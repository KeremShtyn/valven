package com.example.valven.util.base;

import java.util.List;

public interface BaseDTOMapper<Domain, DTO> {

    Domain toDomain(DTO dto);

    List<DTO> toListDTO(List<Domain> entityObjects);

    DTO toDTO(Domain domain);

    List<Domain> toDomainList(List<DTO> dtos);

}
