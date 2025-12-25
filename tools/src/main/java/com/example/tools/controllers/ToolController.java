package com.example.tools.controllers;

import com.example.tools.tools.ServiceTicketDbTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tool")
public class ToolController {

    private final ChatClient chatClient;

    private final ServiceTicketDbTool serviceTicketDbTool;

    @Value("classpath:/promptTemplates/helpDeskSystemPromptTemplate.st")
    Resource promptTemplate;

    public ToolController(ChatClient chatClient,
                          ServiceTicketDbTool serviceTicketDbTool) {
        this.chatClient = chatClient;
        this.serviceTicketDbTool = serviceTicketDbTool;
    }

    @GetMapping("/help-desk")
    public ResponseEntity<String> helpDesk(@RequestHeader("user") String user,
                                           @RequestParam("message") String message) {
        String answer = chatClient.prompt()
                .system(promptTemplate)
                .advisors(a -> a.param(CONVERSATION_ID, user))
                .user(message)
                .tools(serviceTicketDbTool)
                .toolContext(Map.of("user", user))
                .call().content();
        return ResponseEntity.ok(answer);
    }


}
