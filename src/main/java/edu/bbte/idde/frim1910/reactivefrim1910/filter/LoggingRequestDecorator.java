package edu.bbte.idde.frim1910.reactivefrim1910.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;

@Log4j2
public class LoggingRequestDecorator extends ServerHttpRequestDecorator {
    public LoggingRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
        log.info("Request: {} {} {}",
                delegate.getMethodValue(),
                delegate.getPath().value(),
                delegate.getQueryParams().toString()
        );
    }
}
