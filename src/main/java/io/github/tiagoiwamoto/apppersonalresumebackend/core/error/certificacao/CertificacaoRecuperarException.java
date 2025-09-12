package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificacaoRecuperarException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CertificacaoRecuperarException() {
        this.code = "10015";
        this.mensagem = "Erro ao recuperar certificação, tente novamente mais tarde.";
    }
}
