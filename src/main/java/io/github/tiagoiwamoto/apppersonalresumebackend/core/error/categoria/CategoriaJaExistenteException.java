package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaJaExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CategoriaJaExistenteException() {
        this.code = "10004";
        this.mensagem = "Categoria do curso já existe.";
    }
}
