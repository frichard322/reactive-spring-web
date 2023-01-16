package edu.bbte.idde.frim1910.reactivefrim1910.filter;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public class LoggingWebExchange extends ServerWebExchangeDecorator {

    protected LoggingWebExchange(ServerWebExchange delegate) {
        super(delegate);
        new LoggingRequestDecorator(delegate.getRequest());
        new LoggingResponseDecorator(delegate.getResponse());
    }
}
