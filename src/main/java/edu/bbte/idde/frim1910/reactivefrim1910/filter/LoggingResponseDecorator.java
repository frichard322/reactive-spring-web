package edu.bbte.idde.frim1910.reactivefrim1910.filter;

import edu.bbte.idde.frim1910.reactivefrim1910.scheduler.Statistics;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;

@Log4j2
public class LoggingResponseDecorator extends ServerHttpResponseDecorator {
    public LoggingResponseDecorator(ServerHttpResponse delegate) {
        super(delegate);
        Statistics.incResponses();
    }
}
