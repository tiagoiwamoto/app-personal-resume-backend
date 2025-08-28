package io.github.tiagoiwamoto.apppersonalresumebackend.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_curso_categoria")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoCategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uuid;
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;
}
