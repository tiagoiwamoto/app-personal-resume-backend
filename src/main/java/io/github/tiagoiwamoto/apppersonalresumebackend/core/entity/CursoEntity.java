package io.github.tiagoiwamoto.apppersonalresumebackend.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_curso")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uuid;
    private String nome;
    private String nomeArquivoOriginal;
    private String descricao;
    private Long tamanhoArquivoBytes;
    @Enumerated(EnumType.STRING)
    private EscolaEnum escola;
    private Integer duracaoHoras;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalDateTime timestamp;
    private String pathCertificado;
    private String pathMiniaturaCertificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;
}
