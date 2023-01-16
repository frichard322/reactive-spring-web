package edu.bbte.idde.frim1910.reactivefrim1910.scheduler;

import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private static final AtomicInteger RESPONSES = new AtomicInteger(0);

    public static void incResponses() {
        RESPONSES.incrementAndGet();
    }

    public static String getStats() {
        return String.format("Count of responses: %d", RESPONSES.get());
    }
}
