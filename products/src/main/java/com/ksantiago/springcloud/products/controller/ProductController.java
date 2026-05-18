package com.ksantiago.springcloud.products.controller;

import com.ksantiago.springcloud.products.models.Reply;
import com.ksantiago.springcloud.products.models.dto.ProductDto;
import com.ksantiago.springcloud.products.services.ProductCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductCommandService commandService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto dto){
        Reply<?> reply=  commandService.sendCreateAndAwait(dto, Duration.ofSeconds(5));

        if("SUCCESS".equalsIgnoreCase(reply.status())){
            return ResponseEntity.ok().body(reply.body());
        }else{
            return ResponseEntity.badRequest().body(Map.of("error",reply.message()));
        }
    }
}
