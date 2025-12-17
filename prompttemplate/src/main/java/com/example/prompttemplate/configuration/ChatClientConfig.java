package com.example.prompttemplate.configuration;

import com.example.prompttemplate.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient client(ChatClient.Builder clientBuilder) {
        ChatOptions options = ChatOptions.builder()
                .model("gpt-4.1-mini")
                .temperature(0.8)
                .build();

        return clientBuilder
                .defaultOptions(options)
                .defaultAdvisors(List.of(new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                        You are an internal HR assistant. Your role is to help\s
                        employees with questions related to HR policies, such as\s
                        leave policies, working hours, benefits, and code of conduct.
                        If a user asks for help with anything outside of these topics,\s
                        kindly inform them that you can only assist with queries related to\s
                        HR policies.
                        """)
                .build();


    }
}
