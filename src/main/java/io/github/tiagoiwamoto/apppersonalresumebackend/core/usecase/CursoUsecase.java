package io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase;

import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CategoriaAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CertificadoImagemAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.CursoAdapter;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaJaExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.categoria.CategoriaNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.error.curso.CursoNaoExistenteException;
import io.github.tiagoiwamoto.apppersonalresumebackend.core.mapper.CursoMapper;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CategoriaResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursoUsecase {

    private final CursoAdapter adapter;
    private final CertificadoImagemAdapter certificadoImagemAdapter;
    private final CategoriaAdapter cursoCategoriaAdapter;
    private final static String CURSOS = "cursos";

    public CursoResponse cadastrarCursoConcluido(final CursoRequest request,
                                                 final Map<String, String> headers){

        adapter.recuperarCursoPorNome(request.nome()).ifPresent(c -> {
            log.info("Curso já existe, não será possível cadastrar com o mesmo nome. Curso: {}, request recebido {}", c, request);
            throw new CategoriaJaExistenteException();
        });

        var categoria = cursoCategoriaAdapter.recuperarCategoriaPorId(
                request.categoriaId()).orElseThrow(() -> new CategoriaNaoExistenteException());

        var cursoEntity = CursoMapper.converterParaCursoEntity(request, categoria);
        var certificadoResponse = certificadoImagemAdapter.solicitarArmazenamentoCertificado(request.certificado(), CURSOS);
        cursoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
        cursoEntity.setPathCertificado(certificadoResponse.block().caminhoCertificado());
        cursoEntity.setPathMiniaturaCertificado(certificadoResponse.block().caminhoMiniaturaCertificado());
        cursoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
        cursoEntity.setTamanhoArquivoBytes(certificadoResponse.block().tamanhoCertificadoBytes());
        var cursoGravado = adapter.solicitarQueCursoSejaGravada(cursoEntity);

        return CursoResponse.builder()
                .id(cursoGravado.getId())
                .nome(cursoGravado.getNome())
                .descricao(cursoGravado.getDescricao())
                .uuid(cursoGravado.getUuid())
                .escola(cursoGravado.getEscola().getNome())
                .categoria(CategoriaResponse.builder()
                        .id(cursoGravado.getCategoria().getId())
                        .nome(cursoGravado.getCategoria().getNome())
                        .build())
                .dataInicio(cursoGravado.getDataInicio())
                .dataFim(cursoGravado.getDataFim())
                .duracaoHoras(cursoGravado.getDuracaoHoras())
                .certificadoUrl(certificadoResponse.block().caminhoCertificado())
                .miniaturaCertificadoUrl(certificadoResponse.block().caminhoMiniaturaCertificado())
                .timestamp(cursoGravado.getTimestamp())
                .build();
    }

    public CursoResponse alterarCursoConcluido(final CursoRequest request,
                                               final Map<String, String> headers) {

        var cursoEntity = adapter.recuperarCursoPorId(request.id())
                .orElseThrow(() -> new CursoNaoExistenteException());


        cursoEntity.setNome(request.nome());
        cursoEntity.setEscola(request.escola());
        cursoEntity.setCategoria(cursoCategoriaAdapter.recuperarCategoriaPorId(
                request.categoriaId()).orElseThrow(() -> new CategoriaNaoExistenteException()));
        cursoEntity.setDataInicio(request.dataInicio());
        cursoEntity.setDataFim(request.dataFim());
        cursoEntity.setDuracaoHoras(request.duracaoHoras());
        cursoEntity.setTimestamp(LocalDateTime.now());
        if (request.certificado() != null) {
            var certificadoResponse = certificadoImagemAdapter.solicitarAlteracaoCertificado(
                    request.certificado(),
                    CURSOS,
                    cursoEntity.getPathCertificado(),
                    cursoEntity.getPathMiniaturaCertificado());
            cursoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
            cursoEntity.setPathCertificado(certificadoResponse.block().caminhoCertificado());
            cursoEntity.setPathMiniaturaCertificado(certificadoResponse.block().caminhoMiniaturaCertificado());
            cursoEntity.setNomeArquivoOriginal(certificadoResponse.block().nomeArquivoOriginal());
            cursoEntity.setTamanhoArquivoBytes(certificadoResponse.block().tamanhoCertificadoBytes());
        }
        var cursoGravado = adapter.solicitarQueCursoSejaGravada(cursoEntity);

        return CursoResponse.builder()
                .id(cursoGravado.getId())
                .nome(cursoGravado.getNome())
                .descricao(cursoGravado.getDescricao())
                .uuid(cursoGravado.getUuid())
                .escola(cursoGravado.getEscola().getNome())
                .categoria(CategoriaResponse.builder()
                        .id(cursoGravado.getCategoria().getId())
                        .nome(cursoGravado.getCategoria().getNome())
                        .build())
                .dataInicio(cursoGravado.getDataInicio())
                .dataFim(cursoGravado.getDataFim())
                .duracaoHoras(cursoGravado.getDuracaoHoras())
                .certificadoUrl(cursoGravado.getPathCertificado())
                .miniaturaCertificadoUrl(cursoGravado.getPathMiniaturaCertificado())
                .build();
    }

    public void removerCursoConcluido(final Long id){
        var cursoEntity = adapter.recuperarCursoPorId(id)
                .orElseThrow(() -> new CursoNaoExistenteException());
        certificadoImagemAdapter.solicitarRemocaoCertificado(cursoEntity.getPathCertificado(),
                cursoEntity.getPathMiniaturaCertificado());
        adapter.solicitarQueCursoSejaRemovida(cursoEntity);
    }

    public List<CursoResponse> recuperarTodosCursosConcluidos(){
        var cursos = adapter.recuperarTodosCursos();
        return cursos.stream().map(c -> CursoResponse.builder()
                .id(c.getId())
                .nome(c.getNome())
                .descricao(c.getDescricao())
                .uuid(c.getUuid())
                .escola(c.getEscola().getNome())
                .categoria(CategoriaResponse.builder()
                        .id(c.getCategoria().getId())
                        .nome(c.getCategoria().getNome())
                        .build())
                .dataInicio(c.getDataInicio())
                .dataFim(c.getDataFim())
                .duracaoHoras(c.getDuracaoHoras())
                .certificadoUrl(c.getPathCertificado())
                .miniaturaCertificadoUrl(c.getPathMiniaturaCertificado())
                .timestamp(c.getTimestamp())
                .build()).toList();
    }

}
