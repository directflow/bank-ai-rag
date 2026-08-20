package com.example.bankai.chat;

import com.example.bankai.document.DocumentChunk;
import com.example.bankai.document.DocumentChunkRepository;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final DocumentChunkRepository documentChunkRepository;

    private final EmbeddingModel embeddingModel;

    private final ChatModel chatModel;

    public String answer(String question) {
        float[] embedding = embeddingModel.embed(question).content().vector();
        List<DocumentChunk> documentChunks = documentChunkRepository.findByEmbedding(embedding, 0.5, 5);

        String context = documentChunks.stream()
                .map(DocumentChunk::getContent)
                .collect(Collectors.joining("\n\n---\n\n"));

        String prompt = """
                Answer the question using the context below if relevant.
                If the context doesn't contain the answer, you may use your own knowledge,
                but clearly state: "This answer is not based on the provided documents."
                
                Context:
                %s
                
                Question: %s
                """.formatted(context, question);

        return chatModel.chat(prompt);
    }
}
