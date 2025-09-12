package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaRecuperarException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CategoriaRecuperarException() {
        this.code = "10002";
        this.mensagem = "Erro ao recuperar a categoria do curso, tente novamente mais tarde.";
    }
}
