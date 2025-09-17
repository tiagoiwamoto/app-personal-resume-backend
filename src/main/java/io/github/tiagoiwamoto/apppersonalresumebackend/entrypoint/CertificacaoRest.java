package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase.CertificacaoUsecase;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CertificacaoRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CertificacaoResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/certificacoes")
@RequiredArgsConstructor
@Slf4j
public class CertificacaoRest {

    private final CertificacaoUsecase usecase;

    @PostMapping
    public ResponseEntity<DataResponse<CertificacaoResponse>> criar(@RequestBody CertificacaoRequest request,
                                                                    @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para criação de uma certificação. body: {}, headers: {}", request, headers);

        var response = usecase.cadastrarCertificacao(request, headers);

        return ResponseEntity
                .created(URI.create("/cursos/categorias/"))
                .body(
                        DataResponse.<CertificacaoResponse>builder()
                                .data(response)
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<DataResponse<CertificacaoResponse>> atualizar(@RequestBody CertificacaoRequest request,
                                                                     @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para atualização de uma certificação. body: {}, headers: {}", request, headers);

        var response = usecase.alterarCertificacao(request, headers);

        return ResponseEntity
                .ok(
                    DataResponse.<CertificacaoResponse>builder()
                            .data(response)
                            .build()
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse<CertificacaoResponse>> recuperarCertificacao(@PathVariable(name = "id") Long id,
                                                                     @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para recuperação de uma certificação. id: {}, headers: {}", id, headers);

        var response = usecase.recuperarCertificacao(id, headers);

        return ResponseEntity
                .ok(
                    DataResponse.<CertificacaoResponse>builder()
                            .data(response)
                            .build()
                );
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<CertificacaoResponse>>> listarCategorias(@RequestHeader Map<String, String> headers) {
        log.info("Recuperar todas as certificações, headers: {}", headers);

        var response = usecase.recuperarTodasCertificacoes();

        return ResponseEntity
                .ok(
                    DataResponse.<List<CertificacaoResponse>>builder()
                            .data(response)
                            .build()
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity removerCategoria(@RequestHeader Map<String, String> headers,
                                           @PathVariable(name = "id") Long id) {
        log.info("Deletar certificação {}, headers: {}", id, headers);

        usecase.removerCertificacao(id);

        return ResponseEntity.accepted().build();
    }

}
