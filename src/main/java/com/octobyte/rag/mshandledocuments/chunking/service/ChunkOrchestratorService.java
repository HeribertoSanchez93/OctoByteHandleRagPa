package com.octobyte.rag.mshandledocuments.chunking.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Service("ChunkingImplementation")
public class ChunkOrchestratorService {

    private static final int chunkSize = 400;
    private static final int minChunkSizeChars = 200;

    public List<Document> chunk(List<Document> documents) {
        TokenTextSplitter splitter = TokenTextSplitter
                .builder()
                .withChunkSize(chunkSize)
                .withMinChunkSizeChars(minChunkSizeChars)
                .build();

        List<Document> chunkedDocuments;
        Semaphore semaphore = new Semaphore(5);
        try( ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor() ){
            List<Future<Document>> chunkedDocs = splitter.apply(documents).stream()
                    .map(doc -> executor.submit(() -> {
                        semaphore.acquire();
                        try {
                            return assingChunkMetadata(doc);
                        } finally {
                            semaphore.release();
                        }
                    }))
                    .toList();

            chunkedDocuments = chunkedDocs.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
        }
        return chunkedDocuments;
    }

    private Document assingChunkMetadata(Document document) {

        Map<String,Object> metadata = document.getMetadata();
        metadata.put("ChunkSize", chunkSize);
        metadata.put("MinChunkSizeChars", minChunkSizeChars);
        switch (document.getMetadata().get("source").toString()) {
            case "PDF" -> {

                metadata.put("chunkType", "PDF_Section");
                return new Document(
                        document.getText()!=null?document.getText().trim():"",
                        metadata
                );

            }
            case "DB" -> {
                metadata.put("chunkType", "DB_Section");
                return new Document(
                        document.getId(),
                        document.getText(),
                        metadata
                );
            }
            case "MD" -> {
                metadata.put("chunkType", "MD_Section");
                return new Document(
                        document.getId(),
                        document.getText(),
                        metadata
                );
            }
            default -> {
                return document;
            }
        }
    }
}
