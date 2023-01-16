package edu.bbte.idde.frim1910.reactivefrim1910.scheduler;

import edu.bbte.idde.frim1910.reactivefrim1910.messaging.QueueMessenger;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
@Log4j2
public class Scheduler {
    private Future<?> future;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private QueueMessenger queueMessenger;

    @Scheduled
    public void initializeTask() {
        future = threadPoolTaskScheduler.schedule(
            queueMessenger::publish,
            getTrigger(30)
        );
        log.info("Initializing scheduled statistic task.");
    }

    public void stopInitialTask() {
        if (future != null) {
            future.cancel(true);
            log.info("Stopping scheduled statistic task.");
        }
    }

    private CronTrigger getTrigger(Integer processInterval) {
        String expression = String.format("0/%d * * * * *", processInterval);
        return new CronTrigger(expression);
    }
}
