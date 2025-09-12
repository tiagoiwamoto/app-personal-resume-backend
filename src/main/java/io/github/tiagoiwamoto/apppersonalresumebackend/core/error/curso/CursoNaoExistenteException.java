package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoNaoExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoNaoExistenteException() {
        this.code = "10009";
        this.mensagem = "Curso não existe. Solicite a criação.";
    }
}
