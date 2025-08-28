package io.github.tiagoiwamoto.apppersonalresumebackend.config;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria.CursoCategoriaCriacaoException;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.DataResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(CursoCategoriaCriacaoException.class)
    public ResponseEntity<DataResponse<ErrorResponse>> tratarCursoCategoriaCriacaoException(CursoCategoriaCriacaoException ex) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        var errorResponse = ErrorResponse.builder()
                .codigo(ex.getCode())
                .mensagem(ex.getMensagem())
                .detalhes(ex.getData())
                .httpStatus(status.value())
                .timestamp(LocalDateTime.now())
                .build();
        var dataResponse = DataResponse.<ErrorResponse>builder()
                .data(errorResponse)
                .build();
        return ResponseEntity.status(status).body(dataResponse);
    }

}
