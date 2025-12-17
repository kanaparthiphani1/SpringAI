package com.example.structured_output_converter.controllers;

import com.example.structured_output_converter.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {

    private ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> chatBean(@RequestParam String message) {
        CountryCities countryCities =  chatClient.prompt(message).call().entity(CountryCities.class);

        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-list")
    public ResponseEntity<List<String>> chatList(@RequestParam String message) {
        List<String> countryCities =  chatClient.prompt(message).call().entity(new ListOutputConverter());

        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-map")
    public ResponseEntity<Map<String,Object>> chatMap(@RequestParam String message) {
        Map<String,Object> countryCities =  chatClient.prompt(message).call().entity(new MapOutputConverter());

        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-list-bean")
    public ResponseEntity<List<CountryCities>> chatListBean(@RequestParam String message) {
        List<CountryCities> countryCities =  chatClient.prompt(message).call().entity(new ParameterizedTypeReference<List<CountryCities>>() {
        });

        return ResponseEntity.ok(countryCities);
    }
}
