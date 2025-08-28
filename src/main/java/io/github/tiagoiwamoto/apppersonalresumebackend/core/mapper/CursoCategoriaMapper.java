package io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CursoCategoriaEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoCategoriaRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoCategoriaResponse;

import java.time.LocalDateTime;

public class CursoCategoriaMapper {

    private CursoCategoriaMapper() {
    }

    public static CursoCategoriaEntity toCursoCategoriaEntity(final CursoCategoriaRequest request){
        var entity = CursoCategoriaEntity.builder()
                .dataCriacao(LocalDateTime.now())
                .nome(request.nome())
                .descricao(request.descricacao()) //TODO: integrar com chat gpt para criar descrições automáticas
                .build();
        return entity;
    }

    public static CursoCategoriaResponse toCursoCategoriaResponse(final CursoCategoriaEntity entidade){
        var entity = CursoCategoriaResponse.builder()
                .dataCriacao(LocalDateTime.now())
                .nome(entidade.getNome())
                .descricao(entidade.getDescricao())
                .dataCriacao(entidade.getDataCriacao())
                .id(entidade.getUuid())
                .build();
        return entity;
    }
}
