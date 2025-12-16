package com.example.prompttemplate.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient client(ChatClient.Builder clientBuilder) {
        ChatOptions options = ChatOptions.builder().model("gpt-4.1-mini").temperature(0.8).build();

        return clientBuilder.defaultOptions(options).build();


    }
}
