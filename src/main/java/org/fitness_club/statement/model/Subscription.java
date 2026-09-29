package org.fitness_club.statement.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Subscription {
    private Long id;
    private Long clientId;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;

    public Subscription() {}

    public Subscription(Long clientId, String type, LocalDate startDate, LocalDate endDate) {
        this.clientId = clientId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}