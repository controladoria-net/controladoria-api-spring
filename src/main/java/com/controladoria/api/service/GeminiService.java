package com.controladoria.api.service;

import com.controladoria.api.config.PromptConfig;
import com.controladoria.api.dto.DocumentClassificationResponseDto;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final ChatModel chatModel;
    private final PromptConfig promptConfig;
    private final S3Service s3Service;

    public GeminiService(ChatModel chatModel, PromptConfig promptConfig, S3Service s3Service) {
        this.chatModel = chatModel;
        this.promptConfig = promptConfig;
        this.s3Service = s3Service;
    }

    public DocumentClassificationResponseDto classifyDocument(String s3Key) {
        return extractData(s3Key, "DOCUMENT_CLASSIFIER", DocumentClassificationResponseDto.class);
    }

    public <T> T extractData(String s3Key, String promptKey, Class<T> responseType) {
        PromptConfig.PromptDefinition promptDef = promptConfig.getByKey(promptKey);

        // Prepare the converter
        BeanOutputConverter<T> converter = new BeanOutputConverter<>(responseType);

        // Get file content from S3
        byte[] fileContent = s3Service.downloadFile(s3Key);
        Resource resource = new ByteArrayResource(fileContent);

        // Prepare user message with text and media
        // Note: We assume PDF or Image. For simplicity, we might need to detect mime
        // type.
        // S3Service could return mime type or we guess it.
        // For now, let's assume it's a PDF or Image based on extension or just use a
        // generic one if supported,
        // but Spring AI needs a MimeType.
        String mimeType = guessMimeType(s3Key);

        String response = chatClient.prompt()
                .system(promptDef.getSystem_prompt())
                .user(u -> u
                        .text(promptDef.getPrompt() + "\n" + converter.getFormat()) // Injeta instrução de formato JSON
                        .media(new Media(MimeTypeUtils.APPLICATION_PDF, fileResource))
                )
                .call()
                .content();

        return converter.convert(response);
    }

    private String guessMimeType(String s3Key) {
        if (s3Key.toLowerCase().endsWith(".pdf")) {
            return "application/pdf";
        } else if (s3Key.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (s3Key.toLowerCase().endsWith(".jpg") || s3Key.toLowerCase().endsWith(".jpeg")) {
            return "image/jpeg";
        }
        return "application/octet-stream";
    }
}
