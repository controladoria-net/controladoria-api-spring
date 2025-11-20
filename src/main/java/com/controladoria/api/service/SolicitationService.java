package com.controladoria.api.service;

import com.controladoria.api.model.Client;
import com.controladoria.api.model.Document;
import com.controladoria.api.model.DocumentProcessingStatus;
import com.controladoria.api.model.Solicitation;
import com.controladoria.api.model.SolicitationStatus;
import com.controladoria.api.repository.ClientRepository;
import com.controladoria.api.repository.DocumentRepository;
import com.controladoria.api.repository.SolicitationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolicitationService {

    private final SolicitationRepository solicitationRepository;
    private final ClientRepository clientRepository;
    private final DocumentRepository documentRepository;
    private final S3Service s3Service;

    @Transactional
    public UUID createSolicitation(UUID clientId, MultipartFile[] files, String createdBy) throws IOException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Solicitation solicitation = Solicitation.builder()
                .client(client)
                .status(SolicitationStatus.NOVA_SOLICITACAO)
                .createdBy(createdBy)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        solicitation = solicitationRepository.save(solicitation);

        List<Document> documents = new ArrayList<>();
        for (MultipartFile file : files) {
            String storagePath = s3Service.uploadFile(file);

            Document document = Document.builder()
                    .request(solicitation)
                    .fileName(file.getOriginalFilename())
                    .storagePath(storagePath)
                    .processingStatus(DocumentProcessingStatus.PENDENTE)
                    .createdAt(OffsetDateTime.now())
                    .build();

            documents.add(document);
        }

        documentRepository.saveAll(documents);

        // TODO: Publish SolicitationCreatedEvent

        return solicitation.getId();
    }
}
