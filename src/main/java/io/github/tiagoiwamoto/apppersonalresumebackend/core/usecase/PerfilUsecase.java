package io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase;

import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.PerfilAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.PerfilEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil.PerfilCriacaoException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil.PerfilJaExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil.PerfilNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.PerfilRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.PerfilResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerfilUsecase {

    private final PerfilAdapter adapter;

    public PerfilResponse criarNovoPerfil(final PerfilRequest request){
        adapter.recuperarPerfilPorNome(request.nome()).ifPresent(c -> {
            log.info("Perfil já existe, não será possível criar um novo perfil com o mesmo nome. Perfil: {}, request recebido {}", c, request);
            throw new PerfilJaExistenteException();
        });
        var entidadeConvertida = PerfilEntity.builder()
                .nome(request.nome())
                .uuid(UUID.randomUUID().toString())
                .descricao(request.descricao())
                .dataCriacao(request.dataCriacao())
                .status(request.status())
                .timestamp(LocalDateTime.now())
                .build();
        var entity = adapter.solicitarQuePerfilSejaGravado(entidadeConvertida);
        var responseConvertido = PerfilResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .uuid(entity.getUuid())
                .descricao(entity.getDescricao())
                .dataCriacao(entity.getDataCriacao())
                .status(entity.getStatus())
                .timestamp(entity.getTimestamp())
                .build();
        log.info("Dados da execução da criação de novo perfil. entidade convertida: {}, response convertido: {}", entidadeConvertida, responseConvertido);
        return responseConvertido;
    }

    public PerfilResponse editarPerfil(final PerfilRequest request){
        var entidade = adapter.recuperarPerfilPorId(request.id())
                .orElseThrow(() -> new PerfilNaoExistenteException());
        entidade.setNome(request.nome());
        entidade.setDescricao(request.descricao());
        entidade.setStatus(request.status());
        entidade.setTimestamp(LocalDateTime.now());
        var entity = adapter.solicitarQuePerfilSejaGravado(entidade);
        var responseConvertido = PerfilResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .uuid(entity.getUuid())
                .descricao(entity.getDescricao())
                .dataCriacao(entity.getDataCriacao())
                .status(entity.getStatus())
                .timestamp(entity.getTimestamp())
                .build();
        log.info("Dados da execução da edição do perfil. entidade atualizada: {}, response convertido: {}", entidade, responseConvertido);
        return responseConvertido;
    }

    public PerfilResponse recuperarPerfilPorNome(final PerfilRequest request){
        var entity = adapter.recuperarPerfilPorNome(request.nome())
                .orElseThrow(() -> new PerfilNaoExistenteException());
        var responseConvertido = PerfilResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .uuid(entity.getUuid())
                .descricao(entity.getDescricao())
                .dataCriacao(entity.getDataCriacao())
                .status(entity.getStatus())
                .timestamp(entity.getTimestamp())
                .build();
        log.info("Dados da execução de recuperação de perfil por nome. entidade: {}, response convertido: {}", entity, responseConvertido);
        return responseConvertido;
    }

    public PerfilResponse recuperarPerfilPeloId(final Long id){
        var entity = adapter.recuperarPerfilPorId(id)
                .orElseThrow(() -> new PerfilNaoExistenteException());
        var responseConvertido = PerfilResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .uuid(entity.getUuid())
                .descricao(entity.getDescricao())
                .dataCriacao(entity.getDataCriacao())
                .status(entity.getStatus())
                .timestamp(entity.getTimestamp())
                .build();
        log.info("Dados da execução de recuperação de perfil por nome. entidade: {}, response convertido: {}", entity, responseConvertido);
        return responseConvertido;
    }

    public List<PerfilResponse> recuperarTodosPerfis(){
        var registros = adapter.recuperarTodosPerfis()
                .stream()
                .map(entity -> PerfilResponse.builder()
                        .id(entity.getId())
                        .nome(entity.getNome())
                        .uuid(entity.getUuid())
                        .descricao(entity.getDescricao())
                        .dataCriacao(entity.getDataCriacao())
                        .status(entity.getStatus())
                        .timestamp(entity.getTimestamp())
                        .build())
                .toList();
        log.info("Dados da execução de recuperação de perfis. total de registros: {}", registros.size());
        return registros;
    }

    public void deletarPerfil(final Long id){
        var entidade = adapter.recuperarPerfilPorId(id)
                .orElseThrow(() -> new PerfilCriacaoException());
        adapter.solicitarQuePerfilSejaRemovido(entidade);
        log.info("Perfil {} foi removido com sucesso.", entidade);
    }

}
