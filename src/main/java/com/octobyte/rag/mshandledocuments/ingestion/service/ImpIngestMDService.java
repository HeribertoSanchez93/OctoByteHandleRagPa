package com.octobyte.rag.mshandledocuments.ingestion.service;

import com.octobyte.rag.mshandledocuments.util.Constants;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service("MDImplementation")
public class ImpIngestMDService implements IngestService {

    private final MarkdownDocumentReaderConfig markdownDocumentReaderConfig;

    public ImpIngestMDService(MarkdownDocumentReaderConfig markdownDocumentReaderConfig) {
        this.markdownDocumentReaderConfig = markdownDocumentReaderConfig;
    }

    @Override
    public List<Document> ingest() throws Exception {
        File Folder = new File(Constants.MARK_PATH);
        File[] files = Folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".md"));
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
