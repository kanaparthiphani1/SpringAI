package com.example.rag.config;

import com.example.rag.advisors.TokenAuditUsageAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        ChatOptions chatOptions = ChatOptions.builder().model("gpt-4.1-mini")
                .temperature(0.8).build();
        return builder.defaultOptions(chatOptions).defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenAuditUsageAdvisor())).build();
    }
}
