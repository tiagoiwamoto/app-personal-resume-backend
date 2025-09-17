package io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase;

import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CategoriaAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaJaExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper.CategoriaMapper;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CategoriaRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CategoriaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaUsecase {

    private final CategoriaAdapter adapter;

    public CategoriaResponse criarNovaCategoria(final CategoriaRequest request){
        adapter.recuperarCategoriaPorNome(request.nome()).ifPresent(c -> {
            log.info("Categoria já existe, não será possível criar uma nova categoria com o mesmo nome. Categoria: {}, request recebido {}", c, request);
            throw new CategoriaJaExistenteException();
        });
        var entidadeConvertida = CategoriaMapper.toCursoCategoriaEntity(request);
        entidadeConvertida.setUuid(UUID.randomUUID().toString());
        var entity = adapter.solicitarQueCategoriaSejaGravada(entidadeConvertida);
        var responseConvertido = CategoriaMapper.toCursoCategoriaResponse(entity);
        log.info("Dados da execução da criação de nova categoria. entidade convertida: {}, response convertido: {}", entidadeConvertida, responseConvertido);
        return responseConvertido;
    }

    public CategoriaResponse editarCategoria(final CategoriaRequest request){
        var entidade = adapter.recuperarCategoriaPorId(request.id())
                .orElseThrow(() -> new CategoriaNaoExistenteException());
        entidade.setNome(request.nome());
        entidade.setDescricao(request.descricao());
        var entity = adapter.solicitarQueCategoriaSejaGravada(entidade);
        var responseConvertido = CategoriaMapper.toCursoCategoriaResponse(entity);
        log.info("Dados da execução da edição da categoria. entidade atualizada: {}, response convertido: {}", entidade, responseConvertido);
        return responseConvertido;
    }

    public CategoriaResponse recuperarCategoriaPorNome(final CategoriaRequest request){
        var entidade = adapter.recuperarCategoriaPorNome(request.nome())
                .orElseThrow(() -> new CategoriaNaoExistenteException());
        var responseConvertido = CategoriaMapper.toCursoCategoriaResponse(entidade);
        log.info("Dados da execução de recuperação de categoria por nome. entidade: {}, response convertido: {}", entidade, responseConvertido);
        return responseConvertido;
    }

    public CategoriaResponse recuperarCategoriaPeloId(final Long id){
        var entidade = adapter.recuperarCategoriaPorId(id)
                .orElseThrow(() -> new CategoriaNaoExistenteException());
        var responseConvertido = CategoriaMapper.toCursoCategoriaResponse(entidade);
        log.info("Dados da execução de recuperação de categoria por nome. entidade: {}, response convertido: {}", entidade, responseConvertido);
        return responseConvertido;
    }

    public List<CategoriaResponse> recuperarTodasCategoria(){
        var registros = adapter.recuperarTodasCategorias()
                .stream()
                .map(CategoriaMapper::toCursoCategoriaResponse)
                .toList();
        log.info("Dados da execução de recuperação de categorias. total de registros: {}", registros.size());
        return registros;
    }

    public void deletarCategoria(final Long id){
        var entidade = adapter.recuperarCategoriaPorId(id)
                .orElseThrow(() -> new CategoriaNaoExistenteException());
        adapter.solicitarQueCategoriaSejaRemovida(entidade);
        log.info("Categoria {} foi removida com sucesso.", entidade);
    }

}
