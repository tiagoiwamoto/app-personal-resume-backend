package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.cursocategoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoCategoriaDeletarException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CursoCategoriaDeletarException() {
        this.code = "10005";
        this.mensagem = "Falha ao remover a categoria do curso.";
    }
}
