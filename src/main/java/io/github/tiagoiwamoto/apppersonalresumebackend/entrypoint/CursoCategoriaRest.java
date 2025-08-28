package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase.CursoCategoriaUsecase;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoCategoriaRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoCategoriaResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.DataResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(path = "/cursos/categorias")
@RequiredArgsConstructor
@Slf4j
public class CursoCategoriaRest {

    private final CursoCategoriaUsecase usecase;

    @PostMapping
    public ResponseEntity<DataResponse<CursoCategoriaResponse>> criar(@RequestBody CursoCategoriaRequest request,
                                                                       @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para criação de uma categoria de curso. body: {}, headers: {}", request, headers);

        var response = usecase.criarNovaCategoria(request);

        return ResponseEntity
                .created(URI.create("/cursos/categorias/"))
                .body(
                        DataResponse.<CursoCategoriaResponse>builder()
                                .data(response)
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<DataResponse<CursoCategoriaResponse>> atualizar(@RequestBody CursoCategoriaRequest request,
                                                                       @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para atualização de uma categoria de curso. body: {}, headers: {}", request, headers);

        var response = usecase.editarCategoria(request);

        return ResponseEntity
                .ok(
                    DataResponse.<CursoCategoriaResponse>builder()
                            .data(response)
                            .build()
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse<CursoCategoriaResponse>> atualizar(@PathVariable(name = "id") Long id,
                                                                       @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para recuperação de uma categoria de curso. id: {}, headers: {}", id, headers);

        var response = usecase.recuperarCategoriaPeloId(id);

        return ResponseEntity
                .ok(
                    DataResponse.<CursoCategoriaResponse>builder()
                            .data(response)
                            .build()
                );
    }

}
