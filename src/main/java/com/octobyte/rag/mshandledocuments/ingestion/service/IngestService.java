package com.octobyte.rag.mshandledocuments.ingestion.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface IngestService {
    List<Document> ingest()  throws Exception;
    List<Document> ingest(String name)  throws Exception;
}
