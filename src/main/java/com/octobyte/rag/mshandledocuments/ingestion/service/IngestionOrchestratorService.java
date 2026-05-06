package com.octobyte.rag.mshandledocuments.ingestion.service;

import com.octobyte.rag.mshandledocuments.lifeCycle.model.KnokledgeRequest;
import com.octobyte.rag.mshandledocuments.lifeCycle.model.SourceType;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngestionOrchestratorService {

    private final Map<String, IngestService> ingestionStategyMap;

    public IngestionOrchestratorService( Map<String, IngestService> ingestionStategyMap){
        this.ingestionStategyMap = ingestionStategyMap;
    }

    public List<Document> ingest(KnokledgeRequest request) throws Exception {
        SourceType sourceType = request.sourceType();
        String choose = switch (sourceType.toString().toLowerCase()) {
            case "pdf" -> "PDFImplementation";
            case "wiki" -> "MDImplementation";
            case "db" -> "DBImplementation";
            default -> sourceType.toString().toUpperCase();
        };
        IngestService ingestionStategy = ingestionStategyMap.get(choose);
        if (ingestionStategy == null) {
            throw new IllegalArgumentException("No ingest strategy found for source type: " + sourceType);
        }
        return ingestionStategy.ingest(request.name());
    }

    public List<Document> ingestAll() throws Exception {
        List<Document> docs = new ArrayList<>();
        for (IngestService ingestionStategy : ingestionStategyMap.values()) {
            docs.addAll(ingestionStategy.ingest());
        }
        return docs;
    }
}
