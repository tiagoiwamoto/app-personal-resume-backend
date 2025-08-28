package io.github.tiagoiwamoto.apppersonalresumebackend.adapter;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CursoCategoriaEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria.CursoCategoriaCriacaoException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria.CursoCategoriaDeletarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria.CursoCategoriaRecuperarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.repository.CursoCategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursoCategoriaAdapter {

    private final CursoCategoriaRepository repository;

    @Retryable(maxAttempts = 5, backoff = @Backoff)
    public CursoCategoriaEntity solicitarQueCategoriaSejaGravada(final CursoCategoriaEntity entity){
        try{
            var entitySalva = repository.save(entity);
            log.info("Dados da entidade salva: {}", entitySalva);
            return entitySalva;
        }catch (Exception e){
            log.error("Tentativa: {} de gravar o curso da categoria.", recuperarTentativas());
            throw new CursoCategoriaCriacaoException();
        }
    }

    public Optional<CursoCategoriaEntity> recuperarCategoriaPorNome(final String nome){
        try {
            var entidade = repository.findByNomeIgnoreCase(nome);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o categoria.", recuperarTentativas());
            throw new CursoCategoriaRecuperarException();
        }
    }

    public Optional<CursoCategoriaEntity> recuperarCategoriaPorId(final Long id){
        try {
            var entidade = repository.findById(id);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o categoria.", recuperarTentativas());
            throw new CursoCategoriaRecuperarException();
        }
    }

    public void solicitarQueCategoriaSejaRemovida(final CursoCategoriaEntity entity){
        try {
            repository.delete(entity);
        }catch (Exception e){
            log.error("Tentativa: {} de remover a categoria.", recuperarTentativas());
            throw new CursoCategoriaDeletarException();
        }
    }

    private Integer recuperarTentativas() {
        return RetrySynchronizationManager.getContext() != null
                ? RetrySynchronizationManager.getContext().getRetryCount() + 1
                : 0;
    }
}
