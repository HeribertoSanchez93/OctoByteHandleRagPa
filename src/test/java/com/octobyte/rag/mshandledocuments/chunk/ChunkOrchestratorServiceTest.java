package com.octobyte.rag.mshandledocuments.chunk;

import com.octobyte.rag.mshandledocuments.chunking.service.ChunkOrchestratorService;
import com.octobyte.rag.mshandledocuments.ingestion.service.IngestionOrchestratorService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ChunkOrchestratorServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkOrchestratorServiceTest.class);


    @Autowired
    private IngestionOrchestratorService  ingestionOrchestratorService;

    @Autowired
    private ChunkOrchestratorService chunkOrchestratorService;

    @Test
    public void chunkOrchestratorServiceTest() throws Exception {
        List<Document> docs=chunkOrchestratorService.chunk(ingestionOrchestratorService.ingestAll());
        for (Document doc : docs) {
            LOGGER.info("++++++++++++++++++++++++++++++++++++++");
            LOGGER.info("Source: {}", doc.getText());
            LOGGER.info("Chunks: {}", doc.getMetadata());
        }
    }
}
