package com.octobyte.rag.mshandledocuments.Ingest;

import com.octobyte.rag.mshandledocuments.ingestion.service.IngestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class IngestServicesDBTest {
    @Autowired
    @Qualifier("DBImplementation")
    private IngestService ingestService;

    @Test
    public void ingestTestDBs() throws Exception {
        ingestService.ingest();
    }

    @Test
    public void ingestTestDB() throws Exception {
        ingestService.ingest("releaseNotes");
    }
}
