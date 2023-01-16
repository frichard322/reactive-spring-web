package edu.bbte.idde.frim1910.reactivefrim1910.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthController {
    @GetMapping("/")
    public Mono<ResponseEntity<String>> index() {
        return Mono.just(ResponseEntity.ok("The Reactive API Server is up and running!"));
    }
}
