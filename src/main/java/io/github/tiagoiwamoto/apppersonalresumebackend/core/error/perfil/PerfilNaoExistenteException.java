package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.perfil;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerfilNaoExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public PerfilNaoExistenteException() {
        this.code = "10019";
        this.mensagem = "Categoria do curso não existe. Solicite a criação da categoria.";
    }
}
