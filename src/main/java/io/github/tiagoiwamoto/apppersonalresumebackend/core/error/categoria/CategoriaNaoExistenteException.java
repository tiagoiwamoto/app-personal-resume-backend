package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaNaoExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CategoriaNaoExistenteException() {
        this.code = "10003";
        this.mensagem = "Categoria do curso não existe. Solicite a criação da categoria.";
    }
}
