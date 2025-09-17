package io.github.tiagoiwamoto.apppersonalresumebackend.adapter;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CursoEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso.CursoCriacaoException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso.CursoDeletarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso.CursoRecuperarException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.repository.CursoRepository;
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
public class CursoAdapter {

    private final CursoRepository repository;

    @Retryable(maxAttempts = 5, backoff = @Backoff)
    public CursoEntity solicitarQueCursoSejaGravada(final CursoEntity entity){
        try{
            var entitySalva = repository.save(entity);
            log.info("Dados da entidade salva: {}", entitySalva);
            return entitySalva;
        }catch (Exception e){
            log.error("Tentativa: {} de gravar o curso da categoria.", recuperarTentativas());
            throw new CursoCriacaoException();
        }
    }

    public Optional<CursoEntity> recuperarCursoPorNome(final String nome){
        try {
            var entidade = repository.findByNomeIgnoreCase(nome);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o curso.", recuperarTentativas());
            throw new CursoRecuperarException();
        }
    }

    public Optional<CursoEntity> recuperarCursoPorId(final Long id){
        try {
            var entidade = repository.findById(id);
            return entidade;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o curso.", recuperarTentativas());
            throw new CursoRecuperarException();
        }
    }

    public List<CursoEntity> recuperarTodosCursos(){
        try {
            var registros = repository.findAll();
            return registros;
        }catch (Exception e){
            log.error("Tentativa: {} de recuperar o curso.", recuperarTentativas());
            throw new CursoRecuperarException();
        }
    }

    public void solicitarQueCursoSejaRemovida(final CursoEntity entity){
        try {
            repository.delete(entity);
        }catch (Exception e){
            log.error("Tentativa: {} de remover a curso.", recuperarTentativas());
            throw new CursoDeletarException();
        }
    }

    private Integer recuperarTentativas() {
        return RetrySynchronizationManager.getContext() != null
                ? RetrySynchronizationManager.getContext().getRetryCount() + 1
                : 0;
    }
}
