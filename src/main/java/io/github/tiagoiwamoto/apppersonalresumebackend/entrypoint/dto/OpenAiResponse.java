package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OpenAiResponse(
        String input,
        String output,
        LocalDateTime timestamp
) {
}
