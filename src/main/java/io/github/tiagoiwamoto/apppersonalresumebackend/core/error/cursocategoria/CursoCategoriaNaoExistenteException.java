package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoCategoriaNaoExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoCategoriaNaoExistenteException() {
        this.code = "10003";
        this.mensagem = "Categoria do curso não existe. Solicite a criação da categoria.";
    }
}
