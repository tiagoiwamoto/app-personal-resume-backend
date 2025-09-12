package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import java.time.LocalDateTime;

public record OpenAiRequest(
        String input,
        String output,
        LocalDateTime timestamp
) {
}
