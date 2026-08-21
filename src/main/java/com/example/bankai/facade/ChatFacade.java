package com.example.bankai.facade;

import com.example.bankai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatFacade {

    private final ChatService chatService;

    public String answer(String question) {
        float[] embedding = chatService.embedding(question);
        String prompt = chatService.prompt(question, embedding);
        return chatService.answer(prompt);
    }
}
