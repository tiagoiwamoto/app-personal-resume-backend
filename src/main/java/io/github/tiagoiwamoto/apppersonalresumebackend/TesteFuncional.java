package io.github.tiagoiwamoto.apppersonalresumebackend;

import io.github.tiagoiwamoto.apppersonalresumebackend.core.usecase.CursoCategoriaUsecase;
import io.github.tiagoiwamoto.apppersonalresumebackend.entrypoint.dto.CursoCategoriaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TesteFuncional {

    private final CursoCategoriaUsecase usecase;

    @EventListener(ApplicationReadyEvent.class)
    void teste(){
        usecase.criarNovaCategoria(CursoCategoriaRequest.builder()
                .nome("Backend")
                .descricacao("Cursos relacionados a desenvolvimento backend com Java, Spring Boot, Node.js, Python e outras tecnologias.")
                .build());
    }

}
