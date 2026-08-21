package com.example.bankai.service;

public interface ChatService {
    float[] embedding(String question);

    String prompt(String question, float[] embedding);

    String answer(String prompt);
}
