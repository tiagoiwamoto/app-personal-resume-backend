package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificacaoJaExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CertificacaoJaExistenteException() {
        this.code = "10013";
        this.mensagem = "certificação já existe.";
    }
}
