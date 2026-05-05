package com.octobyte.rag.mshandledocuments.ingestion.model;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.content.Media;
import org.springframework.ai.document.Document;

import java.util.Map;

public class IngestedDocument extends Document {
    private String source;

    public IngestedDocument(@Nullable String content,String Source) {
        super(content);
        this.source = Source;
    }

    public IngestedDocument(@Nullable String text, Map<String, Object> metadata,String Source) {
        super(text, metadata);
        this.source = Source;
    }

    public IngestedDocument(String id, @Nullable String text, Map<String, Object> metadata,String Source) {
        super(id, text, metadata);
        this.source = Source;
    }

    public IngestedDocument(@Nullable Media media, Map<String, Object> metadata,String Source) {
        super(media, metadata);
        this.source = Source;
    }

    public IngestedDocument(String id, @Nullable Media media, Map<String, Object> metadata,String Source) {
        super(id, media, metadata);
        this.source = Source;
    }
}
