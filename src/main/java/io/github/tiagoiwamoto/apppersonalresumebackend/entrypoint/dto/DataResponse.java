package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DataResponse<T>(
        T data
) {
}
