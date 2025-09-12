package io.github.tiagoiwamoto.apppersonalresumebackend.adapter;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CategoriaEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaCriacaoException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaDeletarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaRecuperarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.repository.CategoriaRepository;
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
public class CategoriaAdapter {

    private final CategoriaRepository repository;

    @Retryable(maxAttempts = 5, backoff = @Backoff)
    public CategoriaEntity solicitarQueCategoriaSejaGravada(final CategoriaEntity entity){
        try{
            var entitySalva = repository.save(entity);
            log.info("Dados da entidade salva: {}", entitySalva);
            return entitySalva;
        }catch (Exception e){
            log.error("Tentativa: {} de gravar o curso da categoria.", recuperarTentativas());
            throw new CategoriaCriacaoException();
        }
    }

    public Optional<CategoriaEntity> recuperarCategoriaPorNome(final String nome){
        try {
            var entidade = repository.findByNomeIgnoreCase(nome);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o categoria.", recuperarTentativas());
            throw new CategoriaRecuperarException();
        }
    }

    public Optional<CategoriaEntity> recuperarCategoriaPorId(final Long id){
        try {
            var entidade = repository.findById(id);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o categoria.", recuperarTentativas());
            throw new CategoriaRecuperarException();
        }
    }

    public List<CategoriaEntity> recuperarTodasCategorias(){
        try {
            var registros = repository.findAll();
            return registros;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o categoria.", recuperarTentativas());
            throw new CategoriaRecuperarException();
        }
    }

    public void solicitarQueCategoriaSejaRemovida(final CategoriaEntity entity){
        try {
            repository.delete(entity);
        }catch (Exception e){
            log.error("Tentativa: {} de remover a categoria.", recuperarTentativas());
            throw new CategoriaDeletarException();
        }
    }

    private Integer recuperarTentativas() {
        return RetrySynchronizationManager.getContext() != null
                ? RetrySynchronizationManager.getContext().getRetryCount() + 1
                : 0;
    }
}
