package io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase.CursoUsecase;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoRequest;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoResponse;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<DataResponse<CursoResponse>> recuperarCurso(CursoRequest request,
                                                                      @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para atualização de uma curso de curso. body: {}, headers: {}", request, headers);

        var response = usecase.alterarCursoConcluido(request, headers);

        return ResponseEntity
                .ok(
                    DataResponse.<CursoResponse>builder()
                            .data(CursoResponse.builder().build())
                            .build()
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse<CursoResponse>> recuperarCurso(@PathVariable(name = "id") Long id,
                                                                      @RequestHeader Map<String, String> headers) {
        log.info("Informações recebidas para recuperação de uma curso de curso. id: {}, headers: {}", id, headers);


        return ResponseEntity
                .ok(
                    DataResponse.<CursoResponse>builder()
                            .data(CursoResponse.builder().build())
                            .build()
                );
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<CursoResponse>>> listarCursos(@RequestHeader Map<String, String> headers) {
        log.info("Recuperar todas as cursos, headers: {}", headers);

        var registros = usecase.recuperarTodosCursosConcluidos();
        return ResponseEntity
                .ok(
                    DataResponse.<List<CursoResponse>>builder()
                            .data(registros)
                            .build()
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity removerCategoria(@RequestHeader Map<String, String> headers,
                                           @PathVariable(name = "id") Long id) {
        log.info("Deletar curso {}, headers: {}", id, headers);

        usecase.removerCursoConcluido(id);

        return ResponseEntity.accepted().build();
    }

}
