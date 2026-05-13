package com.octobyte.rag.mshandledocuments.ragCrud;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChunkVectorStoreService {

//
//    private final VectorStore vectorStore;
//
//    public ChunkVectorStoreService(  VectorStore vectorStore) {
//        this.vectorStore = vectorStore;
//    }
//
//    public void store(List<Document> chunks) {
//        vectorStore.add(chunks);
//    }
//
//    public void deleteAll() {
//        FilterExpressionBuilder filterExpressionBuilder = new FilterExpressionBuilder();
//        Filter.Expression filter = filterExpressionBuilder.gte("chunkIndex", -1).build();
//        SearchRequest searchRequest = SearchRequest.builder().query("")
//                .filterExpression(filter)
//                .topK(10)
//                .build();
//        while(!vectorStore.similaritySearch(searchRequest).isEmpty()){
//            vectorStore.delete(filter);
//        }
//    }
//
//    public void deleteByIdentity(String identity) {
//        FilterExpressionBuilder filterExpressionBuilder = new FilterExpressionBuilder();
//        vectorStore.delete(filterExpressionBuilder.eq("identity", identity).build());
//    }
}
