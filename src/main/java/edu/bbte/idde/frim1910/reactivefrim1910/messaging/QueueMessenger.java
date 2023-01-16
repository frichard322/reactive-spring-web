package edu.bbte.idde.frim1910.reactivefrim1910.messaging;

import edu.bbte.idde.frim1910.reactivefrim1910.scheduler.Statistics;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class QueueMessenger {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private QueueConfig config;

    public void publish() {
        amqpTemplate.convertAndSend(
                config.getExchange(),
                config.getQueue(),
                Message.fromString(Statistics.getStats())
        );
        log.info("Publishing '{}' using exchange: {} and routine key: {}",
                Statistics.getStats(),
                config.getExchange(),
                config.getQueue()
        );
    }
}
