package io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CategoriaEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.entity.CertificacaoEntity;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CertificacaoRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class CertificacaoMapper {

    private CertificacaoMapper() {
    }

    public static CertificacaoEntity converterParaCertificacaoEntity(final CertificacaoRequest request,
                                                                     final CategoriaEntity categoria) {
        return CertificacaoEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .nome(request.nome())
                .descricao(request.descricao())
                .categoria(categoria)
                .dataEmissao(request.dataEmissao())
                .dataExpiracao(request.dataExpiracao())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
