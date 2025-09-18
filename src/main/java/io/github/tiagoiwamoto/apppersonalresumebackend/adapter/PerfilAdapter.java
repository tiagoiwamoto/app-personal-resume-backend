package io.github.tiagoiwamoto.apppersonalresumebackend.adapter;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.PerfilEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil.PerfilCriacaoException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil.PerfilDeletarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil.PerfilRecuperarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.repository.PerfilRepository;
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
public class PerfilAdapter {

    private final PerfilRepository repository;

    @Retryable(maxAttempts = 5, backoff = @Backoff)
    public PerfilEntity solicitarQuePerfilSejaGravado(final PerfilEntity entity){
        try{
            var entitySalva = repository.save(entity);
            log.info("Dados da entidade salva: {}", entitySalva);
            return entitySalva;
        }catch (Exception e){
            log.error("Tentativa: {} de gravar o perfil.", recuperarTentativas());
            throw new PerfilCriacaoException();
        }
    }

    public Optional<PerfilEntity> recuperarPerfilPorNome(final String nome){
        try {
            var entidade = repository.findByNomeIgnoreCase(nome);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o perfil.", recuperarTentativas());
            throw new PerfilRecuperarException();
        }
    }

    public Optional<PerfilEntity> recuperarPerfilPorId(final Long id){
        try {
            var entidade = repository.findById(id);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o perfil.", recuperarTentativas());
            throw new PerfilRecuperarException();
        }
    }

    public List<PerfilEntity> recuperarTodosPerfis(){
        try {
            var registros = repository.findAll();
            return registros;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar os perfis.", recuperarTentativas());
            throw new PerfilRecuperarException();
        }
    }

    public void solicitarQuePerfilSejaRemovido(final PerfilEntity entity){
        try {
            repository.delete(entity);
        }catch (Exception e){
            log.error("Tentativa: {} de remover o perfil.", recuperarTentativas());
            throw new PerfilDeletarException();
        }
    }

    private Integer recuperarTentativas() {
        return RetrySynchronizationManager.getContext() != null
                ? RetrySynchronizationManager.getContext().getRetryCount() + 1
                : 0;
    }
}
