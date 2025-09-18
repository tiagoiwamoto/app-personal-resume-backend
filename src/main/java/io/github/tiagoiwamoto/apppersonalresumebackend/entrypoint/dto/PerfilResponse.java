package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PerfilResponse(
        Long id,
        String nome,
        String uuid,
        String descricao,
        LocalDate dataCriacao,
        Boolean status,
        LocalDateTime timestamp
) {
}
