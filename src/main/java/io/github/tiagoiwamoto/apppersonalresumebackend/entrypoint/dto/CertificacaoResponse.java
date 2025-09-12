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
public record CertificacaoResponse(
        Long id,
        String uuid,
        String nome,
        String descricao,
        CategoriaResponse categoria,
        LocalDate dataEmissao,
        LocalDate dataExpiracao,
        String pathCertificado,
        String pathMiniaturaCertificado,
        LocalDateTime timestamp
) {
}
