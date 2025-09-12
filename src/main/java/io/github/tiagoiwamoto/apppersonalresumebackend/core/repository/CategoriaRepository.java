package io.github.tiagoiwamoto.apppersonalresumebackend.core.repository;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    Optional<CategoriaEntity> findByNomeIgnoreCase(String nome);

}
