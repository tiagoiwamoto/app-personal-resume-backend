package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.EscolaEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CursoResponse(
        Long id,
        String uuid,
        String nome,
        EscolaEnum escola,
        CategoriaResponse categoria,
        Integer duracaoHoras,
        LocalDate dataInicio,
        LocalDate dataFim,
        String certificadoUrl,
        String miniaturaCertificadoUrl,
        LocalDateTime timestamp
) {
}
