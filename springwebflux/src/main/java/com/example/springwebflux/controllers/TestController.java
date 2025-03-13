package com.example.springwebflux.controllers;

import com.example.springwebflux.services.TestServcie;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private TestServcie testService;

    @GetMapping("/id")
    public Integer GetTestId() {
        return 10;
    }

    @GetMapping("/hello")
    public Mono<String> SayHello() {
        return Mono.just("Hello Mono WebFlux");
    }


    @GetMapping("/service/v1")
    public Mono<String> SayHello_v2() {
        return testService.SayHello_v2();
    }

    @GetMapping(value ="/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> StreamString() {
        /*return Flux.interval(Duration.ofSeconds(10))
                .map(s -> "Flux Stream String")
                .take(Duration.ofMinutes(1));*/

        return Flux.interval(Duration.ZERO, Duration.ofSeconds(1))
                .map(s -> "My Flux Stream String")
                .take(Duration.ofMinutes(2));
    }

    @GetMapping(value="/streamint", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> StreamInt() {
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(2))
                .map(s -> 20)
                .take(Duration.ofMinutes(1));
    }
}
