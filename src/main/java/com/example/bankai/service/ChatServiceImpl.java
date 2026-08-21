package com.example.bankai.service;

import com.example.bankai.domain.DocumentChunk;
import com.example.bankai.repository.DocumentChunkRepository;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final DocumentChunkRepository documentChunkRepository;

    private final EmbeddingModel embeddingModel;

    private final ChatModel chatModel;

    @Override
    public float[] embedding(String question) {
        return embeddingModel.embed(question).content().vector();
    }

    @Override
    @Transactional(readOnly = true)
    public String prompt(String question, float[] embedding) {
        List<DocumentChunk> documentChunks = documentChunkRepository.findByEmbedding(embedding, 0.5, 5);

        String context = documentChunks.stream()
                .map(DocumentChunk::getContent)
                .collect(Collectors.joining("\n\n---\n\n"));

        return """
                Answer the question using the context below if relevant.
                If the context doesn't contain the answer, you may use your own knowledge,
                but clearly state: "This answer is not based on the provided documents."
                
                Context:
                %s
                
                Question: %s
                """.formatted(context, question);
    }

    @Override
    public String answer(String prompt) {
        return chatModel.chat(prompt);
    }
}
