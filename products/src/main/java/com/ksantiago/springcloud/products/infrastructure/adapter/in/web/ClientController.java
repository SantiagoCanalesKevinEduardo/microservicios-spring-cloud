package com.ksantiago.springcloud.products.infrastructure.adapter.in.web;

import com.ksantiago.springcloud.products.application.port.in.ClientUseCase;
import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ClientDto;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper.ClientWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientUseCase clientUseCase;
    private final ClientWebMapper clientMapper;

    public ClientController(ClientUseCase clientUseCase, ClientWebMapper clientMapper) {
        this.clientUseCase = clientUseCase;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createClient(@Valid @RequestBody ClientDto clientDto) {
        Client client = clientMapper.toDomain(clientDto);
        clientUseCase.createClient(client);
        return ResponseEntity.ok().body(Map.of("message", "Request to create client has been sent successfully"));
    }
}
