package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.EscolaEnum;
import lombok.Builder;
import org.springframework.http.codec.multipart.FilePart;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CursoRequest(
        Long id,
        String uuid,
        String nome,
        String descricao,
        EscolaEnum escola,
        Long categoriaId,
        Integer duracaoHoras,
        LocalDate dataInicio,
        LocalDate dataFim,
        LocalDateTime timestamp,
        FilePart certificado
) {
}
