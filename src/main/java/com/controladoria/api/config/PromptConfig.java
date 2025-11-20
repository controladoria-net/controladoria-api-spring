package com.controladoria.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.converter.BeanOutputConverter;
import com.controladoria.api.dto.*;

@Configuration
@ConfigurationProperties(prefix = "")
@Data
public class PromptConfig {

    private List<PromptDefinition> prompts;
    private String systemPrompt;
    private Map<String, PromptDefinition> promptMap;

    @PostConstruct
    public void init() {
        // Map keys to DTO classes
        Map<String, Class<?>> keyToDtoMap = new HashMap<>();
        keyToDtoMap.put("DOCUMENT_CLASSIFIER", DocumentClassificationResponseDto.class);
        keyToDtoMap.put("CNIS", CnisResponseDto.class);
        keyToDtoMap.put("CERTIFICADO_DE_REGULARIDADE", RgpResponseDto.class);
        keyToDtoMap.put("CAEPF", CaepfResponseDto.class);
        keyToDtoMap.put("COMPROVANTE_RESIDENCIA", ProofOfResidenceResponseDto.class);
        keyToDtoMap.put("TERMO_DE_REPRESENTACAO", RepresentationTermResponseDto.class);
        keyToDtoMap.put("GPS_E_COMPROVANTE", GpsResponseDto.class);
        keyToDtoMap.put("BIOMETRIA", BiometryResponseDto.class);
        keyToDtoMap.put("REAP", ReapResponseDto.class);
        keyToDtoMap.put("DOCUMENTO_IDENTIDADE", RegistrationDocumentResponseDto.class);
        keyToDtoMap.put("PROCURACAO", PowerOfAttorneyResponseDto.class);
        keyToDtoMap.put("DECLARACAO_FILIACAO", AffiliationDeclarationResponseDto.class);
        keyToDtoMap.put("OUTRO", OtherResponseDto.class);

        // Transforma a lista em Map para acesso rápido O(1)
        this.promptMap = prompts.stream()
                .collect(Collectors.toMap(PromptDefinition::getKey, p -> p));

        // Generate schema for each prompt if a DTO class is mapped
        keyToDtoMap.forEach((key, dtoClass) -> {
            if (promptMap.containsKey(key)) {
                BeanOutputConverter<?> converter = new BeanOutputConverter<>(dtoClass);
                promptMap.get(key).setResponseSchema(converter.getJsonSchema());
            }
        });
    }

    public PromptDefinition getByKey(String key) {
        if (!promptMap.containsKey(key)) {
            throw new IllegalArgumentException("Prompt key not found: " + key);
        }
        return promptMap.get(key);
    }

    @Data
    public static class PromptDefinition {
        private String key;
        private String description;
        private String prompt;
        private String responseSchema;
    }
}
