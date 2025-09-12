package io.github.tiagoiwamoto.apppersonalresumebackend.core.repository;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CursoEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.EscolaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<CursoEntity, Long> {

    Optional<CursoEntity> findByNomeIgnoreCase(String nome);
    List<CursoEntity> findByEscola(EscolaEnum escola);
    List<CursoEntity> findAllByDataInicioAndDataFim(LocalDate dataInicio, LocalDate dataFim);

}
