package com.example.bankai.model;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(@NotBlank String question) {
}
