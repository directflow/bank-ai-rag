package com.example.bankai.embedding;

import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmbeddingService {
    private final EmbeddingModel embeddingModel;
    private final int dimensions;

    public EmbeddingService(
            EmbeddingModel embeddingModel,
            @Value("${bank-ai.embedding.dimensions}") int dimensions
    ) {
        this.embeddingModel = embeddingModel;
        this.dimensions = dimensions;
    }

    public float[] embed(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text to embed must not be blank.");
        }

        float[] embedding = embeddingModel.embed(text).content().vector();
        if (embedding.length != dimensions) {
            throw new IllegalStateException(
                    "Embedding has " + embedding.length + " dimensions; expected " + dimensions + ".");
        }
        return embedding;
    }
}
