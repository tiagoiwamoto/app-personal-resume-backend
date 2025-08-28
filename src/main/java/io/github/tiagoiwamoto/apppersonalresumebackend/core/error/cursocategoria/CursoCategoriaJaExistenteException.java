package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoCategoriaJaExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoCategoriaJaExistenteException() {
        this.code = "10004";
        this.mensagem = "Categoria do curso já existe.";
    }
}
