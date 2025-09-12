package io.github.tiagoiwamoto.apppersonalresumebackend.adapter.dto;

import lombok.Builder;

@Builder
public record CertificadoResponse(

        String caminhoCertificado,
        String caminhoMiniaturaCertificado,
        String nomeArquivoOriginal,
        String nomeCertificadoGerado,
        String nomeMiniaturaCertificadoGerado,
        Long tamanhoCertificadoBytes,
        Long tamanhoMiniaturaCertificadoBytes

) {
}
