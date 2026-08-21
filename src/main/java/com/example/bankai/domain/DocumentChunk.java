package com.example.bankai.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Arrays;

@Entity
@Table(name = "document_chunk")
public class DocumentChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private String source;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 1024)
    @Column(columnDefinition = "vector(1024)")
    private float[] embedding;

    protected DocumentChunk() {}

    public DocumentChunk(String content, String source) {
        this.content = content;
        this.source = source;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public String getSource() { return source; }
    public float[] getEmbedding() {
        return embedding == null ? null : Arrays.copyOf(embedding, embedding.length);
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding == null ? null : Arrays.copyOf(embedding, embedding.length);
    }
}
