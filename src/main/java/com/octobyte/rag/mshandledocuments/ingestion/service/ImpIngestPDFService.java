package com.octobyte.rag.mshandledocuments.ingestion.service;

import com.octobyte.rag.mshandledocuments.util.Constants;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service("PDFImplementation")
public class ImpIngestPDFService implements IngestService{

    private final PdfDocumentReaderConfig config;

    public ImpIngestPDFService(PdfDocumentReaderConfig config) {
        this.config = config;
    }

    @Override
    public List<Document> ingest() throws Exception {
        File Folder = new File(Constants.PDF_PATH);
        File[] files = Folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        if (files == null || files.length == 0) {
            return List.of();
        }
        List<Document> documents = new ArrayList<>();
        for (File file : files) {
            Resource resource = new FileSystemResource(file);
            documents.addAll(getResource(resource));
        }
        return documents;
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
