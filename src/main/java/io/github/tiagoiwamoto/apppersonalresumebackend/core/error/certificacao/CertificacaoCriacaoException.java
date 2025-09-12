package io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificacaoCriacaoException extends RuntimeException{

    private String code;
    private Map<String, Object> data;
    private String mensagem;

    public CertificacaoCriacaoException() {
        this.code = "10011";
        this.mensagem = "Erro ao gravar a certificação.";
    }
}
