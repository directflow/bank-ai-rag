package com.example.bankai.controller;

import com.example.bankai.facade.IngestionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingestion")
public class IngestionController {

    private final IngestionFacade ingestionFacade;

    @PostMapping
    public Map<String, Object> ingest() throws Exception {
        return Map.of("chunks", ingestionFacade.ingest());
    }
}
