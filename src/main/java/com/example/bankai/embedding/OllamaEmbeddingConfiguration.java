package com.example.bankai.embedding;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OllamaEmbeddingConfiguration {

    @Bean
    EmbeddingModel embeddingModel(
            @Value("${bank-ai.ollama.base-url}") String baseUrl,
            @Value("${bank-ai.ollama.embedding-model}") String modelName
    ) {
        return OllamaEmbeddingModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .build();
    }

    @Bean
    public ChatModel chatModel() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("gemma4:26b")
                .timeout(Duration.ofMinutes(30))
                .numCtx(32768)
                .temperature(0.3)
                .build();
    }
}
