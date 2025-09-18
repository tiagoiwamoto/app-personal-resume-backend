package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerfilCriacaoException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public PerfilCriacaoException() {
        this.code = "10016";
        this.mensagem = "Erro ao gravar a categoria do curso.";
    }
}
