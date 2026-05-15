package com.ksantiago.springcloud.products.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping(){
        return "ping ok productos-api";
    }
}
