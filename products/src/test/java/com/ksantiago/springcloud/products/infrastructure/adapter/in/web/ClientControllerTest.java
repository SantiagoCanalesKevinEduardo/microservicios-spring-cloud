package com.ksantiago.springcloud.products.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksantiago.springcloud.products.application.port.in.ClientUseCase;
import com.ksantiago.springcloud.products.domain.model.Client;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.dto.ClientDto;
import com.ksantiago.springcloud.products.infrastructure.adapter.in.web.mapper.ClientWebMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientUseCase clientUseCase;

    @MockitoBean
    private ClientWebMapper clientMapper;

    @Test
    void shouldCreateClientAndReturnSuccessMessage() throws Exception {
        // Arrange
        ClientDto clientDto = new ClientDto(null, "John", "Doe", 30);

        Client client = Client.builder()
                .name("John")
                .lastName("Doe")
                .age(30)
                .build();

        when(clientMapper.toDomain(any(ClientDto.class))).thenReturn(client);
        doNothing().when(clientUseCase).createClient(any(Client.class));

        // Act & Assert
        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Request to create client has been sent successfully"));
    }
}
