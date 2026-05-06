package com.octobyte.rag.mshandledocuments.ingestion.service;

import org.springframework.ai.document.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service("DBImplementation")
public class ImpIngestDataBaseService implements IngestService {

    private final JdbcTemplate jdbcTemplate;

    public ImpIngestDataBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Document> ingest() throws Exception {
        // Implement your database ingestion logic here
        List<Document> docs = new ArrayList<>();
        docs.addAll( ingestFaqs());
        docs.addAll(ingestReleaseNotes());
        docs.addAll(ingestAnnouncements());

        return docs;
    }

    @Override
    public List<Document> ingest(String name) throws Exception {
        if("faq".equalsIgnoreCase(name)){
            return ingestFaqs();
        }
        if("releaseNotes".equalsIgnoreCase(name)){
            return ingestReleaseNotes();
        }
        if("announcements".equalsIgnoreCase(name)){
            return ingestAnnouncements();
        }
        return Collections.emptyList();
    }


    private List<Document> ingestFaqs() throws Exception {
        List<Map<String, Object>> faqs = jdbcTemplate.
                queryForList("select id,question,answer,department,visibility from faqs");

        List<Document> docs = new ArrayList<>();
        for (Map<String, Object> faq : faqs) {


            String content = "Question: " + faq.get("question") + "\n" +
                    "Answer: " + faq.get("answer");




            docs.add(new Document(
                    content,
                    Map.of("table","faq",
                            "identity","DB#faq",
                            "id",faq.get("id"),
                            "department",faq.get("department"),
                            "visibility",faq.get("visibility"),
                            "source","DB")
            ));
        }
        return docs;
    }

    private List<Document> ingestReleaseNotes() throws Exception {
        List<Map<String, Object>> releaseNotes = jdbcTemplate.
                queryForList("select id,version,summary,details,release_date from release_notes");

        List<Document> docs = new ArrayList<>();
        for (Map<String, Object> releaseNote : releaseNotes) {


            String content = "Version: " + releaseNote.get("version") + "\n" +
                    "Summary: " + releaseNote.get("summary") + "\n" +
                    "Details: " + releaseNote.get("details");
            docs.add(new Document(
                    content,
                    Map.of("table","release_notes",
                            "identity","DB#release_notes",
                            "id",releaseNote.get("id"),
                            "version",releaseNote.get("version"),
                            "releaseDate",releaseNote.get("release_date"),
                            "source","DB")
            ));
        }

        return docs;
    }
    private List<Document> ingestAnnouncements() throws Exception {

        List<Document> docs = new ArrayList<>();
        List<Map<String, Object>> announcements = jdbcTemplate.
                queryForList("select id,subject,body,category," +
                        "effective_from,effective_to,source_type from announcements");

        for (Map<String, Object> announcement : announcements) {


            String content = "Subject: " + announcement.get("subject") + "\n" +
                    "Body: " + announcement.get("body");

            docs.add(new Document(
                    content,
                    Map.of("table","announcements",
                            "identity","DB#announcements",
                            "id",announcement.get("id"),
                            "category",announcement.get("category"),
                            "effectiveFrom",announcement.get("effective_from"),
                            "effectiveTo",announcement.get("effective_to")!=
                                    null?announcement.get("effective_to").toString():"null",
                            "sourceType",announcement.get("source_type"),
                            "source","DB")
            ));
        }
        return docs;
    }
}
