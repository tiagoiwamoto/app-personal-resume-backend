package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificacaoNaoExistenteException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CertificacaoNaoExistenteException() {
        this.code = "10014";
        this.mensagem = "certificação não existe. Solicite a criação.";
    }
}
