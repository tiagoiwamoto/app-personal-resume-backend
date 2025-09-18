package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase.PerfilUsecase;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.DataResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.PerfilRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.PerfilResponse;
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
@RequestMapping(path = "/perfis")
@RequiredArgsConstructor
@Slf4j
public class PerfilRest {

    private final PerfilUsecase usecase;

    @PostMapping
    public ResponseEntity<DataResponse<PerfilResponse>> criar(@RequestBody PerfilRequest request,
                                                              @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para criação de um perfil. body: {}, headers: {}", request, headers);

        var response = usecase.criarNovoPerfil(request);

        return ResponseEntity
                .created(URI.create("/perfis/"))
                .body(
                        DataResponse.<PerfilResponse>builder()
                                .data(response)
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<DataResponse<PerfilResponse>> atualizar(@RequestBody PerfilRequest request,
                                                                     @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para atualização de um perfil. body: {}, headers: {}", request, headers);

        var response = usecase.editarPerfil(request);

        return ResponseEntity
                .ok(
                    DataResponse.<PerfilResponse>builder()
                            .data(response)
                            .build()
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse<PerfilResponse>> atualizar(@PathVariable(name = "id") Long id,
                                                                     @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para recuperação de um perfil. id: {}, headers: {}", id, headers);

        var response = usecase.recuperarPerfilPeloId(id);

        return ResponseEntity
                .ok(
                    DataResponse.<PerfilResponse>builder()
                            .data(response)
                            .build()
                );
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<PerfilResponse>>> listarPerfis(@RequestHeader Map<String, String> headers) {
        log.info("Recuperar todas as perfis, headers: {}", headers);

        var response = usecase.recuperarTodosPerfis();

        return ResponseEntity
                .ok(
                    DataResponse.<List<PerfilResponse>>builder()
                            .data(response)
                            .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity removerPerfil(@RequestHeader Map<String, String> headers,
                                           @PathVariable(name = "id") Long id) {
        log.info("Deletar perfil {}, headers: {}", id, headers);

        usecase.deletarPerfil(id);

        return ResponseEntity.accepted().build();
    }

}
