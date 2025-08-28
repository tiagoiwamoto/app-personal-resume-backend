package io.github.tiagoiwamoto.apppersonalresumebackend.core.repository;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CursoCategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoCategoriaRepository extends JpaRepository<CursoCategoriaEntity, Long> {

    Optional<CursoCategoriaEntity> findByNomeIgnoreCase(String nome);

}
