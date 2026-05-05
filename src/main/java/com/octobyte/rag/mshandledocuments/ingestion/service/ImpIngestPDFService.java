package com.octobyte.rag.mshandledocuments.ingestion.service;

import com.octobyte.rag.mshandledocuments.util.Constants;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpIngestPDFService implements IngestService{

    private final PdfDocumentReaderConfig config;

    public ImpIngestPDFService(PdfDocumentReaderConfig config) {
        this.config = config;
    }

    @Override
    public List<Document> ingest() throws Exception {
        Resource PDFs = new FileSystemResource(Constants.PDF_PATH);
        return getResource(PDFs);
    }

    @Override
    public List<Document> ingest(String name) throws Exception {

        Resource PDFs = new FileSystemResource(Constants.PDF_PATH+"/"+name);
        return getResource(PDFs);
    }

    private List<Document> getResource(Resource PDFs){

        PagePdfDocumentReader reader = new PagePdfDocumentReader(PDFs, config);
        List<Document> documents = reader.read();

        String name = PDFs.getFilename()!=null?PDFs.getFilename():"Unknown";

        documents.forEach(document -> {
            document.getMetadata().put("source","PDF");
            document.getMetadata().put("filename",name);
            document.getMetadata().put("identity","PDF#"+name);
        });

        return documents;
    }

}
