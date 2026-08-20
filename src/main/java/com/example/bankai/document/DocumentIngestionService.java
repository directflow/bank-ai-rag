package com.example.bankai.document;

import com.example.bankai.embedding.EmbeddingService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class DocumentIngestionService {
    private final DocumentChunkRepository repository;
    private final EmbeddingService embeddingService;

    public DocumentIngestionService(
            DocumentChunkRepository repository,
            EmbeddingService embeddingService
    ) {
        this.repository = repository;
        this.embeddingService = embeddingService;
    }

    public int ingest() throws Exception {
        var resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:/documents/*.txt");
        int count = 0;

        for (Resource resource : resources) {
            String text = resource.getContentAsString(StandardCharsets.UTF_8);
            for (String chunk : chunk(text, 500)) {
                DocumentChunk documentChunk = new DocumentChunk(chunk, resource.getFilename());
                documentChunk.setEmbedding(embeddingService.embed(chunk));
                repository.save(documentChunk);
                count++;
            }
        }
        return count;
    }

    private java.util.List<String> chunk(String text, int maxChars) {
        var result = new java.util.ArrayList<String>();
        String normalized = text.replaceAll("\\s+", " ").trim();

        for (int start = 0; start < normalized.length(); start += maxChars) {
            result.add(normalized.substring(start, Math.min(start + maxChars, normalized.length())));
        }
        return result;
    }
}
