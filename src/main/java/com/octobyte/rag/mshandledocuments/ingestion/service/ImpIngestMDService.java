package com.octobyte.rag.mshandledocuments.ingestion.service;

import com.octobyte.rag.mshandledocuments.util.Constants;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpIngestMDService implements IngestService {

    private final MarkdownDocumentReaderConfig markdownDocumentReaderConfig;

    public ImpIngestMDService(MarkdownDocumentReaderConfig markdownDocumentReaderConfig) {
        this.markdownDocumentReaderConfig = markdownDocumentReaderConfig;
    }

    @Override
    public List<Document> ingest() throws Exception {
        Resource markDowns = new FileSystemResource(Constants.MARK_PATH);
        return getResource(markDowns);
    }

    @Override
    public List<Document> ingest(String name) throws Exception {
        Resource markDowns = new FileSystemResource(Constants.MARK_PATH+"/"+name);
        return getResource(markDowns);

    }

    private List<Document> getResource(Resource markDown){

        MarkdownDocumentReader reader = new MarkdownDocumentReader (markDown, markdownDocumentReaderConfig);
        List<Document> documents = reader.read();

        String name = markDown.getFilename()!=null?markDown.getFilename():"Unknown";

        documents.forEach(document -> {
            document.getMetadata().put("source","Markdown");
            document.getMetadata().put("filename",name);
            document.getMetadata().put("identity","Markdown#"+name);
        });

        return documents;
    }
}
