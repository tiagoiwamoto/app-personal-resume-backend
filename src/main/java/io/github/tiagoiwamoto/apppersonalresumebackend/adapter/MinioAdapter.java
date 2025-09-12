package io.github.tiagoiwamoto.apppersonalresumebackend.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioAdapter {

    private final S3Client s3Client;

    private final static String BUCKET = "tiagoiwamoto";

    public void enviarArquivo(final File certificado, final File miniaturaCertificado) {

        var futureCertificado = CompletableFuture.supplyAsync(() ->
                s3Client.putObject(b -> b.bucket(BUCKET).key("certificados/".concat(certificado.getName())),
                        RequestBody.fromFile(certificado))
        );

        var futureMiniaturaCertificado = CompletableFuture.supplyAsync(() ->
                s3Client.putObject(b -> b.bucket(BUCKET).key("miniaturas-certificados/".concat(miniaturaCertificado.getName())),
                        RequestBody.fromFile(miniaturaCertificado))
        );

        CompletableFuture.allOf(futureCertificado, futureMiniaturaCertificado);
        log.info("Upload dos arquivos {} e {} concluído com sucesso.",
                futureCertificado.resultNow(), futureMiniaturaCertificado.resultNow());

    }

    public void removerArquivo(final String pathCertificado, final String pathMiniaturaCertificado) {
        var futureCertificado = CompletableFuture.supplyAsync(() ->
                s3Client.deleteObject(b -> b.bucket(BUCKET).key(pathCertificado))
        );

        var futureMiniaturaCertificado = CompletableFuture.supplyAsync(() ->
                s3Client.deleteObject(b -> b.bucket(BUCKET).key(pathMiniaturaCertificado))
        );

        CompletableFuture.allOf(futureCertificado, futureMiniaturaCertificado);
        log.info("Remoção dos arquivos {} e {} concluído com sucesso.",
                futureCertificado.resultNow(), futureMiniaturaCertificado.resultNow());
    }

}
