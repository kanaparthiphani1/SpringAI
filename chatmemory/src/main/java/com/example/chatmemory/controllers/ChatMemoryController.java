package com.example.chatmemory.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

    private ChatClient chatClient;

    public ChatMemoryController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message, @RequestHeader("user") String user) {
        return chatClient.prompt().advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, user)).user(message).call().content();
    }
}
