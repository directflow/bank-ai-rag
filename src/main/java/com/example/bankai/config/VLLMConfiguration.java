package com.example.bankai.config;

import dev.langchain4j.http.client.jdk.JdkHttpClientBuilder;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class VLLMConfiguration {

    @Value("${bank-ai.vllm.embedding.url}")
    private String vllmEmbeddingURL;

    @Value("${bank-ai.vllm.embedding.model}")
    private String vllmEmbeddingModel;

    @Value("${bank-ai.vllm.chat.url}")
    private String vllmChatURL;

    @Value("${bank-ai.vllm.chat.model}")
    private String vllmChatModel;

    @Bean
    public EmbeddingModel embeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .httpClientBuilder(jdkHttpClientBuilder())
                .baseUrl(vllmEmbeddingURL)
                .modelName(vllmEmbeddingModel)
                .build();
    }

    @Bean
    public ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .httpClientBuilder(jdkHttpClientBuilder())
                .baseUrl(vllmChatURL)
                .modelName(vllmChatModel)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    private JdkHttpClientBuilder jdkHttpClientBuilder() {
        return new JdkHttpClientBuilder()
                .httpClientBuilder(
                        HttpClient.newBuilder()
                                .version(HttpClient.Version.HTTP_1_1)
                );
    }
}
