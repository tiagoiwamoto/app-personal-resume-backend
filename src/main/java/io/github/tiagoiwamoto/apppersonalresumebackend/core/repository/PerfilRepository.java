package io.github.tiagoiwamoto.apppersonalresumebackend.core.repository;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {

    Optional<PerfilEntity> findByNomeIgnoreCase(String nome);
}
