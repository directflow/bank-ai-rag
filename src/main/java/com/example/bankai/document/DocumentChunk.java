package com.example.bankai.document;

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
    @Array(length = 768)
    @Column(columnDefinition = "vector(768)")
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
        if (embedding != null && embedding.length != 768) {
            throw new IllegalArgumentException("Embedding must contain exactly 768 dimensions.");
        }
        this.embedding = embedding == null ? null : Arrays.copyOf(embedding, embedding.length);
    }
}
