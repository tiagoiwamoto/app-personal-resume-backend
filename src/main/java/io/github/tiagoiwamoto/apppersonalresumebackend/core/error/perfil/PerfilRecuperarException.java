package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerfilRecuperarException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public PerfilRecuperarException() {
        this.code = "10020";
        this.mensagem = "Erro ao recuperar a categoria do curso, tente novamente mais tarde.";
    }
}
