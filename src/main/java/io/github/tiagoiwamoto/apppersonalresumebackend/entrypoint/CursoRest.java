package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase.CursoUsecase;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CategoriaRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.DataResponse;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/cursos")
@RequiredArgsConstructor
@Slf4j
public class CursoRest {

    private final CursoUsecase usecase;

    @PostMapping
    public ResponseEntity<DataResponse<CursoResponse>> criar(CursoRequest request,
                                                             @RequestHeader Map<String, String> headers) {

        log.info("Informações recebidas para cadastro de curso. body: {}, headers: {}", request, headers);

        var response = usecase.cadastrarCursoConcluido(request, headers);

        return ResponseEntity
                .created(URI.create("/cursos/"))
                .body(
                        DataResponse.<CursoResponse>builder()
                                .data(response)
                                .build()
                );
    }

    @PutMapping
    public ResponseEntity<DataResponse<CursoResponse>> atualizar(@RequestBody CategoriaRequest request,
                                                                       @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para atualização de uma categoria de curso. body: {}, headers: {}", request, headers);


        return ResponseEntity
                .ok(
                    DataResponse.<CursoResponse>builder()
                            .data(CursoResponse.builder().build())
                            .build()
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse<CursoResponse>> atualizar(@PathVariable(name = "id") Long id,
                                                                       @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para recuperação de uma categoria de curso. id: {}, headers: {}", id, headers);


        return ResponseEntity
                .ok(
                    DataResponse.<CursoResponse>builder()
                            .data(CursoResponse.builder().build())
                            .build()
                );
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<CursoResponse>>> listarCategorias(@RequestHeader Map<String, String> headers) {
        log.info("Recuperar todas as categorias, headers: {}", headers);


        return ResponseEntity
                .ok(
                    DataResponse.<List<CursoResponse>>builder()
                            .data(List.of())
                            .build()
                );
    }

}
