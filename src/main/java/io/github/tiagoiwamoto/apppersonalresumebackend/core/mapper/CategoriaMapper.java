package io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CategoriaEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CategoriaRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CategoriaResponse;

import java.time.LocalDateTime;

public class CategoriaMapper {

    private CategoriaMapper() {
    }

    public static CategoriaEntity toCursoCategoriaEntity(final CategoriaRequest request){
        var entity = CategoriaEntity.builder()
                .dataCriacao(LocalDateTime.now())
                .nome(request.nome())
                .descricao(request.descricao()) //TODO: integrar com chat gpt para criar descrições automáticas
                .build();
        return entity;
    }

    public static CategoriaResponse toCursoCategoriaResponse(final CategoriaEntity entidade){
        var entity = CategoriaResponse.builder()
                .dataCriacao(LocalDateTime.now())
                .nome(entidade.getNome())
                .descricao(entidade.getDescricao())
                .dataCriacao(entidade.getDataCriacao())
                .id(entidade.getId())
                .uuid(entidade.getUuid())
                .build();
        return entity;
    }
}
