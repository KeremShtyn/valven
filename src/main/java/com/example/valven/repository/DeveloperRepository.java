package com.example.valven.repository;

import com.example.valven.entity.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperEntity, String> {

    Optional<DeveloperEntity> findByUsername(String username);

    DeveloperEntity saveDeveloper(String name, String email);

}
