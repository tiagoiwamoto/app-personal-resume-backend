package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String codigo,
        String mensagem,
        Map<String, Object> detalhes,
        Integer httpStatus,
        LocalDateTime timestamp
) {
}
