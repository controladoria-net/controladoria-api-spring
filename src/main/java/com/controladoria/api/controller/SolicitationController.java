package com.controladoria.api.controller;

import com.controladoria.api.service.SolicitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/solicitations")
@RequiredArgsConstructor
public class SolicitationController {

    private final SolicitationService solicitationService;

    @PostMapping
    public ResponseEntity<UUID> createSolicitation(
            @RequestParam("clientId") UUID clientId,
            @RequestParam("files") MultipartFile[] files,
            @AuthenticationPrincipal Jwt jwt) throws IOException {
        String createdBy = jwt.getSubject();
        UUID solicitationId = solicitationService.createSolicitation(clientId, files, createdBy);
        return ResponseEntity.accepted().body(solicitationId);
    }
}
