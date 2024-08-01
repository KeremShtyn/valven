package com.example.valven.mapper;

import com.example.valven.domain.Commit;
import com.example.valven.entity.CommitEntity;
import com.example.valven.util.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommitMapper extends BaseMapper<CommitEntity, Commit> {

    @Mapping(source = "developerId", target = "developer.id")
    @Mapping(source = "developerUsername", target = "developer.username")
    CommitEntity toEntity(Commit domain);

    @Mapping(target = "developerId", source = "developer.id")
    @Mapping(target = "developerUsername", source = "developer.username")
    Commit toDomainObject(CommitEntity entityObject);
}
