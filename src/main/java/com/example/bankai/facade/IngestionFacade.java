package com.example.bankai.facade;

import com.example.bankai.domain.DocumentChunk;
import com.example.bankai.service.IngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IngestionFacade {

    private final IngestionService ingestionService;

    public int ingest() throws Exception {
        Resource[] resources = ingestionService.parse();
        List<DocumentChunk> documentChunks = ingestionService.embed(resources);
        return ingestionService.store(documentChunks);
    }
}
