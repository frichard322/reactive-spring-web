package edu.bbte.idde.frim1910.reactivefrim1910.messaging;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QueueConfig {
    private String queue;
    private String exchange;
}
