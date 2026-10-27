package com.epam.training.sport.configuration;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelConfiguration {
    private final OllamaProperties ollamaProperties;

    @Bean
    public ChatModel chatModel(@Value("${model-provider}") String modelProvider) {
        return switch (modelProvider.toLowerCase()) {
            //case "openai" -> createOpenAiChatModel();
            case "ollama" -> createOllamaChatModel();
            default -> throw new IllegalArgumentException("Unsupported model provider: " + modelProvider);
        };
    }

    @Bean
    public SportEventExtractor sportEventExtractor(ChatModel chatModel) {
        return AiServices.create(SportEventExtractor.class, chatModel);
    }

    private ChatModel createOllamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(ollamaProperties.getBaseUrl())
                .modelName(ollamaProperties.getModelName())
                .build();
    }

//    private ChatModel createOpenAiChatModel() {
//        return OpenAiChatModel.builder()
//                .baseUrl(openAiProperties.getBaseUrl())
//                .apiKey(openAiProperties.getApiKey())
//                .modelName(openAiProperties.getModelName())
//                .logRequests(true)
//                .logResponses(true)
//                .build();
//    }
}
