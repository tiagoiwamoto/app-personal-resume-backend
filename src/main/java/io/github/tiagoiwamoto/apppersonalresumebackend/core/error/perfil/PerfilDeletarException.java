package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerfilDeletarException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public PerfilDeletarException() {
        this.code = "10017";
        this.mensagem = "Falha ao remover a categoria do curso.";
    }
}
