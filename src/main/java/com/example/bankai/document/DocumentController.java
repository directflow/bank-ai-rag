package com.example.bankai.document;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentIngestionService service;

    public DocumentController(DocumentIngestionService service) {
        this.service = service;
    }

    @PostMapping("/ingest")
    public Map<String, Object> ingest() throws Exception {
        return Map.of("chunks", service.ingest());
    }
}
