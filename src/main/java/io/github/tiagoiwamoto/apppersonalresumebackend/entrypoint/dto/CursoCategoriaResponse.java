package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CursoCategoriaResponse(
        String id,
        String nome,
        String descricao,
        LocalDateTime dataCriacao
) {
}
