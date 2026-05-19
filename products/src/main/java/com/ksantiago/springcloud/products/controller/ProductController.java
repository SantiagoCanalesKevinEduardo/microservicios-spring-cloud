package com.ksantiago.springcloud.products.controller;

import com.ksantiago.springcloud.products.models.Reply;
import com.ksantiago.springcloud.products.models.dto.ProductDto;
import com.ksantiago.springcloud.products.services.ProductCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return getResponseEntity(reply);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Reply<?> reply=  commandService.sendReadAndAwait(id, Duration.ofSeconds(5));
        return getResponseEntity(reply);
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        Reply<?> reply=  commandService.sendReadAllAndAwait( Duration.ofSeconds(5));
        return getResponseEntity(reply);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody ProductDto dto){
        Reply<?> reply=  commandService.sendUpdateAndAwait(id, dto,Duration.ofSeconds(5));
        return getResponseEntity(reply);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Reply<?> reply=  commandService.sendDeleteAndAwait(id, Duration.ofSeconds(5));
        return getResponseEntity(reply);
    }


    private ResponseEntity<?> getResponseEntity(Reply<?> reply){
        if("SUCCESS".equalsIgnoreCase(reply.status())){
            return ResponseEntity.ok().body(reply.body());
        }else{
            return ResponseEntity.badRequest().body(Map.of("error",reply.message()));
        }
    }





}
