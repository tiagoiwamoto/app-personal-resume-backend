package io.github.tiagoiwamoto.apppersonalresumebackend.adapter;

import io.github.tiagoiwamoto.apppersonalresumebackend.adapter.dto.CertificadoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificadoImagemAdapter {

    private final MinioAdapter minioAdapter;

    public Mono<CertificadoResponse> solicitarArmazenamentoCertificado(final FilePart certificado, final String path) {
        String nomeOriginal = certificado.filename();
        String nomeUuid = UUID.randomUUID().toString();

        File arquivoCertificado = new File(System.getProperty("user.dir")
                .concat(File.separator)
                .concat("files")
                .concat(File.separator).concat(path).concat(File.separator).concat(nomeUuid).concat(".png"));

        var pathCertificado = "files".concat(File.separator)
                .concat(path)
                .concat(File.separator)
                .concat(nomeUuid);

        File arquivoMiniaturaCertificado = new File(System.getProperty("user.dir")
                .concat(File.separator)
                .concat("files")
                .concat(File.separator).concat(path).concat(File.separator).concat(nomeUuid).concat("_thumb").concat(".png"));

        File pastaCertificado = arquivoCertificado.getParentFile();

        if (!pastaCertificado.exists()) {
            pastaCertificado.mkdirs();
        }

        return certificado.transferTo(arquivoCertificado)
            .then(Mono.fromCallable(() -> {
                BufferedImage imagem = ImageIO.read(arquivoCertificado);
                if (imagem == null) {
                    throw new IllegalArgumentException("Arquivo não é uma imagem válida.");
                }

                int largura = 200;
                int altura = (int) ((double) imagem.getHeight() / imagem.getWidth() * largura);
                Image miniatura = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                BufferedImage miniaturaBuffered = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = miniaturaBuffered.createGraphics();
                g2d.drawImage(miniatura, 0, 0, null);
                g2d.dispose();

                ImageIO.write(miniaturaBuffered, "png", arquivoMiniaturaCertificado);

//                minioAdapter.enviarArquivo(arquivoCertificado, arquivoMiniaturaCertificado);

                return CertificadoResponse.builder()
                        .nomeArquivoOriginal(nomeOriginal)
                        .caminhoCertificado(pathCertificado.concat(".png"))
                        .caminhoMiniaturaCertificado(pathCertificado.concat("_thumb").concat(".png"))
                        .tamanhoCertificadoBytes(arquivoCertificado.length())
                        .tamanhoMiniaturaCertificadoBytes(arquivoMiniaturaCertificado.length())
                        .build();
            }))
            .onErrorMap(e -> {
                log.error("Erro ao processar imagem do certificado", e);
                return new RuntimeException("Erro ao processar imagem do certificado", e);
            });
    }

    public Mono<CertificadoResponse> solicitarAlteracaoCertificado(final FilePart certificado, final String path,
                                                        final String pathCertificadoExistente,
                                                        final String pathMiniaturaCertificadoExistente) {
        // Remove os arquivos existentes
        File certificadoExistente = new File(pathCertificadoExistente);
        File miniaturaExistente = new File(pathMiniaturaCertificadoExistente);
        if (certificadoExistente.exists()) {
            certificadoExistente.delete();
        }
        if (miniaturaExistente.exists()) {
            miniaturaExistente.delete();
        }
        // Armazena o novo certificado
        return solicitarArmazenamentoCertificado(certificado, path);
    }

    public void solicitarRemocaoCertificado(final String pathCertificado, final String pathMiniaturaCertificado) {
        File certificado = new File(pathCertificado);
        File miniatura = new File(pathMiniaturaCertificado);
        if (certificado.exists()) {
            certificado.delete();
        }
        if (miniatura.exists()) {
            miniatura.delete();
        }
//        minioAdapter.removerArquivo(pathCertificado, pathMiniaturaCertificado);
    }

}
