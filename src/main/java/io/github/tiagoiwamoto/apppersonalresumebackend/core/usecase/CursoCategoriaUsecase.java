package io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase;

import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CursoCategoriaAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria.CursoCategoriaJaExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria.CursoCategoriaNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper.CursoCategoriaMapper;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoCategoriaRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoCategoriaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursoCategoriaUsecase {

    private final CursoCategoriaAdapter adapter;

    public CursoCategoriaResponse criarNovaCategoria(final CursoCategoriaRequest request){
        adapter.recuperarCategoriaPorNome(request.nome()).ifPresent(c -> {
            log.info("Categoria já existe, não será possível criar uma nova categoria com o mesmo nome. Categoria: {}, request recebido {}", c, request);
            throw new CursoCategoriaJaExistenteException();
        });
        var entidadeConvertida = CursoCategoriaMapper.toCursoCategoriaEntity(request);
        entidadeConvertida.setUuid(UUID.randomUUID().toString());
        var entity = adapter.solicitarQueCategoriaSejaGravada(entidadeConvertida);
        var responseConvertido = CursoCategoriaMapper.toCursoCategoriaResponse(entity);
        log.info("Dados da execução da criação de nova categoria. entidade convertida: {}, response convertido: {}", entidadeConvertida, responseConvertido);
        return responseConvertido;
    }

    public CursoCategoriaResponse editarCategoria(final CursoCategoriaRequest request){
        var entidade = adapter.recuperarCategoriaPorId(request.id())
                .orElseThrow(() -> new CursoCategoriaNaoExistenteException());
        entidade.setNome(request.nome());
        entidade.setDescricao(request.descricacao());
        var entity = adapter.solicitarQueCategoriaSejaGravada(entidade);
        var responseConvertido = CursoCategoriaMapper.toCursoCategoriaResponse(entity);
        log.info("Dados da execução da edição da categoria. entidade atualizada: {}, response convertido: {}", entidade, responseConvertido);
        return responseConvertido;
    }

    public CursoCategoriaResponse recuperarCategoriaPorNome(final CursoCategoriaRequest request){
        var entidade = adapter.recuperarCategoriaPorNome(request.nome())
                .orElseThrow(() -> new CursoCategoriaNaoExistenteException());
        var responseConvertido = CursoCategoriaMapper.toCursoCategoriaResponse(entidade);
        log.info("Dados da execução de recuperação de categoria por nome. entidade: {}, response convertido: {}", entidade, responseConvertido);
        return responseConvertido;
    }

    public CursoCategoriaResponse recuperarCategoriaPeloId(final Long id){
        var entidade = adapter.recuperarCategoriaPorId(id)
                .orElseThrow(() -> new CursoCategoriaNaoExistenteException());
        var responseConvertido = CursoCategoriaMapper.toCursoCategoriaResponse(entidade);
        log.info("Dados da execução de recuperação de categoria por nome. entidade: {}, response convertido: {}", entidade, responseConvertido);
        return responseConvertido;
    }

    public void deletarCategoria(final CursoCategoriaRequest request){
        var entidade = adapter.recuperarCategoriaPorNome(request.nome())
                .orElseThrow(() -> new CursoCategoriaNaoExistenteException());
        adapter.solicitarQueCategoriaSejaRemovida(entidade);
        log.info("Categoria {} foi removida com sucesso.", entidade);
    }

}
