package com.example.bankai.repository;

import com.example.bankai.domain.DocumentChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentChunkRepository extends JpaRepository<DocumentChunk, Long> {

    @Query(value = """ 
             SELECT * FROM document_chunk
                 WHERE embedding <=> CAST(:queryEmbedding AS vector) < :threshold
                 ORDER BY embedding <=> CAST(:queryEmbedding AS vector)
                 LIMIT :limit
            """, nativeQuery = true)
    List<DocumentChunk> findByEmbedding(
            @Param("queryEmbedding") float[] queryEmbedding,
            @Param("threshold") double threshold,
            @Param("limit") int limit
    );
}
