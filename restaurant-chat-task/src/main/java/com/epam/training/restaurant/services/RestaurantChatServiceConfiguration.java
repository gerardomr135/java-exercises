package com.epam.training.restaurant.services;

import com.epam.training.restaurant.configuration.SystemMessageProvider;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestaurantChatServiceConfiguration {
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }

    @Bean
    public RestaurantChatService restaurantChatService(ChatModel chatModel, SystemMessageProvider systemMessageProvider, ChatMemory chatMemory) {
        return  AiServices.builder(RestaurantChatService.class)
                .chatModel(chatModel)
                .chatMemory(chatMemory)
                .systemMessageProvider(systemMessageProvider)
                .build();
    }
}
