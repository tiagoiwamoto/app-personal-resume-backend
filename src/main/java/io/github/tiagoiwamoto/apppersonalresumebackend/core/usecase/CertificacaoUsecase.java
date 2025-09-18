package io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase;

import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CategoriaAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CertificacaoAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CertificadoImagemAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao.CertificacaoJaExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.certificacao.CertificacaoNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso.CursoNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper.CertificacaoMapper;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CategoriaResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CertificacaoRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CertificacaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificacaoUsecase {

    private final CertificacaoAdapter adapter;
    private final CertificadoImagemAdapter certificadoImagemAdapter;
    private final CategoriaAdapter cursoCategoriaAdapter;
    private final static String CERTIFICACOES = "certificacoes";

    public CertificacaoResponse cadastrarCertificacao(final CertificacaoRequest request,
                                                      final Map<String, String> headers){

        adapter.recuperarCertificadoPorNome(request.nome()).ifPresent(c -> {
            log.info("Certificação já existe, não será possível cadastrar com o mesmo nome. Certificação: {}, request recebido {}", c, request);
            throw new CertificacaoJaExistenteException();
        });

        var categoria = cursoCategoriaAdapter.recuperarCategoriaPorId(
                request.categoriaId()).orElseThrow(() -> new CategoriaNaoExistenteException());

        var certificacaoEntity = CertificacaoMapper.converterParaCertificacaoEntity(request, categoria);
        var certificadoResponse = certificadoImagemAdapter.solicitarArmazenamentoCertificado(request.certificado(), CERTIFICACOES);
        certificacaoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
        certificacaoEntity.setPathCertificado(certificadoResponse.block().caminhoCertificado());
        certificacaoEntity.setPathMiniaturaCertificado(certificadoResponse.block().caminhoMiniaturaCertificado());
        certificacaoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
        certificacaoEntity.setTamanhoArquivoBytes(certificadoResponse.block().tamanhoCertificadoBytes());
        var cursoGravado = adapter.solicitarQueCertificadoSejaGravada(certificacaoEntity);

        return CertificacaoResponse.builder()
                .id(cursoGravado.getId())
                .nome(cursoGravado.getNome())
                .descricao(cursoGravado.getDescricao())
                .uuid(cursoGravado.getUuid())
                .categoria(CategoriaResponse.builder()
                        .id(cursoGravado.getCategoria().getId())
                        .nome(cursoGravado.getNome())
                        .build())
                .dataEmissao(cursoGravado.getDataEmissao())
                .dataExpiracao(cursoGravado.getDataExpiracao())
                .pathCertificado(certificadoResponse.block().caminhoCertificado())
                .pathMiniaturaCertificado(certificadoResponse.block().caminhoMiniaturaCertificado())
                .timestamp(cursoGravado.getTimestamp())
                .build();
    }

    public CertificacaoResponse alterarCertificacao(final CertificacaoRequest request,
                                                    final Map<String, String> headers) {

        var certificacaoEntity = adapter.recuperarCertificadoPorId(request.id())
                .orElseThrow(() -> new CertificacaoNaoExistenteException());


        certificacaoEntity.setNome(request.nome());
        certificacaoEntity.setDescricao(request.descricao());
        certificacaoEntity.setCategoria(cursoCategoriaAdapter.recuperarCategoriaPorId(
                request.categoriaId()).orElseThrow(() -> new CategoriaNaoExistenteException()));
        certificacaoEntity.setDataEmissao(request.dataEmissao());
        certificacaoEntity.setDataExpiracao(request.dataExpiracao());
        certificacaoEntity.setTimestamp(LocalDateTime.now());
        if (request.certificado() != null) {
            var certificadoResponse = certificadoImagemAdapter.solicitarAlteracaoCertificado(
                    request.certificado(),
                    CERTIFICACOES,
                    certificacaoEntity.getPathCertificado(),
                    certificacaoEntity.getPathMiniaturaCertificado());
            certificacaoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
            certificacaoEntity.setPathCertificado(certificadoResponse.block().caminhoCertificado());
            certificacaoEntity.setPathMiniaturaCertificado(certificadoResponse.block().caminhoMiniaturaCertificado());
            certificacaoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
            certificacaoEntity.setTamanhoArquivoBytes(certificadoResponse.block().tamanhoCertificadoBytes());
        }
        var cursoGravado = adapter.solicitarQueCertificadoSejaGravada(certificacaoEntity);

        return CertificacaoResponse.builder()
                .id(cursoGravado.getId())
                .nome(cursoGravado.getNome())
                .nome(cursoGravado.getDescricao())
                .uuid(cursoGravado.getUuid())
                .categoria(CategoriaResponse.builder().id(cursoGravado.getCategoria().getId()).build())
                .dataEmissao(cursoGravado.getDataEmissao())
                .dataExpiracao(cursoGravado.getDataExpiracao())
                .pathCertificado(cursoGravado.getPathCertificado())
                .pathMiniaturaCertificado(cursoGravado.getPathMiniaturaCertificado())
                .build();
    }

    public CertificacaoResponse recuperarCertificacao(final Long id, final Map<String, String> headers){
        var cursoEntity = adapter.recuperarCertificadoPorId(id)
                .orElseThrow(() -> new CertificacaoNaoExistenteException());
        return CertificacaoResponse.builder()
                .id(cursoEntity.getId())
                .nome(cursoEntity.getNome())
                .descricao(cursoEntity.getDescricao())
                .uuid(cursoEntity.getUuid())
                .categoria(CategoriaResponse.builder()
                        .id(cursoEntity.getCategoria().getId())
                        .nome(cursoEntity.getCategoria().getNome())
                        .build())
                .dataEmissao(cursoEntity.getDataEmissao())
                .dataExpiracao(cursoEntity.getDataExpiracao())
                .pathCertificado(cursoEntity.getPathCertificado())
                .pathMiniaturaCertificado(cursoEntity.getPathMiniaturaCertificado())
                .timestamp(cursoEntity.getTimestamp())
                .build();
    }

    public List<CertificacaoResponse> recuperarTodasCertificacoes(){
        return adapter.recuperarTodasCertificados()
                .stream()
                .map(cursoEntity -> CertificacaoResponse.builder()
                        .id(cursoEntity.getId())
                        .nome(cursoEntity.getNome())
                        .descricao(cursoEntity.getDescricao())
                        .uuid(cursoEntity.getUuid())
                        .categoria(CategoriaResponse.builder()
                                .id(cursoEntity.getCategoria().getId())
                                .nome(cursoEntity.getCategoria().getNome())
                                .build())
                        .dataEmissao(cursoEntity.getDataEmissao())
                        .dataExpiracao(cursoEntity.getDataExpiracao())
                        .pathCertificado(cursoEntity.getPathCertificado())
                        .pathMiniaturaCertificado(cursoEntity.getPathMiniaturaCertificado())
                        .timestamp(cursoEntity.getTimestamp())
                        .build())
                .toList();
    }

    public void removerCertificacao(final Long id){
        var cursoEntity = adapter.recuperarCertificadoPorId(id)
                .orElseThrow(() -> new CursoNaoExistenteException());
        certificadoImagemAdapter.solicitarRemocaoCertificado(cursoEntity.getPathCertificado(),
                cursoEntity.getPathMiniaturaCertificado());
        adapter.solicitarQueCertificadoSejaRemovida(cursoEntity);
    }

}
