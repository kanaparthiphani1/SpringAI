package com.example.rag.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;


@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final VectorStore vectorStore;

    private final ChatClient chatClient;

    private final ChatClient webSearchchatClient;

    @Value("classpath:/promptTemplates/systemPromptRandomDataTemplate.st")
    Resource promptTemplate;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource docpromptTemplate;

    public RagController(VectorStore vectorStore, @Qualifier("chatClientWithMemory") ChatClient chatClient, @Qualifier("webSearchRAGChatClient") ChatClient webSearchchatClient) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClient;
        this.webSearchchatClient = webSearchchatClient;
    }

    @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(@RequestHeader("user") String user, @RequestParam("message") String message){
        SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build();
        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
        String similarContext = similarDocs.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));
        String answer = chatClient.prompt().system(systemSpec -> systemSpec.text(promptTemplate).param("documents",similarContext)).advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,user)).user(message).call().content();
        return ResponseEntity.ok(answer);
    }


    @GetMapping("/doc/chat")
    public ResponseEntity<String> docChat(@RequestHeader("user") String user, @RequestParam("message") String message){
//        SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build();
//        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//        String similarContext = similarDocs.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));
        String answer = chatClient.prompt()
//                .system(systemSpec -> systemSpec.text(docpromptTemplate).param("documents",similarContext))
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID,user)).user(message).call().content();
        return ResponseEntity.ok(answer);
    }


    @GetMapping("/web-search/chat")
    public ResponseEntity<String> webSearchChat(@RequestHeader("username")
                                                String username, @RequestParam("message") String message) {
        String answer =webSearchchatClient.prompt()
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .call().content();
        return ResponseEntity.ok(answer);
    }

}
