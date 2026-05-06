package com.octobyte.rag.mshandledocuments.Ingest;

import com.octobyte.rag.mshandledocuments.ingestion.service.IngestService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class IngestServicePDFTest {

    @Autowired
    @Qualifier("PDFImplementation")
    IngestService ingestService;


    @Test
    public void ingestTestPDF() throws Exception {
        ingestService.ingest();
    }
}
