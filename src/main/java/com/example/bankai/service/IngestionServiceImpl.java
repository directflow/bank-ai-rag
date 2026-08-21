package com.example.bankai.service;

import com.example.bankai.domain.DocumentChunk;
import com.example.bankai.repository.DocumentChunkRepository;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestionServiceImpl implements IngestionService {

    private static final int MAX_TOKENS = 50;
    private static final int OVERLAP_TOKENS = 5;

    private final DocumentSplitter splitter =
            DocumentSplitters.recursive(
                    MAX_TOKENS,
                    OVERLAP_TOKENS
            );

    private final EmbeddingModel embeddingModel;

    private final DocumentChunkRepository documentChunkRepository;

    @Override
    public Resource[] parse() throws Exception {
        return new PathMatchingResourcePatternResolver().getResources("classpath:/documents/*.txt");
    }

    @Override
    public List<DocumentChunk> embed(Resource[] resources) throws Exception {
        List<DocumentChunk> documentChunks = new ArrayList<>();

        for (Resource resource : resources) {
            String text = resource.getContentAsString(StandardCharsets.UTF_8);
            List<TextSegment> textSegments = split(Document.from(text));
            List<Embedding> embeddings = embeddingModel.embedAll(textSegments).content();
            for (int i = 0; i < textSegments.size(); i++) {
                DocumentChunk documentChunk = new DocumentChunk(textSegments.get(i).text(), resource.getFilename());
                documentChunk.setEmbedding(embeddings.get(i).vector());
                documentChunks.add(documentChunk);
            }
        }

        return documentChunks;
    }

    @Override
    @Transactional
    public int store(List<DocumentChunk> documentChunks) {
        documentChunkRepository.saveAll(documentChunks);
        return documentChunks.size();
    }

    private List<TextSegment> split(Document document) {
        return splitter.split(document);
    }
}
