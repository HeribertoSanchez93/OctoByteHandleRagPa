package com.octobyte.rag.mshandledocuments.config;

import com.knuddels.jtokkit.api.EncodingType;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgDistanceType.COSINE_DISTANCE;
import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType.HNSW;

@Configuration
public class StoreConfig {

    @Bean
    public BatchingStrategy customTokenCountBatchingStrategy() {
        return new TokenCountBatchingStrategy(
                EncodingType.CL100K_BASE,  // Specify the encoding type
                8000,                      // Set the maximum input token count
                0.1                        // Set the reserve percentage
        );
    }

//    @Bean
//    public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel,
//                                   BatchingStrategy customTokenCountBatchingStrategy) {
//
//        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
//                .dimensions(1536)
//                .distanceType(COSINE_DISTANCE)
//                .indexType(HNSW)
//                .initializeSchema(true)
//                .schemaName("public")
//                .vectorTableName("vector_store")
//                .batchingStrategy(customTokenCountBatchingStrategy)
//                .maxDocumentBatchSize(10000)
//
//                .build();
//    }
}
