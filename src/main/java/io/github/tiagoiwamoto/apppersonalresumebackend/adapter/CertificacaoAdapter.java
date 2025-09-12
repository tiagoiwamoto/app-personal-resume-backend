package io.github.tiagoiwamoto.apppersonalresumebackend.adapter;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CertificacaoEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao.CertificacaoCriacaoException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao.CertificacaoDeletarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao.CertificacaoRecuperarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.repository.CertificacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificacaoAdapter {

    private final CertificacaoRepository repository;

    @Retryable(maxAttempts = 5, backoff = @Backoff)
    public CertificacaoEntity solicitarQueCertificadoSejaGravada(final CertificacaoEntity entity){
        try{
            var entitySalva = repository.save(entity);
            log.info("Dados da entidade salva: {}", entitySalva);
            return entitySalva;
        }catch (Exception e){
            log.error("Tentativa: {} de gravar o curso da certificado.", recuperarTentativas());
            throw new CertificacaoCriacaoException();
        }
    }

    public Optional<CertificacaoEntity> recuperarCertificadoPorNome(final String nome){
        try {
            var entidade = repository.findByNomeIgnoreCase(nome);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o certificado.", recuperarTentativas());
            throw new CertificacaoRecuperarException();
        }
    }

    public Optional<CertificacaoEntity> recuperarCertificadoPorId(final Long id){
        try {
            var entidade = repository.findById(id);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o certificado.", recuperarTentativas());
            throw new CertificacaoRecuperarException();
        }
    }

    public List<CertificacaoEntity> recuperarTodasCertificados(){
        try {
            var registros = repository.findAll();
            return registros;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o Certificado.", recuperarTentativas());
            throw new CertificacaoRecuperarException();
        }
    }

    public void solicitarQueCertificadoSejaRemovida(final CertificacaoEntity entity){
        try {
            repository.delete(entity);
        }catch (Exception e){
            log.error("Tentativa: {} de remover a certificado.", recuperarTentativas());
            throw new CertificacaoDeletarException();
        }
    }

    private Integer recuperarTentativas() {
        return RetrySynchronizationManager.getContext() != null
                ? RetrySynchronizationManager.getContext().getRetryCount() + 1
                : 0;
    }
}
