package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoRecuperarException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoRecuperarException() {
        this.code = "10010";
        this.mensagem = "Erro ao recuperar curso, tente novamente mais tarde.";
    }
}
