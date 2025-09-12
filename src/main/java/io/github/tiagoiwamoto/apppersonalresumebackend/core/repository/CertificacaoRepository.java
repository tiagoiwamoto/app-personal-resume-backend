package io.github.tiagoiwamoto.apppersonalresumebackend.core.repository;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CertificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificacaoRepository extends JpaRepository<CertificacaoEntity, Long> {

    Optional<CertificacaoEntity> findByNomeIgnoreCase(String nome);

}
