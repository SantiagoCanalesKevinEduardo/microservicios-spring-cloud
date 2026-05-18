package com.ksantiago.springcloud.products.controller;

import com.ksantiago.springcloud.products.models.dto.ClientDto;
import com.ksantiago.springcloud.products.services.ClientCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("clients")
public class ClientController {
    
    private final ClientCommandService clientCommandService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClientDto dto) {
        clientCommandService.sendCreate(dto);
        return ResponseEntity.ok().body(Map.of("message", "Success Sent"));
    }
    
}
