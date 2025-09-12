package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoJaExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoJaExistenteException() {
        this.code = "10008";
        this.mensagem = "Curso já existe.";
    }
}
