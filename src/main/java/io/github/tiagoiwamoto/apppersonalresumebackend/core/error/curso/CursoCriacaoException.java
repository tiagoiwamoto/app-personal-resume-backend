package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoCriacaoException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoCriacaoException() {
        this.code = "10006";
        this.mensagem = "Erro ao gravar a categoria do curso.";
    }
}
