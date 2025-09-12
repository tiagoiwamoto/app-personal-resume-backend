package io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CategoriaEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CursoEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class CursoMapper {

    private CursoMapper() {
    }

    public static CursoEntity converterParaCursoEntity(final CursoRequest request,
                                                       final CategoriaEntity categoria) {
        return CursoEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .nome(request.nome())
                .descricao(request.descricao())
                .categoria(categoria)
                .escola(request.escola())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .duracaoHoras(request.duracaoHoras())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
