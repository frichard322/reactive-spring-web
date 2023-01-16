package edu.bbte.idde.frim1910.reactivefrim1910.errorhandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Log4j2
public class ErrorHandler {
    @ExceptionHandler(WebExchangeBindException.class)
    public final Mono<ResponseEntity<Flux<String>>> handleWebExchangeBindException(WebExchangeBindException e) {
        return Mono.just(ResponseEntity
            .badRequest()
            .body(
                Flux.fromIterable(e.getFieldErrors())
                    .map(it -> it.getField() + " " + it.getDefaultMessage() + "\n")
            )
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ResponseEntity<Void>> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<Void>> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
