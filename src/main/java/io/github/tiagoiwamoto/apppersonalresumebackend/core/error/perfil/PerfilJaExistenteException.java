package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerfilJaExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public PerfilJaExistenteException() {
        this.code = "10018";
        this.mensagem = "Categoria do curso já existe.";
    }
}
