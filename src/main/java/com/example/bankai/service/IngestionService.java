package com.example.bankai.service;

import com.example.bankai.domain.DocumentChunk;
import org.springframework.core.io.Resource;

import java.util.List;

public interface IngestionService {
    Resource[] parse() throws Exception;

    List<DocumentChunk> embed(Resource[] resources) throws Exception;

    int store(List<DocumentChunk> documentChunks);
}
