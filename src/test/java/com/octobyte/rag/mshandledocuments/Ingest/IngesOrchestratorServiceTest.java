package com.octobyte.rag.mshandledocuments.Ingest;

import com.octobyte.rag.mshandledocuments.ingestion.service.IngestionOrchestratorService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class IngesOrchestratorServiceTest {

    @Autowired
    IngestionOrchestratorService ingestionOrchestratorService;
    @Test
    public void ingest() throws Exception {

        List<Document> docs= ingestionOrchestratorService.ingestAll();

        System.out.println("Total docs: " + docs.size());

        docs.forEach(doc->{
            System.out.println("SOURCE =" +doc.getText());;
            System.out.println("------");
        });
    }
}
