package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.EscolaEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CursoResponse(
        Long id,
        String uuid,
        String nome,
        String descricao,
        String escola,
        CategoriaResponse categoria,
        Integer duracaoHoras,
        LocalDate dataInicio,
        LocalDate dataFim,
        String certificadoUrl,
        String miniaturaCertificadoUrl,
        LocalDateTime timestamp
) {
}
