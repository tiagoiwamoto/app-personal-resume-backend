package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaCriacaoException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CategoriaCriacaoException() {
        this.code = "10001";
        this.mensagem = "Erro ao gravar a categoria do curso.";
    }
}
