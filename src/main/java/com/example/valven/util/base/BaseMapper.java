package com.example.valven.util.base;

import java.util.List;

public interface BaseMapper<Entity, Domain> {

    Entity toEntity(Domain domain);

    List<Domain> toListDomainObject(List<Entity> entityObjects);


    Domain toDomainObject(Entity entityObject);

    List<Entity> toEntityList(List<Domain> domains);

}
