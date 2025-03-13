package com.example.springwebflux.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TestServcie {

    public Mono<String> SayHello_v2() {
        return Mono.just("Hello Mono WebFlux");
    }
}