package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoCategoriaCriacaoException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoCategoriaCriacaoException() {
        this.code = "10001";
        this.mensagem = "Erro ao criar a categoria do curso.";
    }
}
